/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.gpio;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author SESA244648
 */
public interface IOInterop {

    void export(int ioNumber) throws IOException;

    IOMode getIOMode(int ioNumber) throws FileNotFoundException, IOException;

    IOValue getValue(int ioNumber) throws FileNotFoundException, IOException;

    boolean isExported(int ioNumber);

    void setIOMode(int ioNumber, IOMode ioMode) throws IOException;

    void setValue(int ioNumber, IOValue ioValue) throws IOException;

    void unexport(int ioNumber) throws IOException;
    
}
