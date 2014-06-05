/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.test_hal;

import com.automahoma.api.sensor.EnvironmentalSensor;
import com.automahoma.api.sensor.EnvironmentalSensorsService;

/**
 *
 * @author Tiffany
 */
class EnvironmentalSensorsServiceImpl implements EnvironmentalSensorsService {

    private final EnvironmentalSensor[] environmentalSensors;
    
    public EnvironmentalSensorsServiceImpl() {
        environmentalSensors = new EnvironmentalSensor[2];
        environmentalSensors[0] = new EnvironmentalSensor();
        environmentalSensors[0].setHumidity(23.6f);
        environmentalSensors[0].setLocation("inside");
        environmentalSensors[0].setTemperature(72.1f);
        
        environmentalSensors[1] = new EnvironmentalSensor();
        environmentalSensors[1].setHumidity(63.4f);
        environmentalSensors[1].setLocation("outside");
        environmentalSensors[1].setTemperature(94.2f);
    }

    public EnvironmentalSensor[] getEnvironmentalSensors() {
        return environmentalSensors;
    }
    
}
