/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.sensors.serial;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author SESA244648
 */
public class PipeMaster {
     
    private static final String RSP_PIPE_NAME = "/home/pi/sensor-interface/rsp";
    private static final String CMD_PIPE_NAME = "/home/pi/sensor-interface/cmd";
    
    public static final int MSG_CMD_FIELD = 0;
    public static final int MSG_SIZE_FIELD = 1;
    public static final int MSG_PAYLOAD_FIELD = 2;
    
    private InputStream rspPipe;
    private OutputStream cmdPipe;
    
    private final int BUFFER_SIZE = 256;
    private final byte[] buffer;
    
    public PipeMaster() {
        buffer = new byte[BUFFER_SIZE];
    }
    
    public synchronized void sendCmd(byte[] cmd) throws IOException {
        getCmd().write(cmd);
    }
    
    public synchronized byte[] readRsp() throws IOException {
        int count = 0;
        do {
            count += getRsp().read(buffer, count, BUFFER_SIZE);
        
        } while (isAssemblingMessage(count, buffer));
        
        byte[] msg = new byte[buffer[MSG_SIZE_FIELD]];
        System.arraycopy(buffer, 0, msg, 0, buffer[MSG_SIZE_FIELD]);
        
        return msg;
    }
    
    private InputStream getRsp() throws FileNotFoundException {
        if (rspPipe == null) {
            rspPipe = new FileInputStream(RSP_PIPE_NAME);
        }
        
        return rspPipe;
    }
    
    private OutputStream getCmd() throws FileNotFoundException {
        if (cmdPipe == null) {
            cmdPipe = new FileOutputStream(CMD_PIPE_NAME);
        }
        
        return cmdPipe;
    }
    
    private boolean isAssemblingMessage(int count, byte[] buffer) {
        boolean assembling = true;
        
        if (count > MSG_SIZE_FIELD) {
            int length = buffer[MSG_SIZE_FIELD];
            
            if (length >= count) {
                assembling = false;
            }
        }
        
        return assembling;
    }
}
