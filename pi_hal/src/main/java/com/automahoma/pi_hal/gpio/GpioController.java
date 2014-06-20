/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.gpio;

import com.autamahoma.api.actuator.ActuationSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SESA244648
 */
public class GpioController {
    
    private final Map<ActuationSystem, IOInfo> ioInfoBySystem;
    private final Map<Integer, IOInfo> ioInfoByPin;
    
    private final IOInterop systemIOInterop;
    
    public GpioController(IOInterop systemIOInterop) {
        this.systemIOInterop = systemIOInterop;
        
        ioInfoBySystem = new HashMap();
        ioInfoByPin = new HashMap();
    }
    
    public void reserve(ActuationSystem system, int pin, IOMode mode) throws IOConfigException {
        if (ioInfoBySystem.containsKey(system)) {
            throw new IOConfigException("Function already defined with name " + system.toString());
        }
        
        if (ioInfoByPin.containsKey(pin)) {
            throw new IOConfigException("Pin " + pin
                    + " already defined for function" 
                    + ioInfoByPin.get(pin).getSystem());
        }
        
        if (!systemIOInterop.isExported(pin)) {
            try {
                systemIOInterop.export(pin);
            } catch (IOException ex) {
                throw new IOConfigException(ex);
            }
        }
        
        try {
            systemIOInterop.setIOMode(pin, mode);
        } catch (IOException ex) {
            throw new IOConfigException(ex);
        }
        
        IOInfo info = new IOInfo();
        info.setSystem(system);
        info.setPin(pin);
        info.setMode(mode);
        
        ioInfoBySystem.put(system, info);
        ioInfoByPin.put(pin, info);
    }
    
    public void release(String function) throws IOConfigException {
        IOInfo info = ioInfoBySystem.get(function);
        if (info == null) {
            throw new IOConfigException("Function " + function + " not defined");
        }
        
        ioInfoBySystem.remove(function);
        ioInfoByPin.remove(info.getPin());
        
        try {
            systemIOInterop.unexport(info.getPin());
        } catch (IOException ex) {
            throw new IOConfigException(ex);
        }
    }
    
    public IOValue getValue(String function) throws IOConfigException {
        IOInfo info = ioInfoBySystem.get(function);
        if (info == null) {
            throw new IOConfigException("Function " + function + " not defined");
        }
        
        if (info.getIOMode() == IOMode.Output) {
            throw new IOConfigException("Attempting to get value from an output port");
        }
        
        try {
            return systemIOInterop.getValue(info.getPin());
        } catch (FileNotFoundException ex) {
            throw new IOConfigException(ex);
        } catch (IOException ex) {
            Logger.getLogger(GpioController.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOConfigException(ex);
        }
    }
    
    public void setValue(ActuationSystem system, IOValue value) throws IOConfigException {
        IOInfo info = ioInfoBySystem.get(system);
        if (info == null) {
            throw new IOConfigException("Function " + system.toString() + " not defined");
        }

        if (info.getIOMode() == IOMode.Input) {
            throw new IOConfigException("Attempting to set value for an input port");
        }
        
        try {
            systemIOInterop.setValue(info.getPin(), value);
        } catch (IOException ex) {
            throw new IOConfigException(ex);
        }
    }
    
    
}
