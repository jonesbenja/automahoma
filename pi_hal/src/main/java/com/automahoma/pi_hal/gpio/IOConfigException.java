/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.gpio;

/**
 *
 * @author SESA244648
 */
public class IOConfigException extends Exception {
    public IOConfigException(String message) {
        super(message);
    }
    
    public IOConfigException(Exception cause) {
        super(cause);
    }
}
