package com.i2i.springboot.exception;

import java.lang.RuntimeException;

/**
 * <h> NullListException </h> 
 * <p>
 *  Class used to create the Custom Exception      
 * </p>
 *
 * @version 1.0
 * @author Jaganathan R  
 */
public class NullListException extends RuntimeException {

    public NullListException(String s) {
      
        super(s);
    } 

}