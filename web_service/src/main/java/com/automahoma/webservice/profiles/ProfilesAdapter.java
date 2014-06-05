/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.profiles;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author SESA244648
 */
public class ProfilesAdapter {
    private final ProfilesService profilesService;
    
    public ProfilesAdapter(ProfilesService profilesService) {
        this.profilesService = profilesService;
    }

    public void sendProfiles(HttpServletResponse resp) throws IOException {
        Profile[] profiles = profilesService.getProfiles();
        JSONArray profilesJson = new JSONArray();
        for (Profile profile : profiles) {
            JSONObject profileJson = new JSONObject();
            
            profileJson.put("name", profile.getName());
            profileJson.put("minimum_temperature", profile
                    .getMinimumTemperature());
            profileJson.put("maximum_temperature", profile
                    .getMaximumTemperature());
            
            profilesJson.add(profileJson);
        }
        
        profilesJson.writeJSONString(resp.getWriter());
    }

    public void doPut(HttpServletRequest req) 
            throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray profilesJson = (JSONArray)parser.parse(req.getReader());
        
        int size = profilesJson.size();
        Profile[] profiles = new Profile[size];
        for (int i = 0; i < size; i++) {
            JSONObject profileJson = (JSONObject)profilesJson.get(i);
            
            profiles[i] = new Profile();
            float maximumTemperature = Float.parseFloat((String)profileJson
                    .get("maximum_temperature"));
            profiles[i].setMaximumTemperature(maximumTemperature);
            
            float minimumTemperature = Float.parseFloat((String)profileJson
                    .get("minimum_temperature"));
            profiles[i].setMinimumTemperature(minimumTemperature);
            
            profiles[i].setName((String)profileJson.get("name"));
        }
        
        profilesService.setProfiles(profiles);        
    }
}
