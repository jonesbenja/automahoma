/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.test_hal;

import com.automahoma.api.sensor.ActuationException;
import com.automahoma.api.sensor.Actuator;
import com.automahoma.api.sensor.ActuatorState;
import com.automahoma.api.sensor.ActuatorsService;

/**
 *
 * @author Tiffany
 */
class ActuatorsServiceImpl implements ActuatorsService {

    private final Actuator[] actuators;
    
    public ActuatorsServiceImpl() {
        actuators = new Actuator[5];
        actuators[0] = new Actuator("HVAC Compressor");
        actuators[1] = new Actuator("HVAC Fan");
        actuators[2] = new Actuator("HVAC Heat");
        actuators[3] = new Actuator("Fan High");
        actuators[4] = new Actuator("Fan Low");
    }

    public Actuator[] getActuators() {
        return actuators;
    }

    public void setActuator(String system, ActuatorState state) throws ActuationException {
        for (Actuator actuator : actuators) {
            if (actuator.getSystem().equals(system)) {
                actuator.setState(state);
                return;
            }
        }
        
        throw new ActuationException("System not present: " + system);
    }
    
}
