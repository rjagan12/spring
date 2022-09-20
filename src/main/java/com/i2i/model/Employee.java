package com.i2i.model;

import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.time.LocalDate;

/**
 * <h> Employee </h>
 * <p>
 * Class is used to define the Employee data types 
 * </p>
 *
 * @version 1.0
 * @author Jaganathan R 
 */
@MappedSuperclass
public class Employee {

    @Column(name = "name")
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "mobile_number")
    private long mobileNumber;

    @Column(name = "mail")
    private String mail;


    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "joiningdate")
    private LocalDate dateOfJoin;

    @Column(name = "aadhar_number")
    private long aadharNumber;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "role")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getIsDeleted() {
	return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
	this.isDeleted = isDeleted;
    }

    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }

    public long getMobileNumber() {
        return mobileNumber;
    } 
    public String getPanNumber() {
        return panNumber;
    }

    public long getAadharNumber() {
        return aadharNumber;
    }

    public String getMail() {
        return mail;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public LocalDate getDateOfJoin() {
        return dateOfJoin;
    } 
    
    public String getAddress() {
        return address;
    }

    public void setName(String name) {   
        this.name = name;
   }

   public void setMail(String mail) {
       this.mail = mail;
   }

   public void setDateOfBirth(LocalDate dateOfBirth) {        
       this.dateOfBirth = dateOfBirth;
   }

   public void setDateOfJoin(LocalDate dateOfJoin) {
       this.dateOfJoin = dateOfJoin;
   }

   public void setMobileNumber(long mobileNumber) {           
       this.mobileNumber = mobileNumber;
   }

   public void setAadharNumber(long aadharNumber) {

       this.aadharNumber = aadharNumber;
   }

   public void setPanNumber(String panNumber) {
       this.panNumber = panNumber;
   }
   
   public void setAddress(String address) {
       this.address = address;
   }
}