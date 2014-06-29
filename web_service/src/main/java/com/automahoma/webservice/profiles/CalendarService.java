/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.webservice.profiles;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tiffany
 */
public class CalendarService {
    
    private final Map<String, CalendarEntry[]> calendarEntries;
    
    public CalendarService() {
        calendarEntries = new HashMap();
    }

    public Map<String, CalendarEntry[]> getCalendarEntries() {
        return calendarEntries;
    }
    
    public void setCalendarEntries(Map<String, CalendarEntry[]> calendarEntries) {
        this.calendarEntries.clear();
        this.calendarEntries.putAll(calendarEntries);
    }
}
