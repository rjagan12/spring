package com.i2i.configure;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.i2i.model.Trainer;
import com.i2i.model.Trainee;

/**
 * <h> ConfigureClass </h> 
 * <p>
 *  Class used to create object for Session factory and  
 *  returns to EmployeeDaoImpl Class
 * </p>
 *
 * @version 1.0
 * @author Jaganathan R  
 */
public class ConfigureClass {

    private static SessionFactory factory;
    private static Logger logger = LoggerFactory.getLogger(ConfigureClass.class);

    /**
     * Method used to access package and create Object  
     * @noparam  
     * @return {@link SessionFactory} returns object
     */ 
    public static SessionFactory getFactory() {

        try {
            factory = new Configuration().configure().
                             addPackage("com.i2i.model").
                             addAnnotatedClass(Trainee.class).
                             addAnnotatedClass(Trainer.class).buildSessionFactory();

        } catch (Throwable e) {
            logger.error("Failed to create sessoionFactory object"+"{}", e);
            throw new ExceptionInInitializerError(e);
        }
        return factory;
    }
}