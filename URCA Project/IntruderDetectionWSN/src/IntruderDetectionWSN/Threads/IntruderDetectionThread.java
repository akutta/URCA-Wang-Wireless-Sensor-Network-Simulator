/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN.Threads;

import EnumDefinitionPackage.*;
import IntruderDetectionWSN.*;
import IntruderDetectionWSN.Drivers.VariableSensingCommRatioDriver;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KuttaA
 */
public class IntruderDetectionThread extends BaseThread {

    private void runTest(GlobalRuntimeVariables gsv, PrintWriter out) {
        if ( out != null )
            out.println("Sensing Range,Communication Range,Total Nodes,Unconnected Nodes,Intruder Distance Traveled,Detection Time,Total Detections,Transmit Distance,Transmit Hops to Base");

        //float cRatio = 0.1f;
        //do {

        float cRatio = 2.0f;
        for ( cRatio = 0.1f; cRatio <= 10.0; cRatio += .1f ) {
            cRatio = ((Long)(Math.round(cRatio * 10.0))).floatValue() / 10.0f;
            for ( float sRange = 15.0f; sRange <= 75.0f; sRange += 2 ) {
                Integer count = 0;
                Integer totalHops = 0;
                Integer totalDetections = 0;
                Integer totalAliveNodes = 0;
                Double totalUnconnectedNodes = 0.0;
                Double  distanceTraveledTotal = 0.0;
                Double  transmitDistanceTotal = 0.0;
                long    totalTimeToDetection = 0;

                //Thread wsnThread = null;
                System.out.println("Current Parameters:\t" + sRange + "\tcRatio:\t" + cRatio + "Thread:\t" + this );

                do {
                    wsn = new WirelessSensorNetwork();
                    wsn.setRunParameters(sRange,cRatio*sRange,dType, sType, iType, eType, 400,-1,-1,-1,-1,-1);

                    while ( !wsn.finishedRun() )
                    {
                        // calls intruder update internally
                        // this allows to continue threaded operation
                        if ( !wsn.isUpdating() )    // Basic Mutex
                            wsn.updateDetection();
                    }
                    totalDetections += wsn.wasDetection();
                    distanceTraveledTotal += wsn.getDistanceTraveledInField();
                    transmitDistanceTotal += wsn.getTotalTransmitDistance();
                    totalTimeToDetection += wsn.getTimeToDetection();
                    totalHops += wsn.getTotalHopsToBase();
                    totalUnconnectedNodes += wsn.getUnconnectedNodeCount();
                    totalAliveNodes += wsn.getNodesAlive();
                    wsn.reset();
                    count++;
                    //System.out.println("Average Alive:\t" + totalAliveNodes / count);
                } while ( count < gsv.getMaxIterations() );


                if ( out != null ) {
                    if ( totalDetections == 0 ) {
                        out.println(sRange + "," + cRatio*sRange + "," + 400 + "," + totalUnconnectedNodes / count + "," +
                                distanceTraveledTotal/count + "," + totalTimeToDetection/count + "," +
                                totalDetections + "," + 0 + "," + 0 + "," + totalAliveNodes / count);
                    } else {
                        out.println(sRange + "," + cRatio*sRange + "," + 400 + "," + totalUnconnectedNodes / count + "," +
                                distanceTraveledTotal/count + "," + totalTimeToDetection/count + "," +
                                totalDetections + "," + transmitDistanceTotal/totalDetections + "," + totalHops/totalDetections +
                                "," + totalAliveNodes / count);
                    }
                }

                wsn = null;
            }
         //   if ( cRatio < 2 ) {
         //       cRatio += .1;
        //    } else
        //        cRatio += .5;
        }
        //} while (cRatio <= 10);

        out.close();
    }

    @Override
    public void beginThread() {
        // Set to default if nothing has been specifically declared before
        // running this
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
        String filename = dType + "_" + sType + ".csv";

        initialize();

        try {
            out = new PrintWriter(new FileWriter(filename));
        } catch (IOException ex) {
            Logger.getLogger(VariableSensingCommRatioDriver.class.getName()).log(Level.SEVERE, null, ex);
        }

        runTest(gsv,out);

        out.close();

    }

    void initialize() {
        if ( sType == null )
            sType = SensingTypeEnum.SINGLE_DETECTION;

        // Will allow this to be set later
        if ( dType == null )
            dType = DistributionTypeEnum.RANDOM;

        if ( iType == null )
            iType = IntruderTypeEnum.STRAIGHT_LINE;

        if ( eType == null )
            eType = EnergyComputationEnum.MTE;
    }
}
