/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.contact_sensors;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author SESA244648
 */
public class ContactSensorsAdapter {
    private final ContactSensorsService contactSensorsService;
    
    public ContactSensorsAdapter(ContactSensorsService contactSensorsService) {
        this.contactSensorsService = contactSensorsService;
    }

    public void sendContactSensors(HttpServletResponse resp) throws IOException {
        ContactSensor[] contactSensors = contactSensorsService.getContactSensors();
        
        JSONArray contactSensorsJson = new JSONArray();
        for (ContactSensor contactSensor : contactSensors) {
            JSONObject contactSensorJson = new JSONObject();
            
            contactSensorJson.put("location", contactSensor.getLocation());
            contactSensorJson.put("condition", contactSensor.isOpen() ? "Open" : "Closed");
            
            contactSensorsJson.add(contactSensorJson);
        }
        
        contactSensorsJson.writeJSONString(resp.getWriter());
    }
    
}
