/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.serial_sensors;

/**
 *
 * @author SESA244648
 */
public class SensorReadException extends Exception {
    public SensorReadException(Exception cause) {
        super (cause);
    }
    
    public SensorReadException(String message) {
        super (message);
    }
}
