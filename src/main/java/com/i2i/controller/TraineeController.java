package com.i2i.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.exception.NullListException;
import com.i2i.model.Trainee;
import com.i2i.model.Trainer;
import com.i2i.service.EmployeeService;
import com.i2i.service.impl.EmployeeServiceImpl;
import com.i2i.util.CommonUtil;
import org.hibernate.HibernateException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
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
@RestController
public class TraineeController extends HttpServlet {

    private static final String inValidData = (" ##********* //INVALID DATA// ************## ");
    private static final String noData = (" ##********* // NO DATA // ************## ");
    private static final Logger logger = LoggerFactory.getLogger(TraineeController.class);
    private static final String line = ("******************************************************");
    private static  EmployeeService employeeService ;
    private  ObjectMapper mapper = new ObjectMapper();
    private HttpServletResponse response;

    public TraineeController(EmployeeService employeeService) {
        this.employeeService = employeeService;

    }

    /**
     * Method used to post trainee details to service from user
     * @param {@link @RequestBody Trainee}trainee object with required details
     * @return {@link String}returns the status of the given details
     */
    @PostMapping(path = "/save_trainee")
    public String addTrainee(@RequestBody Trainee trainee) throws Exception {
        String message = " Failed :: Not Inserted ";
        if (null != trainee) {
            return message = employeeService.addTraineeDetails(trainee);
        } else{
            return message;
        }
    }

    /**
     * Method used to delete trainee details  from server
     * @param {@link @pathVariable int}id
     * @return{@link String}returns the status wit respect to given id
     */
    @DeleteMapping("/delete_trainee/{id}")
    protected String deleteById(@PathVariable int id)
            throws Exception {
        String message = "******* there is no record to Delete *******";
        if (0 < id) {
            return message = employeeService.deleteTraineeDetails(id);
        } else {
            return message +" Enter The Valid Id ";
        }

    }


    /**
     * Method used to put or update trainee details to service from user
     * @param {@link @ResponseBody Trainee}trainee object with upadeted details
     * @return {@link String}returns the Status of the given id
     */
    @PutMapping("/update_trainee")
    public String updateTrainee(@RequestBody Trainee trainee) throws Exception {
        String message =" Failed ::TRAINER DETAILS  NOT UPDATED";
        int id = trainee.getId();
        if (null != trainee) {
            return  employeeService.modifyTraineeDetailsById(id, trainee);
        } else {
            return message;
        }
    }

    /**
     * Method used to get trainee details  from server
     * @param {@link @PathVariable int}id
     * @return {@link Trainee}returns trainee object with respect id
     */
    @GetMapping("/trainee/{id}")
    public Map<String, Object> getTraineeById(@PathVariable int id )
            throws NullListException, Exception {

        Map<String, Object> trainee = null;
        if (CommonUtil.validateTrainees(trainee = employeeService.showTraineeDetailsById(id))) {
           return trainee;
        } else {
            return  trainee;
        }
    }
    /**
     * Method used to get trainee details  from server
     * @param {no param}
     * @return {@link List<Trainee>}returns all the trainee details
     */
    @GetMapping("/trainees")
    @ResponseBody
    public List<Map<String, Object>> getAllTrainees() throws NullListException, Exception {

        List<Map<String, Object>> showTrainees = null;

            showTrainees = employeeService.showAllTraineeDetails();
            return showTrainees;

    }

    /**
     * Method used to put or update trainer details with assigning the trainees
     * @param {@link @pathVariable int, String}trainerId,traineeIds
     * @return {String}Status of trainer details
     */
    @PutMapping("/assign_trainer/{traineeId}/{trainerId}")
    public String assignTrainer(@PathVariable int traineeId,
                                @PathVariable String trainerId) throws Exception {
        String message = "Failed :: Trainee Assign Is Not Updated";
        Trainee trainee = employeeService.displayTraineeDetailsById(traineeId);

        if (null != trainee) {
            String[] trainerIds = trainerId.split(",");

            for (int i = 0; i < trainerIds.length; i++) {
                int id = Integer.valueOf(trainerIds[i]);
                Trainer trainer = employeeService.displayTrainerDetailsById(id);

                if (trainer != null) {
                    trainee.getTrainerDetails().add(trainer);
                } else {
                    message = "**** THERE IS NO TRAINEE ID *****";
                }
            }
            message = employeeService.assignTrainers(traineeId, trainee);
            return message;
        } else {
            return message + "**** THERE IS NO TRAINER ID *****";
        }
    }

    @DeleteMapping("/unassign_trainer/{traineeId}/{trainerId}")
    public String unAssignTrainer(@PathVariable int traineeId,
                                  @PathVariable int trainerId) throws Exception {
        String message = "******* THERE IS NO ID TO REMOVE ******** ";
        System.out.println("message1 : " +message);
        Trainee trainee = null;
        trainee = employeeService.displayTraineeDetailsById(traineeId);
        System.out.println("trainer1 " +trainee);
        if (null != trainee) {
            List<Trainer> list = trainee.getTrainerDetails();
            System.out.println("trainer2 " +list);
            if (list.size() >= 1) {
                for (int i = 0; i < list.size(); i++) {
                    if ((list.get(i).getId()) == (trainerId)) {
                        list.remove(i);
                        System.out.println("trainer3 " +list);
                    }
                }
            } else {
                message = "***** THERE IS NO ID TO REMOVE ******";
            }
            message = employeeService.removeAssignedTrainer(traineeId,trainee);
        }
        return message;
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

    @ExceptionHandler(value = NullListException.class)
    public String exceptionHandler(NullListException exception) {
        return "RECORD NOT FOUND"+exception.getMessage();
    }

}
