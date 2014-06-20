/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.test_hal;

import com.autamahoma.api.actuator.ActuationException;
import com.autamahoma.api.actuator.ActuationSystem;
import com.autamahoma.api.actuator.Actuator;
import com.autamahoma.api.actuator.ActuatorState;
import com.autamahoma.api.actuator.ActuatorsService;

/**
 *
 * @author Tiffany
 */
class ActuatorsServiceImpl implements ActuatorsService {

    private final Actuator[] actuators;
    
    public ActuatorsServiceImpl() {
        actuators = new Actuator[5];
        actuators[0] = new Actuator(ActuationSystem.HVAC_Compressor);
        actuators[1] = new Actuator(ActuationSystem.HVAC_Fan);
        actuators[2] = new Actuator(ActuationSystem.HVAC_Heat);
        actuators[3] = new Actuator(ActuationSystem.Fan_High);
        actuators[4] = new Actuator(ActuationSystem.Fan_Low);
    }

    public Actuator[] getActuators() {
        return actuators;
    }

    public void setActuator(ActuationSystem system, ActuatorState state) throws ActuationException {
        for (Actuator actuator : actuators) {
            if (actuator.getSystem().equals(system)) {
                actuator.setState(state);
                return;
            }
        }
        
        throw new ActuationException("System not present: " + system);
    }

    public Actuator getActuator(ActuationSystem system) {
        Actuator actuator = null;
        for (Actuator actuatorCheck : actuators) {
           if (actuatorCheck.getSystem().equals(system)) {
               actuator = actuatorCheck;
               break;
           }
        }
        return actuator;
    }
    
}
