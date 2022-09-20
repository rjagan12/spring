package com.i2i.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.i2i.model.Trainee;
import com.i2i.model.Trainer;
import com.i2i.service.EmployeeService;
import com.i2i.service.impl.EmployeeServiceImpl;
import org.hibernate.HibernateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class TraineeServlet extends HttpServlet {
    private static final String line = ("******************************************************");
    private static final EmployeeService employeeService = new EmployeeServiceImpl();
    private ObjectMapper mapper = new ObjectMapper();

    /*
    @Override
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

    }
    @Override
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
    } */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
         String message = "***** Not Inserted ******";

        if (pathInfo == null || pathInfo.equals("/")) {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String payload = builder.toString();
            Trainee trainee = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
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

                for (int i = 0; i < trainerId.length; i++) {
                    int trainersId = Integer.valueOf(trainerId[i]);
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        List<Trainee> showTrainee = null;

        if (uri.equals("/ServletExample/trainers")) {
            try {
                showTrainee = employeeService.showAllTraineeDetails();

                PrintWriter printWriter = response.getWriter();
                for(Trainee trainees : showTrainee) {
                    // Map<String, Employee> trainees1 = employeeServiceImpl.getObject(trainees);
                }
                String jsonStr = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                        .findAndRegisterModules().writeValueAsString(showTrainee);
                printWriter.print(jsonStr);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            String id = request.getParameter("id");
            int traineeId = Integer.parseInt(id);
            PrintWriter printWriter = response.getWriter();
            Trainee trainee = null;
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
    private  Trainee removeTrainer( int trainerId, Trainee trainee) throws Exception {
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
            throw e;
        }
        return trainee;
    }
}
