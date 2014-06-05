/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.actuator;

import com.automahoma.api.sensor.ActuatorState;
import com.automahoma.api.sensor.Actuator;
import com.automahoma.api.sensor.ActuationException;
import com.automahoma.pi_hal.gpio.GpioController;
import com.automahoma.pi_hal.gpio.IOConfigException;
import com.automahoma.pi_hal.gpio.IOMode;
import com.automahoma.pi_hal.gpio.IOValue;

/**
 *
 * @author SESA244648
 */
public class GpioActuator extends Actuator {
    private final GpioController controller;
    private boolean activeHigh;
    
    public GpioActuator(GpioController controller, int pin, String system, 
            ActuatorState state, boolean activeHigh) throws ActuationException {
        
        super(system);
        
        this.controller = controller;
        this.activeHigh = activeHigh;
        
        try {
            controller.reserve(system, pin, IOMode.Output);
        } catch (IOConfigException ex) {
            throw new ActuationException(ex);
        }
        
        setState(state);
    }
    
    @Override
    public final void setState(ActuatorState state) throws ActuationException {
        if (getState() != state) {
            IOValue ioValue;

            switch (state) {
                case On:
                    ioValue = activeHigh ? IOValue.On : IOValue.Off;
                    break;

                case Off:
                default:
                    ioValue = activeHigh ? IOValue.Off : IOValue.On;
                    break;
            }

            try {
                controller.setValue(getSystem(), ioValue);
            } catch (IOConfigException ex) {
                throw new ActuationException(ex);
            }

            super.setState(state);
        }
    }
}
