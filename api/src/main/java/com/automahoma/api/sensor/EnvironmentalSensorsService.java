/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.api.sensor;

/**
 *
 * @author Tiffany
 */
public interface EnvironmentalSensorsService {

    EnvironmentalSensor[] getEnvironmentalSensors();
    
    EnvironmentalSensor getEnvironmentalSensor(String location, MeasuredQuantity quantity);
}
