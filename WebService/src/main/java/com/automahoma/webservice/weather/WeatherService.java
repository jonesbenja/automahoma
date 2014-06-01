/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.weather;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author SESA244648
 */
public class WeatherService {
    private static final String WEATHER_URL 
            = "http://api.worldweatheronline.com/free/v1/weather.ashx?q=37167"
            + "&format=json&num_of_days=5&fx=yes&key=utgqgqe4jw928gce55ueuwtr";
    
    private static final String TIME_FORMAT_STRING = "HH:mm a";
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
            TIME_FORMAT_STRING, Locale.getDefault());
    
    private static final String DATE_FORMAT_STRING = "YYYY-MM-DD";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            DATE_FORMAT_STRING, Locale.getDefault());
    
    private static final long SAMPLE_INTERVAL = 1000 * 60 * 10;
    
    private long lastSampleTime = 0;
    
    private Weather weather;
    
    public Weather getWeather() throws MalformedURLException, IOException, 
            ParseException, ParseException {
        
        URL weatherUrl = new URL(WEATHER_URL);
        
        HttpURLConnection weatherConnection = (HttpURLConnection)weatherUrl
                .openConnection();
        
        weatherConnection.setDoInput (true);
        weatherConnection.connect();
        
        InputStream inputStream = weatherConnection.getInputStream();
       
        JSONParser parser = new JSONParser();
        
        Reader reader = new InputStreamReader(inputStream);
        
        JSONObject parsedContent = (JSONObject)parser.parse(reader);
        
        
        long now = System.currentTimeMillis();
        
        long delta = now - lastSampleTime;
        
        if (delta > SAMPLE_INTERVAL) {
            weather = buildWeatherFromContent(parsedContent);
        }
        
        return weather;
    }
    
    private Weather buildWeatherFromContent(JSONObject parsedContent) 
            throws ParseException {
        
        Weather update = new Weather();
        
        JSONObject data = (JSONObject)parsedContent.get("data");
        
        JSONArray currentConditions = (JSONArray)data.get("current_condition");
        
        JSONObject currentCondition = (JSONObject)currentConditions.get(0);
        
        String currentTimeStr = (String)currentCondition.get(
                "observation_time");
        try {
            Date currentTime = TIME_FORMAT.parse(currentTimeStr);
            
            update.setCurrentTime(currentTime);
        } catch (java.text.ParseException ex) {
            throw new ParseException(
                    ParseException.ERROR_UNEXPECTED_EXCEPTION, ex);
        }
        
        String temperatureStr = (String)currentCondition.get("temp_F");
        float temperature = Float.parseFloat(temperatureStr);
        update.setTemperature(temperature);
        
        String precipitationStr = (String)currentCondition.get("precipMM");
        float precipitation = Float.parseFloat(precipitationStr);
        update.setPrecipitation(precipitation);
        
        String humidityStr = (String)currentCondition.get("humidity");
        float humidity = Float.parseFloat(humidityStr);
        update.setHumidity(humidity);
        
        JSONArray conditionsArray = (JSONArray)data.get("weather");
        int conditionsSize = conditionsArray.size();
        Condition[] conditions = new Condition[conditionsSize];
        for (int i = 0; i < conditionsSize; i++) {
            JSONObject conditionsObj = (JSONObject)conditionsArray.get(i);
            conditions[i] = new Condition();
            
            String tempMinStr = (String)conditionsObj.get("tempMinF");
            float tempMin = Float.parseFloat(tempMinStr);
            conditions[i].setMinTemperature(tempMin);
            
            String tempMaxStr = (String)conditionsObj.get("tempMaxF");
            float tempMax = Float.parseFloat(tempMaxStr);
            conditions[i].setMaxTemperature(tempMax);
            
            precipitationStr = (String)conditionsObj.get("precipMM");
            precipitation = Float.parseFloat(precipitationStr);
            conditions[i].setPrecipitation(precipitation);
            
            String dateStr = (String)conditionsObj.get("date");
            try {
                Date date = DATE_FORMAT.parse(dateStr);
                conditions[i].setDate(date);
            } catch (java.text.ParseException ex) {
                throw new ParseException(
                        ParseException.ERROR_UNEXPECTED_EXCEPTION);
            }
        }
        
        update.setFutureConditions(conditions);
        
        return update;
    }
}
