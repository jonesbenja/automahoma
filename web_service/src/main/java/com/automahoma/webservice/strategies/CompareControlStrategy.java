package com.automahoma.webservice.strategies;

import com.autamahoma.api.actuator.Actuator;
import com.automahoma.api.control.Strategy;
import com.automahoma.api.sensor.EnvironmentalSensor;
import java.util.HashMap;
import java.util.Map;

public class CompareControlStrategy implements Strategy {
    private static final String HYSTERESIS_KEY = "hysteresis";
    
    private boolean active;
    
    private float hysteresis;
   
    public CompareControlStrategy(EnvironmentalSensor sensor,
            Actuator actuator) {
        
    }
    
    public String getName() {
        return "Temperature Control";
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void poll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(Map properties) {
        hysteresis = Float.parseFloat(properties.get(HYSTERESIS_KEY).toString());
    }

    public Map getParameters() {
        Map parameters = new HashMap();
        parameters.put(HYSTERESIS_KEY, hysteresis);
        return parameters;
    }
    
    
}
