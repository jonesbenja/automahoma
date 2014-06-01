/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.environmental_sensors;

import com.automahoma.webservice.serial_sensors.SerialSensorManager;

/**
 *
 * @author SESA244648
 */
public class EnvironmentalSensorsService {

    private final EnvironmentalSensor[] environmentalSensors;
    
    public EnvironmentalSensorsService(SerialSensorManager serialSensorManager) {
        environmentalSensors = new EnvironmentalSensor[2];
        environmentalSensors[0] = new SerialEnvironmentalSensor(
                serialSensorManager, 0, 1);
        environmentalSensors[0].setLocation("Inside");
        
        environmentalSensors[1] = new EnvironmentalSensor();
        environmentalSensors[1].setLocation("Outside");
        environmentalSensors[1].setHumidity(53.8f);
        environmentalSensors[1].setTemperature(92.1f);
    }
    
    public EnvironmentalSensor[] getEnvironmentalSensors() {
        return environmentalSensors;
    }
    
}
