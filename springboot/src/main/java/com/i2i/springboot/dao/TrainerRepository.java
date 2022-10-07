package com.i2i.springboot.dao;

import com.i2i.springboot.model.Employee;

import com.i2i.springboot.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <h> EmployeeDao </h> 
 * <p>
 *  Interface used to get and store employee details from EmployeeDaoImpl and  
 *  returns details as object to EmployeeServiceImpl vice versa
 * </p>
 *
 * @version 1.0
 * @author Jaganathan R  
 */
@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

}