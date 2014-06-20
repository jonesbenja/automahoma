/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.pi_hal.sensors.serial;

import com.automahoma.api.sensor.SensorReadException;
import java.io.IOException;

/**
 *
 * @author SESA244648
 */
public class SerialSensorManager {
    private static final int SAMPLE_PERIOD = 1000;
    
    private final PipeMaster pipeMaster;
    private final byte[] cmd; 
    private long lastRead;
    
    private final float[] sensors = new float[2];
    
    public SerialSensorManager(PipeMaster pipeMaster) {
        this.pipeMaster = pipeMaster;
        cmd = new byte[1];
        cmd[0] = 'm';
        
        lastRead = 0;
    }
    
    public float[] readSensors() throws SensorReadException {
        long now = System.currentTimeMillis();
        
        long delta = now - lastRead;
                
        if (delta > SAMPLE_PERIOD) {
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

            sensors[0] = val1;
            sensors[1] = val2;

            lastRead = now;
        }

        return sensors;
    }
}
