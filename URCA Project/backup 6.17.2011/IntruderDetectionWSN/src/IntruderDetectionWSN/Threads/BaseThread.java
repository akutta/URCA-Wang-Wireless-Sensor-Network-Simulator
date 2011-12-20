/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN.Threads;

import EnumDefinitionPackage.*;
import IntruderDetectionWSN.WirelessSensorNetwork;
import java.io.PrintWriter;

/**
 *
 * @author KuttaA
 */
public class BaseThread extends Thread {

    protected WirelessSensorNetwork wsn;
    protected SensingTypeEnum sType;
    protected DistributionTypeEnum dType;
    protected IntruderTypeEnum iType;
    protected EnergyComputationEnum eType;
    protected PrintWriter out;

    @Override
    public void run() {
        beginThread();
    }

    protected void beginThread() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public DistributionTypeEnum getDistributionType() {
        return dType;
    }

    public void setDistributionType(DistributionTypeEnum dType) {
        this.dType = dType;
    }

    public void setSensingType(SensingTypeEnum newSType) {
        System.out.println("Sensing Type Set:\t" + newSType);
        sType = newSType;
    }

    public void setIntruderType(IntruderTypeEnum newIType) {
        System.out.println("Intruder Type Set:\t" + newIType);
        iType = newIType;
    }

    protected void runTest(Float sRange, Float cRange) {
    }
}
