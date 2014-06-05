/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *
 * @author SESA244648
 */
public class WeatherAdapter {
    private static final String TIME_FORMAT_STRING = "HH:mm:ss";
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
            TIME_FORMAT_STRING, Locale.getDefault());
    
    private static final String DATE_FORMAT_STRING = "MM-DD-YYYY HH:mm:ss";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            DATE_FORMAT_STRING, Locale.getDefault());
    
    private final WeatherService weatherService;
    
    public WeatherAdapter(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    
    public void sendWeather(HttpServletResponse resp) {
        try {
            try {
                Weather weather = weatherService.getWeather();
                formatWeather(weather).writeJSONString(resp.getWriter());
            } catch (MalformedURLException ex) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (IOException ex) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (ParseException ex) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (IOException ex) {
            
        }
    }
    
    private JSONObject formatWeather(Weather weather) {
        JSONObject formatted = new JSONObject();
        
        JSONObject currentConditions = new JSONObject();
        String timeString = TIME_FORMAT.format(weather.getCurrentTime());
        currentConditions.put("sample_time", timeString);
        currentConditions.put("humidity", weather.getHumidity());
        currentConditions.put("precipitation", weather.getPrecipitation());
        currentConditions.put("temperature", weather.getTemperature());
        
        formatted.put("current_conditions", currentConditions);
        
        Condition[] future = weather.getFutureConditions();
        JSONArray futureConditions = new JSONArray();
        
        for (Condition condition : future) {
            JSONObject futureCondition = new JSONObject();
            
            String dateString = DATE_FORMAT.format(condition.getDate());
            futureCondition.put("date", dateString);
            
            futureCondition.put("max_temperature", 
                    condition.getMaxTemperature());
            
            futureCondition.put("min_temperature", 
                    condition.getMinTemperature());
            
            futureCondition.put("precipitation", 
                    condition.getPrecipitation());
            
            futureConditions.add(futureCondition);
        }
        
        formatted.put("future_conditions", futureConditions);
        
        return formatted;
    }

}
