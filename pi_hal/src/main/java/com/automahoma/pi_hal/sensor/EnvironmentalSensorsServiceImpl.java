/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.sensor;

import com.automahoma.pi_hal.sensors.serial.SerialEnvironmentalSensor;
import com.automahoma.api.sensor.EnvironmentalSensor;
import com.automahoma.api.sensor.EnvironmentalSensorsService;
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
                serialSensorManager, 0, 1);
        environmentalSensors[0].setLocation("Inside");
        
        environmentalSensors[1] = new EnvironmentalSensor();
        environmentalSensors[1].setLocation("Outside");
        environmentalSensors[1].setHumidity(53.8f);
        environmentalSensors[1].setTemperature(92.1f);
    }
    
    @Override
    public EnvironmentalSensor[] getEnvironmentalSensors() {
        return environmentalSensors;
    }
    
}
