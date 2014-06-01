/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.environmental_sensors;

import com.automahoma.webservice.serial_sensors.SensorReadException;
import com.automahoma.webservice.serial_sensors.SerialSensorManager;

/**
 *
 * @author SESA244648
 */
public class SerialEnvironmentalSensor extends EnvironmentalSensor {
    
    private static final int SAMPLE_PERIOD = 1000;
    
    private final SerialSensorManager serialSensorManager;
    
    private long lastRead;
    private final int temperatureChannel;
    private final int humidityChannel;
    
    public SerialEnvironmentalSensor(SerialSensorManager serialSensorManager, 
            int temperatureChannel, int humidityChannel) {
        this.serialSensorManager = serialSensorManager;
        this.temperatureChannel = temperatureChannel;
        this.humidityChannel = humidityChannel;
        lastRead = 0;
    }
    
    @Override
    public float getHumidity() throws SensorReadException {
        readSensors();
    
        return super.getHumidity();
    }
    
    @Override
    public float getTemperature() throws SensorReadException {
        readSensors();
        
        return super.getTemperature();
    }
    
    private void readSensors() throws SensorReadException {
        long now = System.currentTimeMillis();
        
        long delta = now - lastRead;
        
        if (delta > SAMPLE_PERIOD) {
            float[] sensorReadings = serialSensorManager.readSensors();
            
            super.setTemperature(sensorReadings[temperatureChannel]);
            super.setHumidity(sensorReadings[humidityChannel]);
        }
    }
}
