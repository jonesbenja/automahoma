/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.actuators;

import com.autamahoma.api.actuator.ActuationException;
import com.autamahoma.api.actuator.ActuationSystem;
import com.autamahoma.api.actuator.Actuator;
import com.autamahoma.api.actuator.ActuatorState;
import com.autamahoma.api.actuator.ActuatorsService;
import com.automahoma.webservice.TrackedServices;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author SESA244648
 */
public class ActuatorsAdapter {
    private final TrackedServices trackedServices;
    public ActuatorsAdapter(TrackedServices trackedServices) {
        this.trackedServices = trackedServices; 
    }
    
    public void sendActuators(HttpServletResponse resp) throws IOException {
        ActuatorsService actuatorsService = trackedServices.getActuatorsService();
        
        if (actuatorsService == null) {
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Actuators service not available");
            return;
        }
        
        Actuator[] actuators = actuatorsService.getActuators();
        
        JSONArray actuatorsJson = new JSONArray();
        
        for (Actuator actuator : actuators) {
            JSONObject actuatorJson = new JSONObject();
            
            actuatorJson.put("system", actuator.getSystem().toString());
            
            boolean active = actuator.getState() == ActuatorState.On;
            actuatorJson.put("active", active);
            
            actuatorsJson.add(actuatorJson);
        }
        
        actuatorsJson.writeJSONString(resp.getWriter());
    }

    public void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ParseException, ActuationException {
    
        ActuatorsService actuatorsService = trackedServices.getActuatorsService();
        
        if (actuatorsService == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No actuators service present");
            return;
        }
        
        JSONParser parser = new JSONParser();
        JSONArray actuatorsSettings = (JSONArray)parser.parse(req.getReader());
        
        for (Object actuatorSettingObj : actuatorsSettings) {
            JSONObject actuatorSettingJson = (JSONObject)actuatorSettingObj;
            
            String systemStr = (String)actuatorSettingJson.get("system");
            ActuationSystem system = Enum.valueOf(ActuationSystem.class, 
                    systemStr);
            
            Boolean active = (Boolean)actuatorSettingJson.get("active");
            
            ActuatorState state = active ? ActuatorState.On : ActuatorState.Off;
            
            actuatorsService.setActuator(system, state);
        }
    }
}
