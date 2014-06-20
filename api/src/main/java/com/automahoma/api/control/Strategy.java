/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.api.control;

import java.util.Map;

/**
 *
 * @author Tiffany
 */
public interface Strategy {
    String getName();
    
    void setActive(boolean active);
    
    boolean isActive();
    
    void poll();
    
    void update(Map properties);
    
    Map getParameters();
}
