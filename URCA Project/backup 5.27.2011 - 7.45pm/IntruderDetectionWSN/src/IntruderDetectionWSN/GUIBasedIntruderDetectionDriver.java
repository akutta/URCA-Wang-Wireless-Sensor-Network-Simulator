/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN;

import EnumDefinitionPackage.*;
import GUI.ProgressAndResults;
import GUI.WSNFrame;

/**
 *
 * @author KuttaA
 */
public class GUIBasedIntruderDetectionDriver extends Thread {
    public DistributionTypeEnum getDistributionType() {
        return dType;
    }

    public void setDistributionType(DistributionTypeEnum dType) {
        this.dType = dType;
    }

    public void setSensingType(SensingTypeEnum newSType) {
        sType = newSType;
    }

    public void setIntruderType(IntruderTypeEnum newIType) {
        iType = newIType;
    }

    private void runVariableSensingRange(Float sRange, Float cRange) {
        
            Integer count = 0;
            Integer totalHops = 0;
            Integer totalDetections = 0;
            Integer totalAliveNodes = 0;
            Double totalUnconnectedNodes = 0.0;
            Double  distanceTraveledTotal = 0.0;
            Double  transmitDistanceTotal = 0.0;
            long    totalTimeToDetection = 0;

            do {
                wsn = new WirelessSensorNetwork();
                wsn.setRunParameters(sRange,cRange,dType, sType, iType, eType, 400);
                while ( !wsn.finishedRun() )
                {
                    // calls intruder update internally
                    // this allows to continue threaded operation
                    if ( !wsn.isUpdating() )    // Basic Mutex
                        wsn.updateDetection();
                }
                totalDetections += wsn.wasDetection();
                distanceTraveledTotal += wsn.getDistanceTraveled();
                transmitDistanceTotal += wsn.getTotalTransmitDistance();
                totalTimeToDetection += wsn.getTimeToDetection();
                totalHops += wsn.getTotalHopsToBase();
                totalUnconnectedNodes += wsn.getUnconnectedNodeCount();
                totalAliveNodes += wsn.getNodesAlive();
                wsn.reset();
                count++;
                //System.out.println("count:\t" + count);
                progress.updateProgress(count);
            } while ( count < iterations );

            avgDistance = distanceTraveledTotal / count;
            System.out.println("Average Distance Traveled:\t" + distanceTraveledTotal / count);

            wsn = null;
            progress.setFinalDistance(avgDistance);
            parent.threadFinished();
    }

    public void initialize(SensingTypeEnum sType, DistributionTypeEnum dType,
            IntruderTypeEnum iType, Float sRange, Float cRange, 
            Integer iterations, Integer K, WSNFrame parent, ProgressAndResults progress)
    {
        // Set to default if nothing has been specifically declared before
        // running this
        this.sType = sType;
        this.dType = dType;
        this.iType = iType;
        this.sRange = sRange;
        this.cRange = cRange;
        this.parent = parent;
        this.progress = progress;
        this.iterations = iterations;
       //GlobalRuntimeVariables.getInstance().setMaxIterations(iterations);
        GlobalRuntimeVariables.getInstance().setK(K);
    }

    @Override
    public void run() {
        runVariableSensingRange(sRange,cRange);
    }

    private Double avgDistance;

    private WSNFrame parent;
    private Float sRange;
    private Float cRange;
    private Integer iterations;

    private ProgressAndResults progress;
    private WirelessSensorNetwork wsn;
    private SensingTypeEnum sType;
    private DistributionTypeEnum dType;
    private IntruderTypeEnum iType;
    private EnergyComputationEnum eType;

    public Double getAverageDistance() {
        return avgDistance;
    }

}
