/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.environmental_sensors;

import com.automahoma.webservice.serial_sensors.SensorReadException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author SESA244648
 */
public class EnvironmentalSensorsAdapter {
    private final EnvironmentalSensorsService environmentalSensorsService;

    public EnvironmentalSensorsAdapter(
            EnvironmentalSensorsService environmentalSensorsService) {
        
        this.environmentalSensorsService = environmentalSensorsService;
    }

    public void sendEnvironmentalSensors(HttpServletResponse resp) 
            throws IOException {
        
        EnvironmentalSensor[] sensors = environmentalSensorsService
                .getEnvironmentalSensors();
        
        JSONArray sensorsJson = new JSONArray();
        for (EnvironmentalSensor sensor : sensors) {
            try {
                JSONObject sensorJson = new JSONObject();
                
                sensorJson.put("location", sensor.getLocation());
                sensorJson.put("temperature", sensor.getTemperature());
                sensorJson.put("humidity", sensor.getHumidity());            
                sensorsJson.add(sensorJson);
                
            } catch (SensorReadException ex) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        ex.getMessage());
                return;
            }
        }
        
        sensorsJson.writeJSONString(resp.getWriter());
    }
    
}
