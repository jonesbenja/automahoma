/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.test_hal;

import com.automahoma.api.sensor.ContactSensor;
import com.automahoma.api.sensor.ContactSensorsService;

/**
 *
 * @author Tiffany
 */
class ContactSensorsServiceImpl implements ContactSensorsService {

    private final ContactSensor[] contactSensors;
    
    public ContactSensorsServiceImpl() {
        contactSensors = new ContactSensor[5];
        contactSensors[0] = new ContactSensor();
        contactSensors[0].setLocation("Master window");
        contactSensors[1] = new ContactSensor();
        contactSensors[1].setLocation("Violet window");
        contactSensors[2] = new ContactSensor();
        contactSensors[2].setLocation("Cupid window");
        contactSensors[3] = new ContactSensor();
        contactSensors[3].setLocation("Rear sliding door");
        contactSensors[4] = new ContactSensor();
        contactSensors[4].setLocation("Dining window");
    }

    public ContactSensor[] getContactSensors() {
        return contactSensors;
    }
    
}
