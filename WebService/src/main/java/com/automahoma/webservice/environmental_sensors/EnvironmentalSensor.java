/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.environmental_sensors;

import com.automahoma.webservice.serial_sensors.SensorReadException;

/**
 *
 * @author SESA244648
 */
public class EnvironmentalSensor {
    private String location;
    private float temperature;
    private float humidity;
   
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getTemperature() throws SensorReadException {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() throws SensorReadException {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    } 
    
}
