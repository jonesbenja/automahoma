/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice;


import com.autamahoma.api.actuator.ActuationException;
import com.autamahoma.api.actuator.ActuationSystem;
import com.autamahoma.api.actuator.Actuator;
import com.automahoma.api.sensor.EnvironmentalSensor;
import com.automahoma.api.sensor.MeasuredQuantity;
import com.automahoma.webservice.actuators.ActuatorsAdapter;
import com.automahoma.webservice.contact_sensors.ContactSensorsAdapter;
import com.automahoma.webservice.environmental_sensors.EnvironmentalSensorsAdapter;
import com.automahoma.webservice.profiles.CalendarAdapter;
import com.automahoma.webservice.profiles.CalendarService;
import com.automahoma.webservice.profiles.ProfilesAdapter;
import com.automahoma.webservice.profiles.ProfilesService;
import com.automahoma.webservice.strategies.CompareControlStrategy;
import com.automahoma.webservice.strategies.CompareStrategyActivation;
import com.automahoma.webservice.strategies.StrategiesAdapter;
import com.automahoma.webservice.strategies.StrategiesService;
import com.automahoma.webservice.weather.WeatherAdapter;
import com.automahoma.webservice.weather.WeatherService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.ParseException;

/**
 *
 * @author SESA244648
 */
public class WebService extends HttpServlet {
    
    //False uses source code for web resources. 
    //Change to true when deploying to reference resources in JAR
    private static final boolean JAR_MODE = false;
    private static final String PROJECT_DIR = "c:/Users/Tiffany/automahoma/";
    private static final String EXPLODED_DIR = PROJECT_DIR + "web_service/src/main/resources/";
    
    private final WeatherAdapter weatherAdapter;
    private final ProfilesAdapter profilesAdapter;
    private final ActuatorsAdapter actuatorsAdapter;
    private final EnvironmentalSensorsAdapter environmentalSensorsAdapter;
    private final ContactSensorsAdapter contactSensorsAdapter;
    private final StrategiesAdapter strategiesAdapter;
    private final CalendarAdapter calendarAdapter;
    
    public WebService(TrackedServices trackedServices)
            throws ActuationException, FileNotFoundException {
        
        final WeatherService weatherService = new WeatherService();
        weatherAdapter = new WeatherAdapter(weatherService);
        
        final CalendarService calendarService = new CalendarService();
        calendarAdapter = new CalendarAdapter(calendarService);
        
        final ProfilesService profilesService = new ProfilesService();
        profilesAdapter = new ProfilesAdapter(profilesService);
        
        actuatorsAdapter = new ActuatorsAdapter(trackedServices);
        
        environmentalSensorsAdapter = new EnvironmentalSensorsAdapter(
                trackedServices);
        
        contactSensorsAdapter = new ContactSensorsAdapter(
                trackedServices);
        
        final EnvironmentalSensor insideTemperatureSensor = trackedServices
                .getEnvironmentalSensorsService().getEnvironmentalSensor(
                        "inside", MeasuredQuantity.Temperature);
        
        final Actuator compressorActuator = trackedServices
                .getActuatorsService().getActuator(ActuationSystem.HVAC_Compressor);
        
        final CompareControlStrategy coolingStrategy 
                = new CompareControlStrategy("Thermostat Compressor Cooling", 
                        insideTemperatureSensor, compressorActuator, 
                        CompareStrategyActivation.WhenAbove);
        
        final Actuator gasActuator = trackedServices
                .getActuatorsService().getActuator(ActuationSystem.HVAC_Heat);
        
        final CompareControlStrategy heatingStrategy
                = new CompareControlStrategy("Thermostat Gas Heating",
                        insideTemperatureSensor, gasActuator,
                        CompareStrategyActivation.WhenBelow);
        
        final StrategiesService strategiesService = new StrategiesService();
        strategiesService.addStrategy(coolingStrategy);
        strategiesService.addStrategy(heatingStrategy);
        
        strategiesAdapter = new StrategiesAdapter(strategiesService);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        String path = req.getPathInfo();
        String[] pathList = path.split("/");
        
        if (pathList.length > 1 && pathList[1].equals("content")) {
            doGetStaticContent(path, resp);
        } else {
            doGetDynamicContent(pathList, req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String path = req.getPathInfo();
        String[] pathList = path.split("/");
        
        if (pathList.length > 1) {
            try {
                if (pathList[1].equals("profiles")) {
                    profilesAdapter.doPut(req);

                } else if (pathList[1].equals("actuators")) {
                    actuatorsAdapter.doPut(req, resp);
                    
                } else if (pathList[1].equals("strategies")) {
                    strategiesAdapter.doPost(req);
                    
                } else if (pathList[1].equals("calendar")) {
                    calendarAdapter.doPost(req);
                    
                }
                
            } catch (ParseException ex) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (ActuationException ex) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Actuator Error");
                Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, path);
        }
    }
    
    private InputStream getResource(String path) throws FileNotFoundException {
        InputStream is;
        if (JAR_MODE) {
            is = this.getClass().getClassLoader()
                .getResourceAsStream(path);
        } else {
            is = new FileInputStream(EXPLODED_DIR + path);
        }
        return is;
    }
    
    private void doGetStaticContent(String path, HttpServletResponse resp)
            throws IOException {
        
        InputStream is = getResource(path);
        
        int fileNameIndex = path.lastIndexOf("/");
        String fileName = path.substring(fileNameIndex);
        String contentType = MimeMap.getMimeType(fileName);
        
        resp.setContentType(contentType);
        
        ServletOutputStream os = resp.getOutputStream();
        byte[] bufferData = new byte[1024];
        int read;
        while((read = is.read(bufferData))!= -1){
            os.write(bufferData, 0, read);
        }
        os.flush();
        os.close();
        is.close();
    }

    private void doGetDynamicContent(String[] pathList, HttpServletRequest req,
            HttpServletResponse resp) throws IOException {
        
        resp.setContentType(MimeMap.JS);
        
        boolean valid = true;
        if (pathList.length > 1) {            
            resp.setContentType("application/json");
                
            if (pathList[1].equals("forecast")) {
                weatherAdapter.sendWeather(resp);                
            } else if (pathList[1].equals("profiles")) {
                profilesAdapter.sendProfiles(resp);
            } else if (pathList[1].equals("actuators")) {
                actuatorsAdapter.sendActuators(resp);
            } else if (pathList[1].equals("environmentalSensors")) {
                environmentalSensorsAdapter.sendEnvironmentalSensors(resp);
            } else if (pathList[1].equals("contactSensors")) {
                contactSensorsAdapter.sendContactSensors(resp);
            } else if (pathList[1].equals("strategies")) {
                strategiesAdapter.sendStrategies(resp);
            } else if (pathList[1].equals("calendar")) {
                calendarAdapter.sendCalendar(resp);
            } else {
                valid = false;
            } 
        } else {
            valid = false;
        }
        
        if (!valid) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
    }

    
}
