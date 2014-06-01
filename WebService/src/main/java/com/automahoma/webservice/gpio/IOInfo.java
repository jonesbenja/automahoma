/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.gpio;

/**
 *
 * @author SESA244648
 */
public class IOInfo {

    private String function;
    private int pin;
    private IOMode mode;
    
    public void setFunction(String function) {
        this.function = function;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setMode(IOMode mode) {
        this.mode = mode;
    }

    public String getFunction() {
        return function;
    }

    public int getPin() {
        return pin;
    }
    
    public IOMode getIOMode() {
        return mode;
    }
    
}
