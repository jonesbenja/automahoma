package com.automahoma.webservice;

import com.autamahoma.api.actuator.ActuatorsService;
import com.automahoma.api.sensor.ContactSensorsService;
import com.automahoma.api.sensor.EnvironmentalSensorsService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

/**
 *
 * @author Tiffany
 */
public class Activator implements BundleActivator {
    private ServiceTracker httpTracker;
    private ServiceTracker actuatorsServiceTracker;
    private ServiceTracker environmentalSensorsServiceTracker;
    private ServiceTracker contactSensorsServiceTracker;
    
    private TrackedServices trackedServices;
    
    public void start(BundleContext context) {
     
        trackedServices = new TrackedServices();
        
        httpTracker = new ServiceTracker(context, HttpService.class.getName(), null) {
            @Override
            public void removedService(ServiceReference reference, Object service) {
                // HTTP service is no longer available, unregister our servlet...
                try {
                    ((HttpService) service).unregister("/");
                } catch (IllegalArgumentException exception) {
                    // Ignore; servlet registration probably failed earlier on...
                }
            }

            @Override
            public Object addingService(ServiceReference reference) {
                // HTTP service is available, register our servlet...
                HttpService httpService = (HttpService) this.context.getService(reference);
                try {
                    httpService.registerServlet("/", new WebService(trackedServices), null, new Context());
                } catch (Exception exception) {
                    exception.printStackTrace(System.out);
                }
                return httpService;
            }
        };
        
        actuatorsServiceTracker = new ServiceTracker(context, ActuatorsService.class.getName(), null) {
            @Override
            public void removedService(ServiceReference reference, Object service) {
                trackedServices.setActuatorsService(null);
            }

            @Override
            public Object addingService(ServiceReference reference) {
                ActuatorsService actuatorsService 
                        = (ActuatorsService)this.context.getService(reference);
                
                trackedServices.setActuatorsService(actuatorsService);
                
                return actuatorsService;
            }
        };
        
        environmentalSensorsServiceTracker = new ServiceTracker(context, EnvironmentalSensorsService.class.getName(), null) {
            @Override
            public void removedService(ServiceReference reference, Object service) {
                trackedServices.setEnvironmentalSensorsService(null);
            }

            @Override
            public Object addingService(ServiceReference reference) {
                EnvironmentalSensorsService environmentalSensorsService 
                        = (EnvironmentalSensorsService)this.context.getService(reference);
                
                trackedServices.setEnvironmentalSensorsService(environmentalSensorsService);
                
                return environmentalSensorsService;
            }
        };
        
        contactSensorsServiceTracker = new ServiceTracker(context, ContactSensorsService.class.getName(), null) {
            @Override
            public void removedService(ServiceReference reference, Object service) {
                trackedServices.setContactSensorsService(null);
            }

            @Override
            public Object addingService(ServiceReference reference) {
                ContactSensorsService contactSensorsService
                        = (ContactSensorsService)this.context.getService(reference);
                
                trackedServices.setContactSensorsService(contactSensorsService);
                
                return contactSensorsService;
            }
        };
        
        // start tracking all HTTP services...
        httpTracker.open();
        actuatorsServiceTracker.open();
        environmentalSensorsServiceTracker.open();
        contactSensorsServiceTracker.open();
    }

    public void stop(BundleContext context) throws Exception {
        // stop tracking all HTTP services...
        httpTracker.close();
        actuatorsServiceTracker.close();
        environmentalSensorsServiceTracker.close();
        contactSensorsServiceTracker.close();
    }

}
