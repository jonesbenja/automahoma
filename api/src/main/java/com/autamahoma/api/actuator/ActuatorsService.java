/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.autamahoma.api.actuator;

import com.autamahoma.api.actuator.ActuatorState;
import com.autamahoma.api.actuator.Actuator;
import com.autamahoma.api.actuator.ActuationException;


/**
 *
 * @author Tiffany
 */
public interface ActuatorsService {

    Actuator[] getActuators();

    void setActuator(ActuationSystem system, ActuatorState state) throws ActuationException;

    Actuator getActuator(ActuationSystem system);
}
