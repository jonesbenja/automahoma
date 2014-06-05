/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.profiles;

/**
 *
 * @author SESA244648
 */
public class Profile {
    private String name;
    private float minimumTemperature;
    private float maximumTemperature;
  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(float minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public float getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(float maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }
    
    
}
