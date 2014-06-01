/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.weather;

import java.util.Date;

/**
 *
 * @author SESA244648
 */
public class Weather {
    private float temperature;
    private float humidity;
    private float precipitation;
    private Condition[] futureConditions;
    private Date currentTime;
    
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
    
    public float getTemperature() {
        return temperature;
    }
    
    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
    
    public float getHumidity() {
        return humidity;
    }
    
    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }
    
    public float getPrecipitation() {
        return precipitation;
    }
    
    public void setFutureConditions(Condition[] futureConditions) {
        this.futureConditions = futureConditions;
    }
    
    public Condition[] getFutureConditions() {
        return futureConditions;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
    
    public Date getCurrentTime() {
        return currentTime;
    }
}
