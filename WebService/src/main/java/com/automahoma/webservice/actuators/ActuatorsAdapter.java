/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.actuators;

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
    private final ActuatorsService actuatorsService;
    public ActuatorsAdapter(ActuatorsService actuatorsService) {
        this.actuatorsService = actuatorsService; 
    }

    public void sendActuators(HttpServletResponse resp) throws IOException {
        Actuator[] actuators = actuatorsService.getActuators();
        
        JSONArray actuatorsJson = new JSONArray();
        
        for (Actuator actuator : actuators) {
            JSONObject actuatorJson = new JSONObject();
            
            actuatorJson.put("system", actuator.getSystem());
            
            boolean active = actuator.getState() == ActuatorState.On;
            actuatorJson.put("active", active);
            
            actuatorsJson.add(actuatorJson);
        }
        
        actuatorsJson.writeJSONString(resp.getWriter());
    }

    public void doPut(HttpServletRequest req) throws IOException, ParseException,
            ActuationException {
        JSONParser parser = new JSONParser();
        JSONArray actuatorsSettings = (JSONArray)parser.parse(req.getReader());
        
        for (Object actuatorSettingObj : actuatorsSettings) {
            JSONObject actuatorSettingJson = (JSONObject)actuatorSettingObj;
            
            String system = (String)actuatorSettingJson.get("system");
            Boolean active = (Boolean)actuatorSettingJson.get("active");
            
            ActuatorState state = active ? ActuatorState.On : ActuatorState.Off;
            
            actuatorsService.setActuator(system, state);
        }
    }
}
