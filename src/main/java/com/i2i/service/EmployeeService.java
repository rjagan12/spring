package com.i2i.service;

import java.util.List;
import java.util.Map;

import com.i2i.model.Trainer;
import com.i2i.model.Trainee;

/**
 * <h> EmployeeService </h> 
 *  Interface used to get employee details from EmployeeDaoImpl and  
 *  returns details as object to EmployeeController vice versa
 * 
 * @version 1.0
 * @author Jaganathan R  
 */
public interface EmployeeService {

    /**
     * Method used to add All trainees Details 
     * @param {@link Trainee}  
     * @return {@link String }return status
     */
    public String addTraineeDetails(Trainee trainee) throws Exception;

     /**
     * Method used to show All trainees Details 
     * @param {@link noparam}  
     * @return {@link List<Trainee> }return traineeDetails
     */
    public List<Trainee> showAllTraineeDetails() throws Exception;


    /**
     * Method used to remove trainees deatils 
     * @param {@link int }traineeid 
     * @return {@link String }return status
     */
    public String deleteTraineeDetails(int id) throws Exception;
 
    /**
     * Method used to show trainee Details by id
     *
     * @param {@link int}traineeid
     * @return {@link Trainee }return traineeDetails
     */
    public Trainee showTraineeDetailsById(int traineeId) throws Exception;

    /**
     * Method used to update trainee Details by id 
     * @param {@link int, Trainee}traineeid, traineeDetails 
     * @return {@link String}return status
     */
    public String modifyTraineeDetailsById(int traineeId, Trainee traineeDetails) throws Exception;

    /**
     * Method used to add All trainers Details 
     * @param {@link Trainer}  
     * @return {@link String }return status
     */
    public String addTrainerDetails(Trainer trainer) throws Exception;
 
    /**
     * Method used to show All trainers Details 
     * @param {@link noparam} 
     * @return {@link List<Trainer> }return trainerDetails
     */
    public List<Trainer> showAllTrainerDetails() throws Exception;

    /**
     * Method used to remove trainers deatils 
     * @param {@link int }trainerid 
     * @return {@link String }return status
     */
    public String deleteTrainerDetails(int id) throws Exception;

    /**
     * Method used to show trainerDetails by id 
     * @param {@link int}trainerid  
     * @return {@link Trainer }return trainerDetails
     */
    public Trainer showTrainerDetailsById(int trainerId) throws Exception;

    /**
     * Method used to modify trainer details by id
     * @param {@link int, Trainer}trainerid and trainer 
     * @return {@link String}
     */
    public String modifyTrainerDetailsById(int id, Trainer trainerDetails) throws Exception;

    /**
     * Method used to assign trainers to trainee 
     * @param {@link String, Trainer}traineeid and  trainer 
     * @return {@link }
     */
    public String assignTrainers(int traineeId, Trainee trainee) throws Exception;

    /**
     * Method used to assign trainees to trainer 
     * @param {@link int, Trainee}traineeid and trainee 
     * @return {@link }
     */
    public String assignTrainees(int trainerId, Trainer trainer) throws Exception;


    /**
     * Method used to remove trainers from trainee 
     * @param {@link int }trainerid 
     * @return {@link String }return status
     */
    String removeAssignedTrainee(int trainersId, Trainer trainer) throws Exception;

    /**
     * Method used to remove trainee from trainer 
     * @param {@link String}traineeid 
     * @return {@link String }return status
     */
    String removeAssignedTrainer(int traineesId, Trainee trainee) throws Exception;

    public Map<String, Object> getTrainerObject(Trainer trainer);
    public Map<String, Object> getTraineeObject(Trainee trainee);
}