/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.webservice;

import com.automahoma.api.sensor.ActuatorsService;
import com.automahoma.api.sensor.ContactSensorsService;
import com.automahoma.api.sensor.EnvironmentalSensorsService;

/**
 *
 * @author Tiffany
 */
public class TrackedServices {
    
    private ActuatorsService actuatorsService;

    private EnvironmentalSensorsService environmentalSensorsService;

    private ContactSensorsService contactSensorsService;
    
    public ActuatorsService getActuatorsService() {
        return actuatorsService;
    }

    public void setActuatorsService(ActuatorsService actuatorsService) {
        this.actuatorsService = actuatorsService;
    }

    public EnvironmentalSensorsService getEnvironmentalSensorsService() {
        return environmentalSensorsService;
    }

    public void setEnvironmentalSensorsService(EnvironmentalSensorsService environmentalSensorsService) {
        this.environmentalSensorsService = environmentalSensorsService;
    }

    public ContactSensorsService getContactSensorsService() {
        return contactSensorsService;
    }

    public void setContactSensorsService(ContactSensorsService contactSensorsService) {
        this.contactSensorsService = contactSensorsService;
    }
    
}

