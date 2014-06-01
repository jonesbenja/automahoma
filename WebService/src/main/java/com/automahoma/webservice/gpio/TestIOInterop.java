/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.gpio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SESA244648
 */
public class TestIOInterop implements IOInterop {

    private final Map<Integer, IOMode> ioModesByNumber;
    private final Map<Integer, IOValue> ioValuesByNumber;
    
    public TestIOInterop() {
        ioModesByNumber = new HashMap();
        ioValuesByNumber = new HashMap();
    }

    public void export(int ioNumber) throws IOException {
        ioModesByNumber.put(ioNumber, IOMode.Input);
    }

    public IOMode getIOMode(int ioNumber) 
            throws FileNotFoundException, IOException {
        
        IOMode mode = ioModesByNumber.get(ioNumber);

        if (mode == null) {
            throw new FileNotFoundException();
        }
        
        return mode;
    }

    public IOValue getValue(int ioNumber) 
            throws FileNotFoundException, IOException {
        
        IOValue value = ioValuesByNumber.get(ioNumber);
        
        if (value == null) {
            throw new FileNotFoundException();
        }
        
        return value;
    }

    public boolean isExported(int ioNumber) {
        IOMode mode = ioModesByNumber.get(ioNumber);
        return mode != null;
    }

    public void setIOMode(int ioNumber, IOMode ioMode) throws IOException {
        ioModesByNumber.put(ioNumber, ioMode);
    }

    public void setValue(int ioNumber, IOValue ioValue) throws IOException {
        ioValuesByNumber.put(ioNumber, ioValue);
    }

    public void unexport(int ioNumber) throws IOException {
        ioModesByNumber.remove(ioNumber);
        ioValuesByNumber.remove(ioNumber);
    }
}
