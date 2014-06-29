/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.webservice.profiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Tiffany
 */
public class CalendarAdapter {
    private static final CalendarEntry[] CALENDAR_ENTRY_PARAMS 
            = new CalendarEntry[0];
    
    private final CalendarService calendarService;
    
    public CalendarAdapter(CalendarService calendarService) {
        this.calendarService = calendarService;
    }
    
    public void sendCalendar(HttpServletResponse resp) throws IOException {
        Map<String, CalendarEntry[]> days = calendarService.getCalendarEntries();
        
        JSONArray daysJson = new JSONArray();
        
        for (Map.Entry<String, CalendarEntry[]> day : days.entrySet()) {
            JSONObject dayJson = new JSONObject();
            dayJson.put("dayName", day.getKey());
            
            JSONArray entriesJson = new JSONArray();
            
            for (CalendarEntry entry : day.getValue()) {
                JSONObject entryJson = new JSONObject();
                
                entryJson.put("profile", entry.getProfileName());
                entryJson.put("startTime", entry.getStartTime());
                entryJson.put("endTime", entry.getEndTime());
                
                entriesJson.add(entryJson);
            }
            
            dayJson.put("entries", entriesJson);
            
            daysJson.add(dayJson);
        }
        
        daysJson.writeJSONString(resp.getWriter());
    }

    public void doPost(HttpServletRequest req) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray daysJson = (JSONArray)parser.parse(req.getReader());
    
        Map<String, CalendarEntry[]> days = new HashMap();
        
        for (Object dayObj : daysJson) {
            JSONObject dayJson = (JSONObject)dayObj;
            
            String dayName = (String)dayJson.get("dayName");
            
            JSONArray entriesJson = (JSONArray)dayJson.get("entries");
        
            List<CalendarEntry> calendarEntries = new ArrayList();
            for (Object entryObj : entriesJson) {
                CalendarEntry entry = new CalendarEntry();
                
                JSONObject entryJson = (JSONObject)entryObj;
                
                String profile = entryJson.get("profile").toString();
                int startTime = Integer.parseInt(entryJson.get("startTime").toString());
                int endTime = Integer.parseInt(entryJson.get("endTime").toString());
                
                entry.setProfileName(profile);
                entry.setStartTime(startTime);
                entry.setEndTime(endTime);
                
                calendarEntries.add(entry);
            }
            
            days.put(dayName, calendarEntries.toArray(CALENDAR_ENTRY_PARAMS));
        }
        
        calendarService.setCalendarEntries(days);
    }

}
