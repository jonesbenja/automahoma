/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.sensor;

import com.automahoma.api.sensor.ContactSensorsService;
import com.automahoma.api.sensor.ContactSensor;

/**
 *
 * @author SESA244648
 */
public class ContactSensorsServiceImpl implements ContactSensorsService {
    private final ContactSensor[] contactSensors;
    
    public ContactSensorsServiceImpl() {
        contactSensors = new ContactSensor[4];
        
        contactSensors[0] = new ContactSensor();
        contactSensors[0].setLocation("Living Room Door");
        contactSensors[0].setOpen(true);
        
        contactSensors[1] = new ContactSensor();
        contactSensors[1].setLocation("Master Bedroom Window");
        contactSensors[1].setOpen(true);
        
        contactSensors[2] = new ContactSensor();
        contactSensors[2].setLocation("Cupid Room Window");
        contactSensors[2].setOpen(false);
        
        contactSensors[3] = new ContactSensor();
        contactSensors[3].setLocation("Violet Room Window");
        contactSensors[3].setOpen(false);
    }
    
    @Override
    public ContactSensor[] getContactSensors() {
        return contactSensors;
    }
    
}
