package com.i2i;

import com.i2i.controller.EmployeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;

/**
 * <h> Application </h> 
 * <p>
 *  Class used to run and view mainMenu  
 *  to-get and set details of an Employee
 * </p>
 *
 * @version 1.0
 * @author Jaganathan R  
 */
public class Application {
     
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * Method used to access details of the employee
     */ 
    public static void main (String[] args) throws Exception {

        try {
            EmployeeController.showMenu();

        } catch(InputMismatchException e) {
            logger.error("{}",e);
            EmployeeController.showMenu();

        } catch(Exception e) {
            logger.error("{}",e);

        }
    }
}