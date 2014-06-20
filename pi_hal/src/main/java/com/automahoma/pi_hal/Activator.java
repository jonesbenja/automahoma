package com.automahoma.pi_hal;

import com.autamahoma.api.actuator.ActuatorsService;
import com.automahoma.api.sensor.ContactSensorsService;
import com.automahoma.api.sensor.EnvironmentalSensorsService;
import com.automahoma.pi_hal.actuator.ActuatorsServiceImpl;
import com.automahoma.pi_hal.sensor.ContactSensorsServiceImpl;
import com.automahoma.pi_hal.sensor.EnvironmentalSensorsServiceImpl;
import com.automahoma.pi_hal.sensors.serial.PipeMaster;
import com.automahoma.pi_hal.sensors.serial.SerialSensorManager;
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
        
        PipeMaster pipeMaster = new PipeMaster();
        SerialSensorManager serialSensorManager = new SerialSensorManager(
                pipeMaster);
        
        EnvironmentalSensorsService environmentalSensorsService 
                = new EnvironmentalSensorsServiceImpl(serialSensorManager);
        
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
