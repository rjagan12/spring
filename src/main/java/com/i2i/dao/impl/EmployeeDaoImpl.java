package com.i2i.dao.impl;

import com.i2i.configure.ConfigureClass;
import com.i2i.dao.EmployeeDao;
import com.i2i.model.Trainee;
import com.i2i.model.Trainer;
import com.i2i.service.EmployeeService;
import com.i2i.service.impl.EmployeeServiceImpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * <h> EmployeeDaoImpl </h> 
 * <p>
 *  Class used to get and store employee details from EmployeeDaoImpl and  
 *  returns details as object to EmployeeServiceImpl vice versa which is 
 *  implemented from EmployeeDao Interface
 * </p>
 *
 * @version 1.0
 * @author Jaganathan R  
 */
public class EmployeeDaoImpl implements EmployeeDao{

    /**
     * Method used to add All trainees Details 
     * @param {@link Trainee}  trainee object
     * @return {@link String }return status
     */
    @Override
    public String insertTraineeDetails(Trainee trainee) throws Exception {
	
	Transaction transaction = null;
	String message = "Details Not Added Successfully";

	try(Session session = ConfigureClass.getFactory().openSession();) {
	    transaction = session.beginTransaction();
	    session.save(trainee);
	    transaction.commit();
	    message = "Trainee details Added Successfully";
	
	} catch(HibernateException e) {

	    if(transaction != null) {
		transaction.rollback();
	    }	  
	    throw e;
	}
	return message;
	
    }

    /**
     * Method used to add All trainers Details 
     * @param {@link Trainer} trainer object
     * @return {@link String }return status
     */
    @Override
    public String insertTrainerDetails(Trainer trainer) throws Exception {
	
	Transaction transaction = null;
	String message = "Details Not Added Successfully";

	try(Session session = ConfigureClass.getFactory().openSession();) {
	    transaction = session.beginTransaction();
	    session.save(trainer);
	    transaction.commit();
	    message = "Trainer details Added Successfully";
	
	} catch(HibernateException e) {

	    if(transaction != null) {
		transaction.rollback();
	    }	  
	    throw e;
	}
	return message;
	
    }

    /**
     * Method used to show traineeDetails by id 
     * @param {@link int}traineeid  
     * @return {@link Trainee }return traineeDetails
     */
    @Override
    public Trainee displayTraineeDetailsById(int traineeId) throws Exception {

        Transaction transaction = null;
        Trainee trainee = null;

        try(Session session = ConfigureClass.getFactory().openSession();) {
            transaction = session.beginTransaction();
            trainee = (Trainee) session.get(Trainee.class, traineeId);

        } catch(HibernateException e) {

            throw e;
        }
        return trainee; 
    }

    /**
     * Method used to show trainerDetails by id 
     * @param {@link int}trainerid  
     * @return {@link Trainer }return trainerDetails
     */
    @Override
    public Trainer displayTrainerDetailsById(int trainerId) throws Exception {

        Transaction transaction = null;
        Trainer trainer = null;

        try(Session session = ConfigureClass.getFactory().openSession();) {
            transaction = session.beginTransaction();
            trainer = (Trainer) session.get(Trainer.class, trainerId);

        } catch(HibernateException e) {

            throw e;
        }
        return trainer;
    }


    /**
     * Method used to show All trainees Details 
     * @param {@link noparam} 
     * @return {@link List<Trainee> }return traineeDetails
     */
    @Override
    public List<Trainee> retrieveTraineesDetails() throws Exception {

        List<Trainee> trainees = new ArrayList<>(); 

        try (Session session = ConfigureClass.getFactory().openSession();) {
            Criteria criteria = session.createCriteria(Trainee.class).add(Restrictions.eq("isDeleted", false));
            trainees = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        } catch(HibernateException e) {

            throw e;
        }
        return trainees;
    }


    /**
     * Method used to show All trainers Details 
     * @param {@link noparam} 
     * @return {@link List<Trainer> }return trainerDetails
     */
    @Override
    public List<Trainer> retrieveTrainersDetails() throws Exception {

        List<Trainer> trainers = new ArrayList<>();
 
        try (Session session = ConfigureClass.getFactory().openSession();) {
            Criteria criteria = session.createCriteria(Trainer.class).add(Restrictions.eq("isDeleted", false));
            trainers = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch(HibernateException e) {

            throw e;
        }
        return trainers;
    }

    /**
     * Method used to remove trainees deatils 
     * @param {@link int}traineeid 
     * @return {@link String }return status
     */
    @Override
    public String removeTraineeDetails(int id) throws Exception {

        Transaction transaction = null;
        String message = "Trainee Details not deleted";

        try (Session session = ConfigureClass.getFactory().openSession();) {
            transaction = session.beginTransaction();
            Trainee trainee = (Trainee) session.get(Trainee.class, id);
            trainee.setIsDeleted(true);
            session.update(trainee);
            message = "trainee details deleted successfully";
            transaction.commit();

        } catch (HibernateException e) {

            if(transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return message;
    }

    /**
     * Method used to remove trainers deatils
     * @param {@link int }trainerid 
     * @return {@link String }return status
     */
    @Override
    public String removeTrainerDetails(int id) throws Exception {

        Transaction transaction = null;
        String message = "Trainer Details not deleted";

        try (Session session = ConfigureClass.getFactory().openSession();) {
            transaction = session.beginTransaction();
            Trainer trainer = (Trainer) session.get(Trainer.class, id);
            trainer.setIsDeleted(true);
            session.update(trainer);
            message = "trainer details deleted successfully";
            transaction.commit();

        } catch (HibernateException e) {

            if(transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return message;
    }


    /**
     * Method used to update trainee Details by id 
     * @param {@link int, Trainee}traineeid, traineeDetails 
     * @return {@link String}return status
     */
    @Override
    public String updateTraineeDetails(int traineeId, Trainee trainee) throws Exception {

        Transaction transaction = null;
        String message = "Trainee details not updated successfully";

        try(Session session = ConfigureClass.getFactory().openSession();) {
            transaction = session.beginTransaction();
            session.update(trainee);
            message = "trainee Details updated Successfully";
            transaction.commit();

        } catch(HibernateException e) {

            if(transaction!=null) {
                transaction.rollback();
            }
            throw e;
        }
        return message;
    }


    /**
     * Method used to update trainer Details by id 
     * @param {@link int, Trainer}trainerid, trainerDetails 
     * @return {@link String}return status
     */
    @Override
    public String updateTrainerDetails(int trainerId, Trainer trainer) throws Exception {

        Transaction transaction = null;
        String message = "Trainer details not updated successfully";

        try(Session session = ConfigureClass.getFactory().openSession();) {
            transaction = session.beginTransaction();
            session.update(trainer);
            message = "Trainer Details updated Successfully";
            transaction.commit();

        } catch(HibernateException e) {

            if(transaction!=null) {
                transaction.rollback();
            }
            throw e;
        }
        return message;

    } 


}