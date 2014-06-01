/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.actuators;

/**
 *
 * @author SESA244648
 */
public abstract class Actuator {
    private String system;
    private ActuatorState state;

    public Actuator(String system) {
        this.system = system;
    }
    
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public ActuatorState getState() {
        return state;
    }

    public void setState(ActuatorState state) throws ActuationException {
        this.state = state;
    }
     
}
