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

    private Integer numNodes;
    private Integer initPosX, finalPosX;
    private Integer initPosY, finalPosY;

    @Override
    protected void runTest(Float sRange, Float cRange) {
        
            Integer count = 0;
            Integer detections = 0;
            Double  distanceTraveledTotal = 0.0;
            Integer unconnectedNodes = 0;
            Integer totalNodes = 0;
            do {
                wsn = new WirelessSensorNetwork();
                wsn.setRunParameters(sRange,cRange,dType, sType, iType, eType, numNodes, -1,initPosX,initPosY,finalPosX,finalPosY);
                while ( !wsn.finishedRun() )
                {
                    // calls intruder update internally
                    // this allows to continue threaded operation
                    if ( !wsn.isUpdating() )    // Basic Mutex
                        wsn.updateDetection();
                }

                unconnectedNodes = wsn.getUnconnectedNodeCount();
                totalNodes = wsn.getNumNodes();
                distanceTraveledTotal += wsn.getDistanceTraveledInField();
                detections += wsn.wasDetection();
                wsn.reset();
                count++;
                progress.updateProgress(count);
            } while ( count < iterations );

            avgDistance = distanceTraveledTotal / count;
            progress.setFinalDistanceAndProbability(
                    avgDistance,
                    1.0 - (unconnectedNodes.doubleValue() / totalNodes.doubleValue()),
                    (detections.doubleValue()/count.doubleValue())
                    );
            wsn = null;
            parent.threadFinished();
    }

    public void initialize(SensingTypeEnum sType, DistributionTypeEnum dType,
            IntruderTypeEnum iType, Float sRange, Float cRange, 
            Integer iterations, Integer K, Integer boardWidth, Integer boardHeight, Integer numNodes,
            Integer initPosX, Integer initPosY,
            Integer finalPosX, Integer finalPosY,
            WSNFrame parent, ProgressAndResults progress)
    {
        // Set to default if nothing has been specifically declared before
        // running this
        this.sType = sType;
        this.dType = dType;
        this.iType = iType;
        this.sRange = sRange;
        this.cRange = cRange;
        this.initPosX = initPosX;
        this.initPosY = initPosY;
        this.finalPosX = finalPosX;
        this.finalPosY = finalPosY;
        this.parent = parent;
        this.progress = progress;
        this.iterations = iterations;
        this.numNodes = numNodes;

        GlobalRuntimeVariables.getInstance().setBoardSize(boardWidth, boardHeight);
        GlobalRuntimeVariables.getInstance().setK(K);
    }

    @Override
    public void beginThread() {
        if ( this.numNodes > 0 )
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
