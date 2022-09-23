package com.i2i.util;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import org.apache.commons.validator.routines.EmailValidator;

import com.i2i.exception.NullListException;
import com.i2i.model.Trainer;
import com.i2i.model.Trainee;

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
    public static boolean mailValidation(String mail) {  
      
        return EmailValidator.getInstance().isValid(mail);
    }

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
    public static boolean validateTrainers(List< Trainer> Trainers) throws NullListException {
        if (Trainers.size() == 0) {
             
            throw new NullListException(" ************* THERE IS NO RECORD IN DATABAE *********** ");
            
        } 
        return true;
    } 

    /**
     * Method used to check the list have values or not 
     * @param {@link List< Trainee>} list of trainees
     * @return {@link boolean}returns true or Exception
     */
    public static boolean validateTrainees(List< Trainee> Trainees) throws NullListException {
        if (Trainees.size()==0) {
            throw new NullListException(" ******** THERE IS NO RECORD IN DATABASE *********** ");
           
        } 
        return true;
    }
  
}   