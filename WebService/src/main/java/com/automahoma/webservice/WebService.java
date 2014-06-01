/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice;


import com.automahoma.webservice.actuators.ActuationException;
import com.automahoma.webservice.environmental_sensors.EnvironmentalSensorsService;
import com.automahoma.webservice.actuators.ActuatorsAdapter;
import com.automahoma.webservice.actuators.ActuatorsService;
import com.automahoma.webservice.contact_sensors.ContactSensorsAdapter;
import com.automahoma.webservice.contact_sensors.ContactSensorsService;
import com.automahoma.webservice.environmental_sensors.EnvironmentalSensorsAdapter;
import com.automahoma.webservice.profiles.ProfilesAdapter;
import com.automahoma.webservice.profiles.ProfilesService;
import com.automahoma.webservice.serial_sensors.PipeMaster;
import com.automahoma.webservice.serial_sensors.SerialSensorManager;
import com.automahoma.webservice.weather.WeatherAdapter;
import com.automahoma.webservice.weather.WeatherService;
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
    
    private final WeatherAdapter weatherAdapter;
    private final ProfilesAdapter profilesAdapter;
    private final ActuatorsAdapter actuatorsAdapter;
    private final EnvironmentalSensorsAdapter environmentalSensorsAdapter;
    private final ContactSensorsAdapter contactSensorsAdapter;
    
    public WebService() throws ActuationException, FileNotFoundException {
        
        final WeatherService weatherService = new WeatherService();
        weatherAdapter = new WeatherAdapter(weatherService);
        
        final ProfilesService profilesService = new ProfilesService();
        profilesAdapter = new ProfilesAdapter(profilesService);
        
        final ActuatorsService actuatorsService = new ActuatorsService();
        actuatorsAdapter = new ActuatorsAdapter(actuatorsService);
        
        final PipeMaster pipeMaster = new PipeMaster();
        final SerialSensorManager serialSensorManager = new SerialSensorManager(pipeMaster);
        final EnvironmentalSensorsService environmentalSensorsService = new EnvironmentalSensorsService(serialSensorManager);
        environmentalSensorsAdapter = new EnvironmentalSensorsAdapter(environmentalSensorsService);
        
        final ContactSensorsService contactSensorsService = new ContactSensorsService();
        contactSensorsAdapter = new ContactSensorsAdapter(contactSensorsService);
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
                    actuatorsAdapter.doPut(req);
                    
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
    
    private void doGetStaticContent(String path, HttpServletResponse resp)
            throws IOException {
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream(path);
        
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
