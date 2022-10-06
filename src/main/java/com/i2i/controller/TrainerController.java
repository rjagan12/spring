package com.i2i.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.exception.NullListException;
import com.i2i.model.Trainee;
import com.i2i.model.Trainer;
import com.i2i.service.EmployeeService;
import com.i2i.util.CommonUtil;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    private  NullListException message = new NullListException("ID NOT FOUND");
    private HttpServletResponse response;
    private HttpServletRequest request;

    public TrainerController(EmployeeService employeeService) {
        this.employeeService = employeeService;

    }

    /**
     * Method used to put or update trainer details  to server
     * @param {@link @RequestBody Trainer}trainer
     * @return {String}Status of trainer details
     */
    @PutMapping("/update_trainer")
    public String updateTrainer(@RequestBody Trainer trainer ) throws Exception {
        String message =" Failed ::TRAINER DETAILS  NOT UPDATED";
        int id = trainer.getId();
        if (null != trainer) {
            return  employeeService.modifyTrainerDetailsById(id, trainer);
        } else {
            return message;
        }
    }

    /**
     * Method used to put or update trainer details with assigning the trainees
     * @param {@link @pathVariable int, String}trainerId,traineeIds
     * @return {String}Status of trainer details
     */
    @PutMapping("/assign_trainee/{trainerId}/{traineeId}")
    public String assignTrainee(@PathVariable int trainerId,
                                @PathVariable String traineeId) throws Exception {
       String message = "Failed :: Trainee Assign Is Not Updated";
       Trainer trainer = employeeService.displayTrainerDetailsById(trainerId);

       if (null != trainer) {
           String[] traineeIds = traineeId.split(",");
           for (int i = 0; i < traineeIds.length; i++) {
               int id = Integer.valueOf(traineeIds[i]);
               Trainee trainee = employeeService.displayTraineeDetailsById(id);

               if (trainee != null) {
                   trainer.getTraineeDetails().add(trainee);
               } else {
                   message = "**** THERE IS NO TRAINEE ID *****";
               }
           }
           message = employeeService.assignTrainees(trainerId, trainer);
           return message;
       } else {
           return message + "**** THERE IS NO TRAINER ID *****";

       }
    }

    /**
     * Method used to post or add trainer details  to server
     * @param {@link @RequestBody Trainer}trainer
     * @return {String}Status of trainer details
     */
    @PostMapping(path = "/save_trainer")
    public String addTrainer(@RequestBody Trainer trainer) throws IOException,Exception {
        String message = " Failed :: Not Inserted ";
        if (null != trainer) {
           return message = employeeService.addTrainerDetails(trainer);
        } else{
            return message;
        }
    }
    /**
     * Method used to delete trainer details  from server  by id
     * @param {@link @pathVariable int}id
     * @return {String} returns the status message
     */
    @DeleteMapping("/delete_trainer/{id}")
    public String doDelete(@PathVariable int id)
            throws Exception {
        String message = "******* there is no record to Delete *******";
        if (0 < id) {
            return message = employeeService.deleteTrainerDetails(id);
        } else {
            return message +" Enter The Valid Id ";
        }

    }

    @DeleteMapping("/unassign_trainee/{trainerId}/{traineeId}")
    public String unAssignTrainee(@PathVariable int trainerId,
                                  @PathVariable int traineeId) throws Exception {
        String message = "******* THERE IS NO ID TO REMOVE ******** ";
        System.out.println("message1 : " +message);
        Trainer trainer = null;
        trainer = employeeService.displayTrainerDetailsById(trainerId);
        System.out.println("trainer1 " +trainer);
        if (null != trainer) {
            List<Trainee> list = trainer.getTraineeDetails();
            System.out.println("trainer2 " +list);
            if (list.size() >= 1) {
                for (int i = 0; i < list.size(); i++) {
                    if ((list.get(i).getId()) == (traineeId)) {
                        list.remove(i);
                        System.out.println("trainer3 " +list);
                    }
                }
            } else {
                message = "***** THERE IS NO ID TO REMOVE ******";
            }
            message = employeeService.removeAssignedTrainee(trainerId,trainer);
        }
        return message;
    }


    /**
     * Method used to get All trainer details  from server
     *
     * @param {@link noparam}
     * @return {List<Trainer>} returns list of trainer details
     *
     */

    @GetMapping("/trainer/{id}")
    public Map<String, Object> getTrainerById(@PathVariable int id )
            throws NullListException, Exception {

        Map<String, Object> trainer = null;
        if (CommonUtil.validateTrainers(employeeService.showTrainerDetailsById(id))) {
            trainer = employeeService.showTrainerDetailsById(id);
            return trainer;
        } else {
            return trainer;
        }
    }

    @GetMapping("/trainers")
    @ResponseBody
    public List<Map<String, Object>> getAllTrainers() throws Exception {

        List<Map<String, Object>> showTrainers = null;
        showTrainers = employeeService.showAllTrainerDetails();
        return showTrainers;
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
                                    String companyName = trainer.getcompanyName();
                                    if (CommonUtil.panValidation(companyName)) {
                                        trainers.setcompanyName(companyName);
                                        int experience = trainer.getExperience();
                                        if (0 != experience) {
                                            trainers.setExperience(experience);
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
                                                    "\nPlease Enter The Exerience(< 1) ");
                                        }
                                    } else {
                                        response.getOutputStream().println(message +
                                                "\nPlease Enter The Valid CompanyName ");
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
    @ExceptionHandler(value = NullListException.class)
    public String exceptionHandler(NullListException exception) {
       return "RECORD NOT FOUND"+exception.getMessage();
    }
}