package com.automahoma.test_hal;

import com.autamahoma.api.actuator.ActuatorsService;
import com.automahoma.api.sensor.ContactSensorsService;
import com.automahoma.api.sensor.EnvironmentalSensorsService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration actuatorsRegistration;
    private ServiceRegistration contactSensorsRegistration;
    private ServiceRegistration environmentalSensorsRegistration;
    
    public void start(BundleContext context) throws Exception {
        ActuatorsService actuatorsService = new ActuatorsServiceImpl();
        
        ContactSensorsService contactSensorsService 
                = new ContactSensorsServiceImpl();
        
        EnvironmentalSensorsService environmentalSensorsService 
                = new EnvironmentalSensorsServiceImpl();
        
        actuatorsRegistration = context.registerService(
                ActuatorsService.class.getName(), 
                actuatorsService, null);
        
        contactSensorsRegistration = context.registerService(
                ContactSensorsService.class.getName(), 
                contactSensorsService, null);
        
        environmentalSensorsRegistration = context.registerService(
                EnvironmentalSensorsService.class.getName(), 
                environmentalSensorsService, null);
    }

    public void stop(BundleContext context) throws Exception {
        actuatorsRegistration.unregister();
        contactSensorsRegistration.unregister();
        environmentalSensorsRegistration.unregister();
    }

}
