/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autamahoma.api.actuator;

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
