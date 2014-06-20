/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.api.sensor;

/**
 *
 * @author SESA244648
 */
public class EnvironmentalSensor {
    private String location;
    private MeasuredQuantity measuredQuantity;
    private float value;
   
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MeasuredQuantity getMeasuredQuantity() {
        return measuredQuantity;
    }
    
    public void setMeasuredQuantity(MeasuredQuantity measuredQuantity) {
        this.measuredQuantity = measuredQuantity;
    }

    public float getValue() throws SensorReadException {
        return value;
    }
    
    public void setValue(float value) {
        this.value = value;
    }
}
