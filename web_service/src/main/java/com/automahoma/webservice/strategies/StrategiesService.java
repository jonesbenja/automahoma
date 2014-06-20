/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.automahoma.webservice.strategies;

import com.automahoma.api.control.Strategy;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tiffany
 */
public class StrategiesService {
    
    private final static Strategy[] STRATEGY_PARAMS = new Strategy[0];
    private final Map<String, Strategy> strategies;
    
    public StrategiesService() {
        strategies = new HashMap();
    }
    
    public void addStrategy(Strategy strategy) {
        strategies.put(strategy.getName(), strategy);
    }
    
    public void removeStrategy(Strategy strategy) {
        strategies.remove(strategy.getName());
    }
    
    public Strategy[] getStrategies() {
        return strategies.values().toArray(STRATEGY_PARAMS);
    }
    
    public void pollActiveStrategies() {
        for (Strategy strategy : strategies.values()) {
            if (strategy.isActive()) {
                strategy.poll();
            }
        }
    }
}