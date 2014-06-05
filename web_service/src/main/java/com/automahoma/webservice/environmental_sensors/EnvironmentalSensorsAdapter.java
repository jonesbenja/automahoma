/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.environmental_sensors;

import com.automahoma.api.sensor.EnvironmentalSensor;
import com.automahoma.api.sensor.EnvironmentalSensorsService;
import com.automahoma.api.sensor.SensorReadException;
import com.automahoma.webservice.TrackedServices;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author SESA244648
 */
public class EnvironmentalSensorsAdapter {
    private final TrackedServices trackedServices;

    public EnvironmentalSensorsAdapter(
            TrackedServices trackedServices) {
        this.trackedServices = trackedServices;
    }

    public void sendEnvironmentalSensors(HttpServletResponse resp) 
            throws IOException {
        
        EnvironmentalSensorsService environmentalSensorsService
                = trackedServices.getEnvironmentalSensorsService();
        
        if (environmentalSensorsService == null) {
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, 
                    "Environmental sensors service not available");
        }
        
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
