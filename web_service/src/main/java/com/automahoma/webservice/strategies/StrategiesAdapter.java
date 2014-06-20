/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.webservice.strategies;

import com.automahoma.api.control.Strategy;
import java.io.IOException;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
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
public class StrategiesAdapter {
    private final StrategiesService strategiesService;
    
    public StrategiesAdapter(StrategiesService strategiesService) {
        this.strategiesService = strategiesService;
    }
    
    public void sendStrategies(HttpServletResponse resp) throws IOException {
        Strategy[] strategies = strategiesService.getStrategies();
        JSONArray strategiesJson = new JSONArray();
        
        for (Strategy strategy : strategies) {
            JSONObject strategyJson = new JSONObject();
            
            strategyJson.put("name", strategy.getName());
            
            Map parameters = strategy.getParameters();
            JSONArray parametersArrayJson = new JSONArray();
            for (Object entryObj : parameters.entrySet()) {
                Map.Entry entry = (Map.Entry)entryObj;
                JSONObject parameterJson = new JSONObject();
                parameterJson.put("name", entry.getKey());
                parameterJson.put("value", entry.getValue());
                parametersArrayJson.add(parameterJson);
            }
            strategyJson.put("parameters", parametersArrayJson);
            
            strategyJson.put("active", strategy.isActive());
            
            strategiesJson.add(strategyJson);
        }
        
        strategiesJson.writeJSONString(resp.getWriter());
    }

    public void doPost(HttpServletRequest req) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray strategiesJson = (JSONArray)parser.parse(req.getReader());
        
        Map<String, JSONObject> strategiesJsonMap = new HashMap();
        for (Object strategyObj : strategiesJson) {
            JSONObject strategyJson = (JSONObject)strategyObj;
            String name = (String)strategyJson.get("name");
            strategiesJsonMap.put(name, strategyJson);
        }
        
        Strategy[] strategies = strategiesService.getStrategies();
        
        for (Strategy strategy : strategies) {
            JSONObject strategyJson = strategiesJsonMap.get(strategy.getName());
            if (strategyJson != null) {
                boolean active = (Boolean)strategyJson.get("active");
                strategy.setActive(active);
                        
                JSONArray parametersJson = (JSONArray)strategyJson.get("parameters");
                
                Map parameters = new HashMap();
                
                for (Object parameterObj : parametersJson) {
                    JSONObject parameterJson = (JSONObject)parameterObj;
                    parameters.put(parameterJson.get("name"), parameterJson.get("value"));
                }
                
                strategy.update(parameters);
            }
        }
    }
    
    
}
