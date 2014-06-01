/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice.serial_sensors;

import java.io.IOException;

/**
 *
 * @author SESA244648
 */
public class SerialSensorManager {
    private final PipeMaster pipeMaster;
    private final byte[] cmd; 
    public SerialSensorManager(PipeMaster pipeMaster) {
        this.pipeMaster = pipeMaster;
        cmd = new byte[1];
        cmd[0] = 'm';
    }
    
    public float[] readSensors() throws SensorReadException {
        byte[] rsp;
        try {
            pipeMaster.sendCmd(cmd);
            rsp = pipeMaster.readRsp();
        } catch (IOException ex) {
            throw new SensorReadException(ex);
        }
        
        if (rsp[PipeMaster.MSG_CMD_FIELD] != 'm') {
            throw new SensorReadException("Remote exception: " 
                    + rsp[PipeMaster.MSG_PAYLOAD_FIELD]);
        }
        
        int val1 = rsp[PipeMaster.MSG_PAYLOAD_FIELD] 
                | (rsp[PipeMaster.MSG_PAYLOAD_FIELD + 1] << 8);
        
        int val2 = rsp[PipeMaster.MSG_PAYLOAD_FIELD + 2] 
                | (rsp[PipeMaster.MSG_PAYLOAD_FIELD + 3] << 8);
        
        float[] sensors = new float[2];
        
        sensors[0] = val1;
        sensors[1] = val2;
        
        return sensors;
    }
}
