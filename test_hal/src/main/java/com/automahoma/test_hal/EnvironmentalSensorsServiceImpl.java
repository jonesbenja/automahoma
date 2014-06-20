/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.test_hal;

import com.automahoma.api.sensor.EnvironmentalSensor;
import com.automahoma.api.sensor.EnvironmentalSensorsService;
import com.automahoma.api.sensor.MeasuredQuantity;

/**
 *
 * @author Tiffany
 */
class EnvironmentalSensorsServiceImpl implements EnvironmentalSensorsService {

    private final EnvironmentalSensor[] environmentalSensors;
    
    public EnvironmentalSensorsServiceImpl() {
        environmentalSensors = new EnvironmentalSensor[4];
        environmentalSensors[0] = new EnvironmentalSensor();
        environmentalSensors[0].setMeasuredQuantity(MeasuredQuantity.Temperature);
        environmentalSensors[0].setLocation("inside");
        environmentalSensors[0].setValue(72.1f);
        
        environmentalSensors[1] = new EnvironmentalSensor();
        environmentalSensors[1].setLocation("outside");
        environmentalSensors[1].setMeasuredQuantity(MeasuredQuantity.Temperature);
        environmentalSensors[1].setValue(94.2f);
        
        environmentalSensors[2] = new EnvironmentalSensor();
        environmentalSensors[2].setMeasuredQuantity(MeasuredQuantity.Humidity);
        environmentalSensors[2].setLocation("inside");
        environmentalSensors[2].setValue(50.1f);
        
        environmentalSensors[3] = new EnvironmentalSensor();
        environmentalSensors[3].setLocation("outside");
        environmentalSensors[3].setMeasuredQuantity(MeasuredQuantity.Humidity);
        environmentalSensors[3].setValue(84.4f);
        
    }

    public EnvironmentalSensor[] getEnvironmentalSensors() {
        return environmentalSensors;
    }

    public EnvironmentalSensor getEnvironmentalSensor(String location, MeasuredQuantity quantity) {
        EnvironmentalSensor sensor = null;
        
        for (EnvironmentalSensor sensorCheck : environmentalSensors) {
            if (sensorCheck.getLocation().equals(location) 
                    && sensorCheck.getMeasuredQuantity() == quantity) {
                sensor = sensorCheck;
                break;
            }
        }
        
        return sensor;
    }
    
}
