/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.api.sensor;

/**
 *
 * @author SESA244648
 */
public class ContactSensor {
    private String location;
    private boolean open;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
    
}
