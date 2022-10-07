package com.i2i.springboot.dao;

import com.i2i.springboot.model.Employee;
import com.i2i.springboot.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraineeRepository extends JpaRepository<Trainee, Integer> {

    //String delete(int id);
}
