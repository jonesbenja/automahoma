/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autamahoma.api.actuator;

/**
 *
 * @author SESA244648
 */
public class Actuator {
    private ActuationSystem system;
    private ActuatorState state;

    public Actuator(ActuationSystem system) {
        this.system = system;
    }
    
    public ActuationSystem getSystem() {
        return system;
    }

    public void setSystem(ActuationSystem system) {
        this.system = system;
    }

    public ActuatorState getState() {
        return state;
    }

    public void setState(ActuatorState state) throws ActuationException {
        this.state = state;
    }
     
}
