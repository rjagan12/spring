package com.i2i.model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * <h> Trainee </h> 
 * <p>
 *  Xlass used to get Trainee details from user and  
 *  set details with setter and getters 
 * </p>
 * 
 * @version 1.0
 * @author Jaganathan R  
 */
@Entity
@Table(name = "trainees")
public class Trainee extends Employee {


    @Column(name = "year_of_passing")
    private LocalDate passOutYear;

    @ManyToMany(targetEntity = Trainer.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "trainers_trainees",
                joinColumns = {@JoinColumn(name = "trainee_id")}) 
    private List<Trainer> trainer;

    public void setPassOutYear(LocalDate passOutYear) {
        this.passOutYear = passOutYear;
    }

    public void setTrainerDetails(List<Trainer> trainer) {
        this.trainer = trainer;
    }

    public List<Trainer> getTrainerDetails() {
        return trainer;
    }

    public LocalDate getPassOutYear() {
        return passOutYear;
    }
}