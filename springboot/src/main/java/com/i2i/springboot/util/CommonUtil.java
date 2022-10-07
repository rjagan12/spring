package com.i2i.springboot.util;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;

//import org.apache.commons.validator.routines.EmailValidator;

import com.i2i.springboot.exception.NullListException;
import com.i2i.springboot.model.Trainer;
import com.i2i.springboot.model.Trainee;

/**
 * <h> CommonUtil </h> 
 *  class used to validate user input and return status to EmployeeController 
 * 
 * @version 1.0
 * @author Jaganathan R  
 */
public class CommonUtil {
  
   

    /**
     * Method used to generate employee id and return to controller 
     * @param {@link String} id
     * @param {@link Trainer} trainer object
     * @return {@link String}returns generated id 
     */
    public static String generatedId(int idcount) {      
        String id;
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        idcount += 1;
        id = ("I"+year%100+idcount);
       
        return id;
    } 

    /**
     * Method used to vaild the mail id and returns in boolean
     * @param {@link String} mail
     * @return {@link boolean} if vaid returns true or false
     */    
   /* public static boolean mailValidation(String mail) {
      
        return EmailValidator.getInstance().isValid(mail);
    }*/

    /**
     * Method used to vaild the String and returns in boolean
     * @param {@link String} name
     * @return {@link boolean} if vaid returns true or false
     */    
    public static boolean stringValidation(String name) {
  
        String regex = "[a-z\\sA-Z(.)]{2,30}";  
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /**
     * Method used to vaild the String and returns in boolean
     * @param {@link String} name
     * @return {@link boolean} if vaid returns true or false
     */
    public static boolean phoneNumberValidation(String number) {

        String regex = "[6-9]{1}[0-9]{9}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }
    
    /**
     * Method used to generate UUID and return UUID
     * @no param 
     * @return {@link UUID}returns uniqueId
     */
    public static UUID getUUID() {

        UUID uniqueId = UUID.randomUUID();
        return uniqueId;
    }
    
    /**
     * Method used to check the list have values or not 
     * @param {@link List< Trainer>} list of trainers
     * @return {@link boolean}returns true or Exception
     */
    public static boolean validateTrainers(Map<String, Object> Trainer) throws NullListException {
        if (Trainer == null) {
             
            throw new NullListException(" ************* THERE IS NO RECORD IN DATABASE *********** ");
        } 
        return true;
    } 

    /**
     * Method used to check the list have values or not 
     * @param {@link List< Trainee>} list of trainees
     * @return {@link boolean}returns true or Exception
     */
    public static boolean validateTrainees(Map<String, Object> trainee) throws NullListException {
        if (trainee == null) {
            throw new NullListException(" ******** THERE IS NO RECORD IN DATABASE *********** ");
        } 
        return true;
    }

    public static boolean panValidation(String panNumber) {
        String regex = "[a-zA-z0-9]{10}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(panNumber);

        return matcher.matches();
    }

    public static boolean aadharValidation(Long aadharNumber) {
        String number = String.valueOf(aadharNumber);
        String regex = "[0-9]{12}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean addressValidation(String address) {
        String regex = "[a-z\\sA-z0-9]{5,75}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);

        return matcher.matches();
    }
}