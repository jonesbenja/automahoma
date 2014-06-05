/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.contact_sensors;

import com.automahoma.api.sensor.ContactSensor;
import com.automahoma.api.sensor.ContactSensorsService;
import com.automahoma.webservice.TrackedServices;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author SESA244648
 */
public class ContactSensorsAdapter {
    private final TrackedServices trackedServices;
    
    public ContactSensorsAdapter(TrackedServices trackedServices) {
        this.trackedServices = trackedServices;
    }

    public void sendContactSensors(HttpServletResponse resp) throws IOException {
        ContactSensorsService contactSensorsService 
                = trackedServices.getContactSensorsService();
        
        if (contactSensorsService == null) {
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, 
                    "Contact sensors service unavailable");
            return;
        }
        
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
