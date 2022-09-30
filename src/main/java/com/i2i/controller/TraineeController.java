package com.i2i.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.i2i.model.Trainee;
import com.i2i.model.Trainer;
import com.i2i.service.EmployeeService;
import com.i2i.service.impl.EmployeeServiceImpl;
import com.i2i.util.CommonUtil;
import org.hibernate.HibernateException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
//@RestController
//@RequestMapping("/TraineeServlet")
public class TraineeController extends HttpServlet {

    private static final String inValidData = (" ##********* //INVALID DATA// ************## ");
    private static final String noData = (" ##********* // NO DATA // ************## ");
    private static final Logger logger = LoggerFactory.getLogger(TraineeController.class);
    private static final String line = ("******************************************************");
   // logger.info("_______________________________________________");
    //logger.info("******* Respose from Trainer Controller *********");
    //logger.info("_______________________________________________");
    private static  EmployeeService employeeService ;
    private  ObjectMapper mapper = new ObjectMapper();
    private HttpServletResponse response;

    public TraineeController(EmployeeService employeeService) {
        this.employeeService = employeeService;

    }

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
     * @return noreturn
     */
   @Override
  // @RequestMapping(value="/demoservlet/Trainee",method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        String uri = request.getRequestURI();
        try {
            message = "***** Not Inserted ******";
            if (uri.equals("/demoservlet/Trainee")) {
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                String traineeDetails = builder.toString();
                Trainee trainee = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .findAndRegisterModules().readValue(traineeDetails, Trainee.class);
                message = dataValidation(trainee, response);
                response.getOutputStream().println(message );
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                response.getOutputStream().println(message);
            }
        } catch(DateTimeException | InvalidFormatException exception) {
            response.getOutputStream().println(message + exception + "\n Please Enter The VALID DATE (yyyy-mm-dd)");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method used to get and validate trainee details
     * @param {@link HttpServletrequest, HttpServletResponse}request and response
     * @return {@link String}returns status of validation
     */
    private String dataValidation(Trainee trainee, HttpServletResponse response) throws Exception {
        Trainee trainees = new Trainee();
        String message = "*** INVALID INPUT **" ;
         String name = trainee.getName();
         if (CommonUtil.stringValidation(name)) {
             trainees.setName(name);
             String mail = trainee.getMail();
             if (CommonUtil.mailValidation(mail)) {
                 mail = mail.toLowerCase();
                 trainees.setMail(mail);
                 Long mobileNumber = trainee.getMobileNumber();
                 String number = String.valueOf(mobileNumber);
                 if ((CommonUtil.phoneNumberValidation(number))) {
                     trainees.setMobileNumber(Long.parseLong(number));
                     String role = trainee.getRole();
                     if (CommonUtil.stringValidation(role)) {
                         trainees.setRole(role);
                         String panNumber = trainee.getPanNumber();
                         if (CommonUtil.panValidation(panNumber)) {
                             trainees.setPanNumber(panNumber);
                             String address = trainee.getAddress();
                             if (CommonUtil.addressValidation(address)) {
                                 address.toUpperCase();
                                 trainees.setAddress(address);
                                 Long aadharNumber = trainee.getAadharNumber();
                                 if (CommonUtil.aadharValidation(aadharNumber)) {
                                     trainees.setAadharNumber(aadharNumber);
                                     try {
                                         LocalDate passOutYear = trainee.getPassOutYear();
                                         trainees.setPassOutYear(LocalDate.of(passOutYear.getYear()
                                                 , passOutYear.getMonthValue(), passOutYear.getDayOfMonth()));
                                         LocalDate dateOfBirth = trainee.getDateOfBirth();
                                         trainees.setDateOfBirth(LocalDate.of(dateOfBirth.getYear()
                                                 , dateOfBirth.getMonthValue(), dateOfBirth.getDayOfMonth()));
                                         LocalDate dateOfJoin = trainee.getDateOfJoin();
                                         trainees.setDateOfJoin(LocalDate.of(dateOfJoin.getYear()
                                                 , dateOfJoin.getMonthValue(), dateOfJoin.getDayOfMonth()));
                                         message = employeeService.addTraineeDetails(trainees);
                                     } catch (DateTimeException exception) {
                                         throw new DateTimeException( "\n Please Enter The VALID DATE (yyyy-mm-dd)");
                                     }catch(RuntimeException exception){
                                         throw new RuntimeException(""+ exception);
                                     }
                                 } else {
                                     response.getOutputStream().println(message +
                                             "\nPlease Enter The Valid AadharNumber ");
                                 }
                             } else {
                                 response.getOutputStream().println(message + "\nPlease Enter The Valid Address ");
                             }
                         } else {
                             response.getOutputStream().println(message + "\nPlease Enter The Valid PanNumber ");
                         }
                     } else {
                         response.getOutputStream().println(message + "\nPlease Enter The Valid role  ");
                     }
                 } else {
                     response.getOutputStream().println(message + "\nPlease Enter The Valid Phone Number  ");
                 }
             } else {
                 response.getOutputStream().println(message + "\nPlease Enter The Valid Mail  ");
             }
         } else {
             response.getOutputStream().println(message + "\nPlease Enter The Valid Name  ");
         }
       return message ;
    }

    /**
     * Method used to delete trainee details  from server
     * @param {@link HttpServletrequest, HttpServletResponse}request and response
     * @return {noreturn}
     */
    @Override
    //@RequestMapping(value="/save",method = RequestMethod.DELETE)
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
                response.getOutputStream().println(message);
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
    //@RequestMapping(value="/Trainer/{id..}",method = RequestMethod.PUT)
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
     * Method used to get trainee details  from server
     * @param {@link HttpServletrequest, HttpServletResponse}request and response
     * @return {noreturn}
     */
    @Override
   // @RequestMapping(value="/Trainee/{id}",method = RequestMethod.GET)
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
            PrintWriter printWriter = response.getWriter();
            Trainee traineeDetail = null;
            try {
                traineeDetail = employeeService.showTraineeDetailsById(traineeId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (traineeDetail != null) {

                    Map<String, Object> list = employeeService.getTraineeObject(traineeDetail);
                    String jsonStr = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                            .findAndRegisterModules().writeValueAsString(list);
                    // String jsonStr = mapper.writeValueAsString(trainee);
                    printWriter.print(jsonStr);

            } else {
                printWriter.println("Invalid Employee ID");
            }

        }
    }

    /**
     * Method used to delete trainee details from assign trainees
     * @param {@link Int, Trainee}id and trainee object
     * @return {@link Trainee}trainee Object
     */
    private  Trainee removeTrainer(int trainerId, Trainee trainee) throws Exception {
        List<Trainer> list =trainee.getTrainerDetails();
        try {
            if (list.size() >= 1) {
                for (int i = 0; i < list.size(); i++) {

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
    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try{
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
            System.out.println("List of Trainee Details : "+str);
        }
        JSONObject jObj = new JSONObject();
        System.out.println("JObj of Input"+jObj);
        String name = String.valueOf(jObj.getString("Name"));
        System.out.println(name);
        String number = String.valueOf(jObj.("mobileNumber"));
        String pan = String.valueOf(jObj.("panNumber"));
        Long aadharNumber = Long.valueOf((jObj.("aadharNumber")));
        String role = String.valueOf(jObj.("role"));
        String address = String.valueOf(jObj.("address"));
        LocalDate dateOfBirth =LocalDate.parse((CharSequence) jObj.("dateOfBirth"));
        LocalDate dateOfJoinate = LocalDate.parse((CharSequence) jObj.("dateOfJoin"));
        LocalDate passOutYear =LocalDate.parse((CharSequence) jObj.("passOutYear"));
        response.setContentType("Trainee");
        PrintWriter out = response.getWriter();
        //out.print(jObj.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
   }*/


}
