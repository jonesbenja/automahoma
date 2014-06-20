/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.actuator;

import com.autamahoma.api.actuator.ActuatorState;
import com.autamahoma.api.actuator.Actuator;
import com.autamahoma.api.actuator.ActuationException;
import com.autamahoma.api.actuator.ActuationSystem;
import com.autamahoma.api.actuator.ActuatorsService;
import com.automahoma.pi_hal.gpio.GpioController;
import com.automahoma.pi_hal.gpio.IOInterop;
import com.automahoma.pi_hal.gpio.TestIOInterop;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SESA244648
 */
public class ActuatorsServiceImpl implements ActuatorsService {

    private static final Actuator[] ACTUATOR_PARAM = new Actuator[0];
    
    private final Map<ActuationSystem, Actuator> actuators;

    private final GpioController gpioController;
    
    public ActuatorsServiceImpl() throws ActuationException {
        IOInterop ioInterop = new TestIOInterop();
        gpioController = new GpioController(ioInterop);

        actuators = new HashMap();
        actuators.put(ActuationSystem.HVAC_Compressor, new GpioActuator(
                gpioController, 4, ActuationSystem.HVAC_Compressor, ActuatorState.Off, false));
        actuators.put(ActuationSystem.HVAC_Heat, new GpioActuator(
                gpioController, 17, ActuationSystem.HVAC_Heat, ActuatorState.Off, false));
        actuators.put(ActuationSystem.HVAC_Fan, new GpioActuator(
                gpioController, 18, ActuationSystem.HVAC_Fan, ActuatorState.Off, false));
        actuators.put(ActuationSystem.Fan_Low, new GpioActuator(
                gpioController, 27, ActuationSystem.Fan_Low, ActuatorState.Off, false));
        actuators.put(ActuationSystem.Fan_High, new GpioActuator(
                gpioController, 22, ActuationSystem.Fan_High, ActuatorState.Off, false));
    }
    
    @Override
    public Actuator[] getActuators() {
        return actuators.values().toArray(ACTUATOR_PARAM);
    }
    
    @Override
    public void setActuator(ActuationSystem system, ActuatorState state) throws ActuationException {
        Actuator actuator = actuators.get(system);
        
        if (actuator == null) {
            throw new ActuationException("Actuator " + system + " undefined");
        }
        
        actuator.setState(state);
    }

    public Actuator getActuator(ActuationSystem system) {
        return actuators.get(system);
    }
}
