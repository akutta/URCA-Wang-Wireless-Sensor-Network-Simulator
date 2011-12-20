/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN.Threads;

import EnumDefinitionPackage.*;
import GUI.ProgressAndResults;
import GUI.WSNFrame;
import IntruderDetectionWSN.GlobalRuntimeVariables;
import IntruderDetectionWSN.WirelessSensorNetwork;

/**
 *
 * @author KuttaA
 */
public class GUIBasedIntruderDetectionThread extends BaseThread {

    @Override
    protected void runTest(Float sRange, Float cRange) {
        
            Integer count = 0;
            Double  distanceTraveledTotal = 0.0;

            do {
                wsn = new WirelessSensorNetwork();
                wsn.setRunParameters(sRange,cRange,dType, sType, iType, eType, 400,-1);
                while ( !wsn.finishedRun() )
                {
                    // calls intruder update internally
                    // this allows to continue threaded operation
                    if ( !wsn.isUpdating() )    // Basic Mutex
                        wsn.updateDetection();
                }
                distanceTraveledTotal += wsn.getDistanceTraveled();
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
        
        GlobalRuntimeVariables.getInstance().setK(K);
    }

    @Override
    public void beginThread() {
        runTest(sRange,cRange);
    }

    //
    // Variables Specific to GUI Base Thread
    //

    private WSNFrame parent;
    private Double avgDistance;
    private Float sRange;
    private Float cRange;
    private Integer iterations;

    private ProgressAndResults progress;

    public Double getAverageDistance() {
        return avgDistance;
    }

}
