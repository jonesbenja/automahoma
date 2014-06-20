/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.gpio;

import com.autamahoma.api.actuator.ActuationSystem;

/**
 *
 * @author SESA244648
 */
public class IOInfo {

    private ActuationSystem system;
    private int pin;
    private IOMode mode;
    
    public void setSystem(ActuationSystem system) {
        this.system = system;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setMode(IOMode mode) {
        this.mode = mode;
    }

    public ActuationSystem getSystem() {
        return system;
    }

    public int getPin() {
        return pin;
    }
    
    public IOMode getIOMode() {
        return mode;
    }
    
}
