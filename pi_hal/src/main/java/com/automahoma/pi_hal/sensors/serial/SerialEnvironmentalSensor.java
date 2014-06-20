/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.sensors.serial;

import com.automahoma.api.sensor.EnvironmentalSensor;
import com.automahoma.api.sensor.SensorReadException;

/**
 *
 * @author SESA244648
 */
public class SerialEnvironmentalSensor extends EnvironmentalSensor {
    
    private final SerialSensorManager serialSensorManager;
    
    private final int channel;
    
    public SerialEnvironmentalSensor(SerialSensorManager serialSensorManager, 
            int channel) {
        this.serialSensorManager = serialSensorManager;
        this.channel = channel;
    }
    
    @Override
    public float getValue() throws SensorReadException {
        readSensors();
    
        return super.getValue();
    }    
    
    private void readSensors() throws SensorReadException {
        float[] sensorReadings = serialSensorManager.readSensors();

        super.setValue(sensorReadings[channel]);
    }
}
