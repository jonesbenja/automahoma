/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.actuators;

/**
 *
 * @author SESA244648
 */
public class ActuationException extends Exception {
    public ActuationException(String message) {
        super(message);
    }
    
    public ActuationException(Exception cause) {
        super(cause);
    }
}
