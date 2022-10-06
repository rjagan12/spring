package com.i2i.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.i2i.dao.EmployeeDao;
import com.i2i.dao.impl.EmployeeDaoImpl;
import com.i2i.exception.NullListException;
import com.i2i.model.Trainer;
import com.i2i.model.Trainee;
import com.i2i.service.EmployeeService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

/**
 * <h> EmployeeServiceImpl </h> 
 * <p>
 *  Class used to get employee details from EmployeeDaoImpl and  
 *  returns details as object to EmployeeController vice versa
 *  which is implemented from EmployeeService
 *  </p>
 *
 * @version 1.0
 * @author Jaganathan R  
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao  ;
    private  NullListException message = new NullListException("ID NOT FOUND");

    public EmployeeServiceImpl(EmployeeDaoImpl employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * Method used to add All trainees Details 
     * @param {@link Trainee}  
     * @return {@link String }return status
     */
    @Override
    public String addTraineeDetails(Trainee trainee) throws Exception {

        return employeeDao.insertTraineeDetails(trainee);
    }

    /**
     * Method used to show All trainees Details
     *
     * @param {@link noparam}
     * @return {@link List<Trainee> }return traineeDetails
     */
    @Override
    public List<Map<String, Object>> showAllTraineeDetails() throws Exception {
        List<Map<String, Object>> traineeDetails = new ArrayList<>();
        List<Trainee> trainee = employeeDao.retrieveTraineesDetails();

        for (Trainee trainees: trainee ) {
            traineeDetails.add(getTraineeObject(trainees));
        }
        return traineeDetails;
    }

    /**
     * Method used to remove trainees deatils 
     * @param {@link int }traineeid 
     * @return {@link String }return status
     */
    @Override
    public String deleteTraineeDetails(int id) throws Exception {

        return employeeDao.removeTraineeDetails(id);
    }

    /**
     * Method used to show trainee Details by id
     *
     * @param {@link int}traineeid
     * @return {@link Trainee }return traineeDetails
     */
    @Override
    public Map<String, Object> showTraineeDetailsById(int traineeId) throws Exception {

        Trainee trainee = employeeDao.displayTraineeDetailsById(traineeId);

        return (null != trainee) ? getTraineeObject(trainee) : null;

    }

    /**
     * Method used to update trainee Details by id 
     * @param {@link int, Trainee}traineeid, traineeDetails 
     * @return {@link String}return status
     */
    @Override
    public String modifyTraineeDetailsById(int traineeId, Trainee traineeDetails) throws Exception {

        return employeeDao.updateTraineeDetails(traineeId, traineeDetails);
    }

    /**
     * Method used to add All trainers Details 
     * @param {@link Trainer}  
     * @return {@link String }return status
     */
    @Override
    public String addTrainerDetails(Trainer trainer) throws Exception {

        return employeeDao.insertTrainerDetails(trainer);
    }

    /**
     * Method used to show All trainers Details 
     * @param {@link noparam} 
     * @return {@link List<Trainer> }return trainerDetails
     */
    @Override
    public List<Map<String, Object>> showAllTrainerDetails() throws Exception {
        Map<String, Object> trainerDetails = null;
        List<Map<String, Object>> trainers = new ArrayList<>();
        List<Trainer> list = employeeDao.retrieveTrainersDetails();
        for (Trainer trainer:list) {
            trainers.add( getTrainerObject(trainer));
        }
        return trainers;
    }

    /**
     * Method used to remove trainers deatils 
     * @param {@link int }trainerid 
     * @return {@link String }return status
     */
    @Override
    public String deleteTrainerDetails(int id) throws Exception {

        return employeeDao.removeTrainerDetails(id);
    }

    /**
     * Method used to show trainerDetails by id 
     * @param {@link int}trainerid  
     * @return {@link Trainer }return trainerDetails
     */
    @Override
    public Map<String, Object> showTrainerDetailsById(int trainerId) throws Exception {

        Trainer trainer = null;
        if (null != employeeDao.displayTrainerDetailsById(trainerId)) {
            trainer = employeeDao.displayTrainerDetailsById(trainerId) ;
            return getTrainerObject(trainer);
        } else {
            return (Map<String, Object>) trainer;
        }
    }
    /**
     * Method used to show trainerDetails by id
     * @param {@link int}trainerid
     * @return {@link Trainee }return trainerDetails
     */
    @Override
    public Trainee displayTraineeDetailsById(int traineeId) throws Exception {
        return employeeDao.displayTraineeDetailsById(traineeId);
    }
    /**
     * Method used to show trainerDetails by id
     * @param {@link int}trainerid
     * @return {@link Trainer }return trainerDetails
     */
    @Override
    public Trainer displayTrainerDetailsById(int trainerId) throws Exception {

        Trainer trainer = null;

         if (employeeDao.displayTrainerDetailsById(trainerId) != null) {

             return employeeDao.displayTrainerDetailsById(trainerId);
         } else {
             return trainer;
         }
    }
    /**
     * Method used to modify trainer details by id
     * @param {@link int, Trainer}trainerid and trainer 
     * @return {@link String}
     */
    @Override
    public String modifyTrainerDetailsById(int id, Trainer trainerDetails) throws Exception {

        return employeeDao.updateTrainerDetails(id, trainerDetails);
    }

    /**
     * Method used to assign trainers to trainee 
     * @param {@link int, Trainer}traineeid and  trainer 
     * @return {@link }
     */
    @Override
    public String assignTrainers(int traineeId, Trainee trainee) throws Exception {

        return employeeDao.updateTraineeDetails( traineeId, trainee);
    }   

    /**
     * Method used to assign trainees to trainer 
     * @param {@link int, Trainee}traineeid and trainee 
     * @return {@link }
     */
    @Override
    public String assignTrainees(int trainerId, Trainer trainer) throws Exception {
    
        return employeeDao.updateTrainerDetails( trainerId, trainer);
    }   

    /**
     * Method used to remove trainees from trainer 
     * @param {@link int, Trainer}trainerid trainerDetails
     * @return {@link String }return status
     */
    @Override
    public String removeAssignedTrainee(int trainersId, Trainer trainer) throws Exception {
        
	return employeeDao.updateTrainerDetails(trainersId, trainer); 
    }
 
    /**
     * Method used to remove trainer from trainee 
     * @param {@link int Trainee}traineeid traineeDetails
     * @return {@link String }return status
     */
    @Override   
    public String removeAssignedTrainer(int traineesId, Trainee trainee) throws Exception {

	    return employeeDao.updateTraineeDetails(traineesId,trainee);
    }

    /**
     * Method used to get Trainer Details and created a new collection to avoid
     * Lazy exception while retrieve the trainers
     * @param {@link Trainer}trainerDetails
     * @return {@link Map<String, Object> }returns the trainerDetails
     */
    @Override
    public Map<String, Object> getTrainerObject(Trainer trainer) {
        List<Map<String, Object>> trainee = new ArrayList<>();
        List<Trainee> list = trainer.getTraineeDetails();

        for(Trainee traineeList : list){
            Map<String,Object> listTrainee = new HashMap<>();
            listTrainee.put("traineeId",traineeList.getId());
            listTrainee.put("Trainee Name",traineeList.getName());
            trainee.add(listTrainee);

        }
        Map<String,Object> map = new HashMap<>();
        map.put("trainer Id",trainer.getId());
        map.put("Trainer Name",trainer.getName());
        map.put("Trainer Mail",trainer.getMail());
        map.put("Trainer Role" ,trainer.getRole());
        map.put("Trainer Mobile Number", trainer.getMobileNumber());
        map.put("trainees", trainee);
        return map;
    }

    /**
     * Method used to get Trainee Details and created a new collection to avoid
     * Lazy exception while retrieve the trainees
     * @param {@link Trainee}traineeDetails
     * @return {@link Map<String, Object> }returns the traineeDetails
     */
    @Override
    public Map<String, Object> getTraineeObject(Trainee trainee) {

        List<Map<String, Object>> trainer = new ArrayList<>();
        List<Trainer> list = trainee.getTrainerDetails();

        for(Trainer trainerList : list){
            Map<String,Object> listTrainer = new HashMap<>();
            listTrainer.put("trainerId",trainerList.getId());
            listTrainer.put("Trainer Name",trainerList.getName());
            trainer.add(listTrainer);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("trainee Id",trainee.getId());
        map.put("Trainee Name",trainee.getName());
        map.put("Trainee Mail",trainee.getMail());
        map.put("Trainee Role" ,trainee.getRole());
        map.put("Trainee Mobile Number", trainee.getMobileNumber());
        map.put("trainers", trainer);
        return map;
    }
}