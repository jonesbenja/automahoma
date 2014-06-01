/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.contact_sensors;

/**
 *
 * @author SESA244648
 */
public class ContactSensorsService {
    private final ContactSensor[] contactSensors;
    
    public ContactSensorsService() {
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
    
    public ContactSensor[] getContactSensors() {
        return contactSensors;
    }
    
}
