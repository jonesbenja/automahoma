/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.sensor;

import com.automahoma.pi_hal.sensors.serial.SerialEnvironmentalSensor;
import com.automahoma.api.sensor.EnvironmentalSensor;
import com.automahoma.api.sensor.EnvironmentalSensorsService;
import com.automahoma.api.sensor.MeasuredQuantity;
import com.automahoma.pi_hal.sensors.serial.SerialSensorManager;

/**
 *
 * @author SESA244648
 */
public class EnvironmentalSensorsServiceImpl implements EnvironmentalSensorsService {

    private final EnvironmentalSensor[] environmentalSensors;
    
    public EnvironmentalSensorsServiceImpl(SerialSensorManager serialSensorManager) {
        environmentalSensors = new EnvironmentalSensor[2];
        environmentalSensors[0] = new SerialEnvironmentalSensor(
                serialSensorManager, 0);
        
        environmentalSensors[0].setLocation("Inside");
        environmentalSensors[0].setMeasuredQuantity(MeasuredQuantity.Temperature);
        
        environmentalSensors[1] = new SerialEnvironmentalSensor(
                serialSensorManager, 1);
        
        environmentalSensors[1].setLocation("Inside");
        environmentalSensors[1].setMeasuredQuantity(MeasuredQuantity.Illumination);
    }
    
    @Override
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
