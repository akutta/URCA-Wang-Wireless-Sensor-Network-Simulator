/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN.Threads;

import EnumDefinitionPackage.*;
import IntruderDetectionWSN.*;
import java.io.*;
import java.util.logging.*;

/**
 *
 * @author KuttaA
 */
public class VariableBaseLocationThread extends BaseThread {
    private void runVariableSensingRange(GlobalRuntimeVariables gsv, PrintWriter out) {
        if ( out != null )
            out.println("Sensing Range,Total Nodes,Unconnected Nodes,Intruder Distance Traveled,Total Detections,BaseDistance From Center");
            //out.println("Sensing Range,Communication Range,Total Nodes,Unconnected Nodes,Intruder Distance Traveled,Detection Time,Total Detections,Transmit Distance,Transmit Hops to Base,BaseDistance From Center");

        float cRatio = 2.0f;
        Integer baseDistanceFromCenter = 0;

        while ( baseDistanceFromCenter <= 500 ) {
            for ( float sRange = 15.0f; sRange <= 75.0f; sRange += 2 ) {
                Integer count = 0;
                Integer totalDetections = 0;
                Double totalUnconnectedNodes = 0.0;
                Double  distanceTraveledTotal = 0.0;

                //Thread wsnThread = null;
                System.out.println("Current Parameters:\t" + sRange + "\baseDistanceFromCenter:\t" + baseDistanceFromCenter + "Thread:\t" + this );

                do {
                    wsn = new WirelessSensorNetwork();
                    wsn.setRunParameters(sRange,cRatio*sRange,dType, sType, iType, eType, 400,baseDistanceFromCenter,-1,-1,-1,-1);

                    while ( !wsn.finishedRun() )
                    {
                        // calls intruder update internally
                        // this allows to continue threaded operation
                        if ( !wsn.isUpdating() )    // Basic Mutex
                            wsn.updateDetection();
                    }
                    totalDetections += wsn.wasDetection();
                    distanceTraveledTotal += wsn.getDistanceTraveledInField();
                    totalUnconnectedNodes += wsn.getUnconnectedNodeCount();
                    wsn.reset();
                    count++;
                } while ( count < gsv.getMaxIterations() );


                // NOTE:  This 400 is only correct if running a randomly distributed network
                if ( out != null ) {
                    out.println(sRange + "," + 400 + "," + totalUnconnectedNodes / count + "," +
                            distanceTraveledTotal/count + "," + totalDetections + "," + baseDistanceFromCenter);
                }
                wsn = null;
            }
            baseDistanceFromCenter += 50;
        }
        out.close();
    }

    @Override
    public void beginThread() {
        // Set to default if nothing has been specifically declared before
        // running this
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
        String filename = dType + "_" + sType + "BASELOCATION" + ".csv";

        initialize();

        try {
            out = new PrintWriter(new FileWriter(filename));
        } catch (IOException ex) {
            Logger.getLogger(VariableBaseLocationThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        runVariableSensingRange(gsv,out);

        out.close();

    }

    void initialize() {
        // CHANGE PARAMETERS HERE!!!
        //SensingTypeEnum sType = SensingTypeEnum.THREE_SIMULTANEOUS;
        //SensingTypeEnum sType = SensingTypeEnum.THREE_INDIVIDUAL;
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
