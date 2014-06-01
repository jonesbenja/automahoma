/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.actuators;

import com.automahoma.webservice.gpio.GpioController;
import com.automahoma.webservice.gpio.IOInterop;
import com.automahoma.webservice.gpio.SystemIOInterop;
import com.automahoma.webservice.gpio.TestIOInterop;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SESA244648
 */
public class ActuatorsService {

    private static final Actuator[] ACTUATOR_PARAM = new Actuator[0];
    
    private final Map<String, Actuator> actuators;

    private final GpioController gpioController;
    
    public ActuatorsService() throws ActuationException {
        IOInterop ioInterop = new TestIOInterop();
        gpioController = new GpioController(ioInterop);

        actuators = new HashMap();
        actuators.put("AC", new GpioActuator(
                gpioController, 4, "AC", ActuatorState.Off, false));
        actuators.put("Heat", new GpioActuator(
                gpioController, 17, "Heat", ActuatorState.Off, false));
        actuators.put("Fan", new GpioActuator(
                gpioController, 18, "Fan", ActuatorState.Off, false));
        actuators.put("House Fan Low", new GpioActuator(
                gpioController, 27, "House Fan Low", ActuatorState.Off, false));
        actuators.put("House Fan High", new GpioActuator(
                gpioController, 22, "House Fan High", ActuatorState.Off, false));
    }
    
    public Actuator[] getActuators() {
        return actuators.values().toArray(ACTUATOR_PARAM);
    }
    
    public void setActuator(String system, ActuatorState state) throws ActuationException {
        Actuator actuator = actuators.get(system);
        
        if (actuator == null) {
            throw new ActuationException("Actuator " + system + " undefined");
        }
        
        actuator.setState(state);
    }
}
