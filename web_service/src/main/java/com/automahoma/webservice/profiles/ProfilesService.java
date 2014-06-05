/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.profiles;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SESA244648
 */
public class ProfilesService {
    private final Map<String, Profile> profiles;
    
    public ProfilesService() {
        profiles = new HashMap();
        
        Profile nightProfile = new Profile();
        nightProfile.setName("night");
        nightProfile.setMinimumTemperature(65);
        nightProfile.setMaximumTemperature(72);
        
        profiles.put(nightProfile.getName(), nightProfile);
        
        Profile dayProfile = new Profile();
        dayProfile.setName("day");
        dayProfile.setMinimumTemperature(67);
        dayProfile.setMaximumTemperature(78);
        
        profiles.put(dayProfile.getName(), dayProfile);
    }
    
    public synchronized Profile[] getProfiles() {
        return profiles.values().toArray(new Profile[0]);
    }
    
    public synchronized void setProfiles(Profile[] profiles) {
        this.profiles.clear();
        
        for (Profile profile : profiles) {
            this.profiles.put(profile.getName(), profile);
        }
    }
    
    public synchronized Profile getProfile(String name) {
        return profiles.get(name);
    }
}
