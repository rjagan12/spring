package com.i2i.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.i2i.model.Trainee;
import com.i2i.model.Trainer;
import com.i2i.service.EmployeeService;
import com.i2i.util.CommonUtil;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

/**
 * <h> TrainerServlet </h>
 *  class used to extends HttpServlet and gives method definitions
 *  to get, put, post and delete employee details from user to EmployeeServiceImpl
 *  vice versa
 *
 * @version 1.0
 * @author Jaganathan R
 */
@RestController
public class TrainerController extends HttpServlet {

    private static final String line = ("******************************************************");
    private static final Logger logger = LoggerFactory.getLogger(TrainerController.class);
    private static EmployeeService employeeService ;
    private ObjectMapper mapper = new ObjectMapper();
    private HttpServletResponse response;
    private HttpServletRequest request;

    public TrainerController(EmployeeService employeeService) {
        this.employeeService = employeeService;

    }

    @PutMapping("/Trainer")
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "***** Details Not Modify *****";
        Trainer trainer = null;
        String uri = request.getRequestURI();
        String value = request.getParameter("id");
        int id = Integer.parseInt(value);

        if (uri.equals("/spring/Trainer")) {

            try {
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String trainerDetails = buffer.toString();
                trainer = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                            .findAndRegisterModules().readValue(trainerDetails, Trainer.class);
                message = employeeService.modifyTrainerDetailsById(id, trainer);
                response.getOutputStream().println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                List<Trainee> list = null;
                int trainerId = Integer.parseInt(request.getParameter("id"));
                trainer = employeeService.showTrainerDetailsById(trainerId);
                String ids = request.getParameter("traineeIds");
                String[] traineeId = ids.split(",");

                for (int i = 0; i < traineeId.length; i++) {
                    int traineesId = Integer.valueOf(traineeId[i]);
                    Trainee trainee = employeeService.showTraineeDetailsById(traineesId);

                    if (trainee != null) {
                        trainer.getTraineeDetails().add(trainee);

                    } else {
                        message = "**** THERE IS NO TRAINEE ID *****";
                    }
                }
                message = employeeService.assignTrainees(trainerId, trainer);
                response.getOutputStream().println(message);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method used to post or add trainer details  to server
     * @param {@link HttpServletrequest, HttpServletResponse}request and response
     * @return {noreturn}
     */
    @PostMapping(path = "/Trainer")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String message = "***** Not Inserted ******";

        try {
            if (uri.equals("/spring/Trainer")) {
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = request.getReader();
                 String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String trainerDetails = buffer.toString();
                System.out.println(trainerDetails);
                Trainer trainer = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                          .findAndRegisterModules().readValue(trainerDetails, Trainer.class);
                System.out.println(trainer);
                message = dataValidation(trainer, response);
                response.getOutputStream().println(message);
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
     * Method used to delete trainer details  from server
     * @param {@link HttpServletrequest, HttpServletResponse}request and response
     * @return {noreturn}
     */

    @RequestMapping("Delete")
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "******* there is no record to Delete *******";
        String uri = request.getRequestURI();
        if (uri.equals("/demoservlet/Trainer")) {
            String value = request.getParameter("id");
            int id = Integer.parseInt(value);
            try {
                message = employeeService.deleteTrainerDetails(id);
                response.getOutputStream().println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            int trainerId = Integer.parseInt(request.getParameter("id"));
            Trainer trainer = null;
            message = "******* THERE IS NO ID TO REMOVE ********";

            try {
                trainer = employeeService.showTrainerDetailsById(trainerId);
                int traineeId = Integer.parseInt(request.getParameter("traineeId"));
                message = employeeService.removeIdFromAssignedTrainer(trainerId, removeTrainee(traineeId, trainer));
                response.getOutputStream().println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Method used to remove trainee details  from trainers
     * @param {@link HttpServletrequest, HttpServletResponse}request and response
     * @return {@link Trainer}returns trainer object
     */
  /*  private Trainer removeTrainee(int traineeId, Trainer trainer) throws Exception {
        List<Trainee> list = trainer.getTraineeDetails();
        try {
            if (list.size() >= 1) {
                for (int i = 0; i < list.size(); i++) {

                    if ((list.get(i).getId()) == (traineeId)) {
                        list.remove(i);
                    }
                }
                trainer.setTraineeDetails(list);
            } else {
                String message = "***** THERE IS NO ID TO REMOVE ******";
            }

        } catch (HibernateException e) {
            throw e;
        }
        return trainer;
    }    */
    /**
     * Method used to get trainer details  from server
     *
     * @param {@link HttpServletrequest, HttpServletResponse}request and response
     * @return {noreturn}
     */
    @GetMapping("/Trainers")
    protected ResponseEntity<List<Trainer>> getAllTrainers() throws IOException {


        List<Trainer> showTrainer = null;
        try {
            showTrainer = employeeService.showAllTrainerDetails();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(showTrainer);
    }
    @GetMapping("/Trainer/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Integer id ) throws IOException {

           int ids = Integer.valueOf(id);
          Trainer trainer = null;

          try {
              trainer = employeeService.showTrainerDetailsById(ids);
              if ( null != trainer) {
                  return ResponseEntity.ok().body(trainer);
              }
          } catch (Exception e) {
              throw new RuntimeException(e);
         }
          return ResponseEntity.ok().body(trainer);
    }

    /**
     * Method used to validate  trainer details  from input datas
     * @param {@link HttpServletrequest, HttpServletResponse}request and response
     * @return {@link String}returns status of trainer details
     */
   private String dataValidation(Trainer trainer, HttpServletResponse request) throws IOException {
        Trainer trainers = new Trainer();
        String message = "*** INVALID INPUT **" ;
        String name = trainer.getName();
        if (CommonUtil.stringValidation(name)) {
            trainers.setName(name);
            String mail = trainer.getMail();
            if (CommonUtil.mailValidation(mail)) {
                mail = mail.toLowerCase();
                trainers.setMail(mail);
                Long mobileNumber = trainer.getMobileNumber();
                String number = String.valueOf(mobileNumber);
                if ((CommonUtil.phoneNumberValidation(number))) {
                    trainers.setMobileNumber(Long.parseLong(number));
                    String role = trainer.getRole();
                    if (CommonUtil.stringValidation(role)) {
                        trainers.setRole(role);
                        String panNumber = trainer.getPanNumber();
                        if (CommonUtil.panValidation(panNumber)) {
                            trainers.setPanNumber(panNumber);
                            String address = trainer.getAddress();
                            if (CommonUtil.addressValidation(address)) {
                                address.toUpperCase();
                                trainers.setAddress(address);
                                Long aadharNumber = trainer.getAadharNumber();
                                if (CommonUtil.aadharValidation(aadharNumber)) {
                                    trainers.setAadharNumber(aadharNumber);
                                    try {
                                        
                                        LocalDate dateOfBirth = trainer.getDateOfBirth();
                                        trainers.setDateOfBirth(LocalDate.of(dateOfBirth.getYear()
                                                , dateOfBirth.getMonthValue(), dateOfBirth.getDayOfMonth()));
                                        LocalDate dateOfJoin = trainer.getDateOfJoin();
                                        trainers.setDateOfJoin(LocalDate.of(dateOfJoin.getYear()
                                                , dateOfJoin.getMonthValue(), dateOfJoin.getDayOfMonth()));
                                        message = employeeService.addTrainerDetails(trainers);
                                    } catch (DateTimeException exception) {
                                        throw new DateTimeException( "\n Please Enter The VALID DATE (yyyy-mm-dd)");
                                    }catch(RuntimeException exception){
                                        throw new RuntimeException(""+ exception);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
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
}