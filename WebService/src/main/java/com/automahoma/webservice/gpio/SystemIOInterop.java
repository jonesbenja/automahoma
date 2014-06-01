/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.gpio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author SESA244648
 */
public class SystemIOInterop implements IOInterop {
    private static final String GPIO_BASE = "/sys/class/gpio";
    
    private static final String GPIO_EXPORT = GPIO_BASE + "/export";
    private static final String GPIO_UNEXPORT = GPIO_BASE + "/unexport";
    
    private static final String GPIO_NODES = GPIO_BASE + "/gpio";
    
    private static final String DIRECTION_FILE = "/direction";
    private static final String VALUE_FILE = "/value";
    
    @Override
    public synchronized void export(int ioNumber) throws IOException {
        FileWriter writer = new FileWriter(GPIO_EXPORT);
        writer.write(Integer.toString(ioNumber));
        writer.close();
    }
    
    @Override
    public synchronized void unexport(int ioNumber) throws IOException {
        FileWriter writer = new FileWriter(GPIO_UNEXPORT);
        writer.write(Integer.toString(ioNumber));
        writer.close();
    }
    
    @Override
    public boolean isExported(int ioNumber) {
        String fileName = GPIO_NODES + ioNumber;
        File file = new File(fileName);
        return file.exists();
    }
    
    @Override
    public synchronized void setIOMode(int ioNumber, IOMode ioMode)
            throws IOException {
        String fileName = GPIO_NODES + ioNumber + DIRECTION_FILE;
        FileWriter writer = new FileWriter(fileName);
        writer.write(ioMode == IOMode.Input ? "in" : "out");
        writer.close();
    }
    
    @Override
    public synchronized IOMode getIOMode(int ioNumber) 
            throws FileNotFoundException, IOException {
        String fileName = GPIO_NODES + ioNumber + DIRECTION_FILE;
        FileReader reader = new FileReader(fileName);
        int value = reader.read();
        return (value == 'i') ? IOMode.Input : IOMode.Output;
    }
    
    @Override
    public synchronized void setValue(int ioNumber, IOValue ioValue)
            throws IOException {
        String fileName = GPIO_NODES + ioNumber + VALUE_FILE;
        FileWriter writer = new FileWriter(fileName);
        writer.write(ioValue == IOValue.On ? "1" : "0");
        writer.close();
    }
    
    @Override
    public synchronized IOValue getValue(int ioNumber) 
            throws FileNotFoundException, IOException {
        String fileName = GPIO_NODES + ioNumber + VALUE_FILE;
        FileReader reader = new FileReader(fileName);
        int value = reader.read();
        return (value == 0) ? IOValue.Off : IOValue.On;
    }
}
