package com.i2i.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.i2i.exception.NullListException;
import com.i2i.model.Trainee;
import com.i2i.model.Trainer;
import com.i2i.service.EmployeeService;
import com.i2i.service.impl.EmployeeServiceImpl;
import com.i2i.util.CommonUtil;
import org.hibernate.HibernateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <h> TraineeServlet </h>
 *  class used to extends HttpServlet and gives method definitions
 *  to get, put, post and delete employee details from user to EmployeeServiceImpl
 *  vice versa
 *
 * @version 1.0
 * @author Jaganathan R
 */
public class TraineeServlet extends HttpServlet {

    private static final String inValidData = (" ##********* //INVALID DATA// ************## ");
    private static final String noData = (" ##********* // NO DATA // ************## ");
    private static final Logger logger = LoggerFactory.getLogger(TraineeServlet.class);

    private static final String line = ("******************************************************");
    private static final EmployeeService employeeService = new EmployeeServiceImpl();
    private  ObjectMapper mapper = new ObjectMapper();
    private  HttpServletResponse response;
    private  HttpServletRequest request;

    /**
     * Method used to get trainer details  from server
     * @param {@link HttpServletrequest, HttpServletResponse}request and response
     * @return {noreturn}
     */
    /*@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        List<Trainee> listOfTrainees = null;
        if( "/demoservlet/Trainers".equals(uri)) {
            try {
                listOfTrainees = employeeService.showAllTraineeDetails();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                if (CommonUtil.validateTrainees(listOfTrainees)) {
                    System.out.println(line);
                    for (Trainee list : listOfTrainees) {
                        try {
                            response.getOutputStream().println(("Employee Name :")
                                    + (list.getName())
                                    + (" \n EmployeeID : ")
                                    + (list.getId())
                                    + (" \n MobileNumber : ")
                                    + (list.getMobileNumber())
                                    + (" \n Role Of Employee : ")
                                    + (list.getRole()) + ("\n")
                                    + line);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
            } catch (NullListException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Trainee trainee = null;

            try {
                int id = Integer.parseInt(request.getParameter("id"));
                trainee = employeeService.showTraineeDetailsById(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(line);
            response.getOutputStream().println(("\n Employee Name : ")
                             + (trainee.getName())+(" \n EmployeeID : ")
                             + (trainee.getId())+(" \n MobileNumber : ")
                             + (trainee.getMobileNumber())+(" \n Role Of Employee : ")
                             + (trainee.getRole())+("\n")+(line));
        }

    } */

    /**
     * Method used to post trainee details to service from user
     * @param {@link HttpServletRequest, HttpServletResponse}request and response
     * @return {noreturn}
     */
   /*@Override
    protected void  doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message= "**** Not Inserted ****";
        String uri = request.getRequestURI();
        Trainee trainee = new Trainee();
        trainee.setName(request.getParameter("name"));
        trainee.setMail(request.getParameter("mail"));
        trainee.setAddress(request.getParameter("address"));
        trainee.setPanNumber(request.getParameter("panNumber"));
        trainee.setRole(request.getParameter("role"));
        String aadhar = request.getParameter("aadharNumber");
        trainee.setAadharNumber(Long.parseLong(aadhar));
        String number = request.getParameter("mobileNumber");
        trainee.setMobileNumber(Long.parseLong(number));
        String year = request.getParameter("passOutYear");
        LocalDate pass = LocalDate.parse(year);
        trainee.setPassOutYear(pass);
        String dateOfBirth = request.getParameter("dateOfBirth");
        LocalDate birth = LocalDate.parse(dateOfBirth);
        trainee.setDateOfBirth(birth);
        LocalDate date = LocalDate.parse(request.getParameter("dateOfJoin"));
        trainee.setDateOfJoin(date);

        try {
            message = employeeService.addTraineeDetails(trainee);
            response.getOutputStream().println(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
     */

    /**
     * Method used to post trainee details to service from user
     * @param {@link HttpServletRequest, HttpServletResponse}request and response
     * @return {noreturn}
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
         String message = "***** Not Inserted ******";

        if (uri != null) {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String payload = builder.toString();

            Trainee trainee = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .findAndRegisterModules().readValue(payload, Trainee.class);

            try {
                message = employeeService.addTraineeDetails(trainee);
                response.getOutputStream().println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().println(message);
        }
    }
    private StringBuilder dataValidation(HttpServletRequest request) throws IOException {

        String name =null;
        StringBuilder builder = new StringBuilder();
        try {

            name = request.getParameter("name");
            System.out.println("Name : "+name);
            if (CommonUtil.stringValidation( name)) {
                //trainer.setName(name);
                return builder.append(name);
            } else {
                response.getOutputStream().println(inValidData+"\n please Enter the valid name");
            }
            String mail = request.getParameter("mail") ;
            if(CommonUtil.mailValidation( mail)) {
                mail.toLowerCase();
                return builder.append(mail);
            } else{
                response.getOutputStream().println(inValidData+"Please Enter the Valid Mail Id : ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*request.getParameter("mobileNumber");
        while (!.hasNext("[6-9]{1}[0-9]{9}")) {
            logger.error("{}",inValidData);
            System.out.println("Please Enter Valid Number : ");
            scanner.next();
        }

        trainer.setMobileNumber(scanner.nextLong());
        r

        }

        boolean flag = false;

        while (!flag) {

            try {
                System.out.println(" Enter The Date Of Birth in dd/mm/yyyy format ");
                System.out.println("Enter the day(1-31) in date of Birth :");
                int day = (scanner.nextInt());
                System.out.println("Enter the month(1-12) in date of Birth :");
                int month = (scanner.nextInt());
                System.out.println("Enter the year(yyyy) in date of Birth :");
                int year = (scanner.nextInt());
                System.out.println("Changing the date Format :");
                trainer.setDateOfBirth(LocalDate.of(year, month,day));
                flag = true;

            } catch(DateTimeException e) {
                System.out.println(e);

            } catch(Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Date Of Birth : " + trainer.getDateOfBirth());
        flag = false;

        while (!flag) {

            try {
                System.out.println("Enter the Date Of Join in format of dd/mm/yyyy : ");
                System.out.println("Enter the day in date of Join :");
                int day1 = (scanner.nextInt());
                System.out.println("Enter the month in date of Join :");
                int month1 = (scanner.nextInt());
                System.out.println("Enter the year in date of Join :");
                int year1 = (scanner.nextInt());
                System.out.println("Changing the date Format :");
                trainer.setDateOfJoin(LocalDate.of(year1, month1, day1));
                flag = true;

            } catch(DateTimeException e) {
                System.out.println(e);

            } catch(Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Date Of Join : "+trainer.getDateOfJoin());
        System.out.println(" Enter The Aadhar Card Number :");

        while (!scanner.hasNext("[0-9]{12}")) {
            logger.error("{}",inValidData);
            System.out.println("Enter The Valid Aaadhar Number :");
            scanner.next();
        }
        trainer.setAadharNumber(scanner.nextLong());
        System.out.println(" Enter the Pan Card number :");

        while (!scanner.hasNext("[a-zA-z0-9]{10}")) {
            logger.error("{}",inValidData);
            scanner.next();
        }
        trainer.setPanNumber(scanner.next().toUpperCase());
        scanner.nextLine();
        System.out.println(" Enter the Current Address : ");
        String address = scanner.nextLine();

        while (!CommonUtil.stringValidation(address)) {
            logger.error("{}",inValidData);
            System.out.println("Please Enter Valid Address");
            address = scanner.nextLine();
        }
        trainer.setAddress(address);
        System.out.println("Enter the Previous Company Name of an Employee : ");
        String previousCompanyName = scanner.nextLine();

        while (!CommonUtil.stringValidation(previousCompanyName)) {
            logger.error("{}",inValidData);
            System.out.println("Please Enter Valid Company Name");
            previousCompanyName = scanner.nextLine();
        }

        trainer.setPreviousCompanyName(previousCompanyName);
        System.out.println("Enter The Previous Company Experience ");
        int experience = scanner.nextInt();
        trainer.setExperience(experience);
        System.out.println("Enter the Role of an Employee : ");
        String role = scanner.nextLine();

        while (!CommonUtil.stringValidation(role)) {
            logger.error("{}",inValidData);
            System.out.println("Please Enter the Valid Role : ");
            role = scanner.nextLine();
        }
        trainer.setRole(role);
        System.out.println("*************!_^_^_^!****************"); */
       return builder;
    }

    /**
     * Method used to remove or delete trainee details to service from user
     * @param {@link HttpServletRequest, HttpServletResponse}request and response
     * @return {noreturn}
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message = "******* there is no record to Delete *******";
        String uri = request.getRequestURI();
        int id = Integer.parseInt(request.getParameter("id"));

        if (uri.equals("/demoservlet/Trainer")) {
            try {
                message = employeeService.deleteTraineeDetails(id);
                response.getOutputStream().println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
           }
        } else {
            int traineeId = Integer.parseInt(request.getParameter("id"));
            Trainee trainee = null;
            message = "******* THERE IS NO ID TO REMOVE ********";

            try {
                trainee = employeeService.showTraineeDetailsById(traineeId);
                int trainerId = Integer.parseInt(request.getParameter("trainerId"));
                message = employeeService.removeIdFromAssignedTrainee(traineeId, removeTrainer(trainerId, trainee));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method used to put or update trainee details to service from user
     * @param {@link HttpServletRequest, HttpServletResponse}request and response
     * @return {noreturn}
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        String message = "***** Details Not Modify *****";
        Trainee trainee = null;
        String value = request.getParameter("id");
        int id = Integer.parseInt(value);

        if (uri.equals("/demoservlet/Trainee")) {
            try {
                trainee = employeeService.showTraineeDetailsById(id);
                trainee.setName(request.getParameter("name"));
                trainee.setMail(request.getParameter("mail"));
                trainee.setAddress(request.getParameter("address"));
                trainee.setPanNumber(request.getParameter("panNumber"));
                trainee.setRole(request.getParameter("role"));
                String aadhar = request.getParameter("aadharNumber");
                trainee.setAadharNumber(Long.parseLong(aadhar));
                String year = request.getParameter("passOutYear");
                trainee.setPassOutYear(LocalDate.parse(year));
                String mobileNumber = request.getParameter("mobileNumber");
                Long number = Long.parseLong(mobileNumber);
                trainee.setMobileNumber(number);
                String dateOfBirth = request.getParameter("dateOfBirth");
                LocalDate birth = LocalDate.parse(dateOfBirth);
                trainee.setDateOfBirth(birth);
                LocalDate date = LocalDate.parse(request.getParameter("dateOfJoin"));
                trainee.setDateOfJoin(date);
                message = employeeService.modifyTraineeDetailsById(id, trainee);
                response.getOutputStream().println(message);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                List<Trainer> list = null;
                int traineeId = Integer.parseInt(request.getParameter("id"));
                trainee = employeeService.showTraineeDetailsById(traineeId);
                String ids = request.getParameter("trainerIds");
                System.out.println(ids+"trainerIds");
                String[] trainerId = ids.split(",");

                for (String trainersIds: trainerId) {
                    int trainersId = Integer.valueOf(trainersIds);
                    Trainer trainer = employeeService.showTrainerDetailsById(trainersId);
                    if (trainer != null) {
                        trainee.getTrainerDetails().add(trainer);
                    } else {
                        message = "**** THERE IS NO TRAINER ID *****";
                    }
                }
                message = employeeService.assignTrainers(traineeId, trainee);
                response.getOutputStream().println(message);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method used to get trainer details  from server
     * @param {@link HttpServletrequest, HttpServletResponse}request and response
     * @return {noreturn}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        List<Trainee> trainees = null;

        if (uri.equals("/demoservlet/Trainees")) {
            try {
                trainees = employeeService.showAllTraineeDetails();

                PrintWriter printWriter = response.getWriter();
                for(Trainee traineelist  : trainees) {
                    Map<String, Object> trainee = employeeService.getTraineeObject(traineelist);

                    String jsonStr = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                            .findAndRegisterModules().writeValueAsString(trainee);
                    printWriter.print(jsonStr);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            String id = request.getParameter("id");
            int traineeId = Integer.parseInt(id);
            Trainee trainee = null;
            PrintWriter printWriter = response.getWriter();
            try {
                trainee = employeeService.showTraineeDetailsById(traineeId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (trainee != null) {

                String jsonStr = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                        .findAndRegisterModules().writeValueAsString(trainee);
                // String jsonStr = mapper.writeValueAsString(trainee);
                printWriter.print(jsonStr);

            } else {
                printWriter.println("Invalid Employee ID");
            }

        }
    }

    /**
     * Method used to delete trainer details from assign trainees
     * @param {@link Int, Trainee}id and trainee object
     * @return {@link Trainee}trainee Object
     */
    private  Trainee removeTrainer(int trainerId, Trainee trainee) throws Exception {
        List<Trainer> list =trainee.getTrainerDetails();
        try {
            if (list.size() >= 1) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(list.get(i).getId());
                    if ((list.get(i).getId()) == (trainerId)) {
                        list.remove(i);
                    }
                }
                trainee.setTrainerDetails(list);
            } else {
                String message = "***** THERE IS NO ID TO REMOVE ******";
            }

        } catch(HibernateException e) {
            throw new HibernateException(e);
        }
        return trainee;
    }

}
