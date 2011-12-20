/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN;

import EnumDefinitionPackage.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KuttaA
 */
public class IntruderDetectionThread extends Thread {

    private WirelessSensorNetwork wsn;
    private SensingTypeEnum sType;
    private DistributionTypeEnum dType;
    private IntruderTypeEnum iType;
    private EnergyComputationEnum eType;
    private PrintWriter out;

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

    private void runEnergyDissipation(GlobalRuntimeVariables gsv, PrintWriter out) {
        // sRange,iteration,numAlive,numConnected,400
        for ( float sRange = 27.0f; sRange <= 77.0f; sRange += 5 ) {
            System.out.println("Current Parameters:\t" + sRange + "\tThread:\t" + this );

            DataStorageClass dsc[] = new DataStorageClass[gsv.getIterations()];
            for ( int i = 0; i < gsv.getIterations(); i++ ) {
                dsc[i] = new DataStorageClass();
            }

            for ( Integer curIteration = 1; curIteration < gsv.getMaxIterations(); curIteration++ ) {
                if ( curIteration % (gsv.getMaxIterations() / 10) == 0 ) System.out.println(100 * curIteration / gsv.getMaxIterations());
                Integer iter = 0;
                Integer count = 0;
                wsn = new WirelessSensorNetwork();
                wsn.setRunParameters(sRange,2*sRange,dType, sType, iType, eType, 400);

                do {
                    wsn.updateEnergy();
                    count++;
                    dsc[iter].setData(wsn.getNodesAlive(),400 - wsn.getUnconnectedNodeCount());
                    iter++;
                } while ( gsv.getIterations() > iter && wsn.getUnconnectedNodeCount() < 200 && wsn.getNodesAlive() > 200);
                wsn = null;
            }
            
            if ( out != null ) {
                for (int i = 0; i < gsv.getIterations(); i++ ) {
                    if ( dsc[i].getAvgNumAlive() != 0 ) {
                        out.println(sRange + "," + i +
                                "," + dsc[i].getAvgNumAlive() +
                                ",400");
                    }
                }
            } else {
                System.out.println("ERROR:\tFile not open");
                System.exit(0);
            }
            dsc = null;
        }
        out.close();
    }

    private void runVariableSensingRange(GlobalRuntimeVariables gsv, PrintWriter out) {
        if ( out != null )
            out.println("Sensing Range,Communication Range,Total Nodes,Unconnected Nodes,Intruder Distance Traveled,Detection Time,Total Detections,Transmit Distance,Transmit Hops to Base");

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
            System.out.println("Current Parameters:\t" + sRange + "\tThread:\t" + this );

            do {
                wsn = new WirelessSensorNetwork();
                wsn.setRunParameters(sRange,2*sRange,dType, sType, iType, eType, 400);

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
                //System.out.println("Average Alive:\t" + totalAliveNodes / count);
            } while ( count < gsv.getMaxIterations() );


            if ( out != null ) {
                if ( totalDetections == 0 ) {
                    out.println(sRange + "," + 2*sRange + "," + 400 + "," + totalUnconnectedNodes / count + "," +
                            distanceTraveledTotal/count + "," + totalTimeToDetection/count + "," +
                            totalDetections + "," + 0 + "," + 0 + "," + totalAliveNodes / count);
                } else {
                    out.println(sRange + "," + 2*sRange + "," + 400 + "," + totalUnconnectedNodes / count + "," +
                            distanceTraveledTotal/count + "," + totalTimeToDetection/count + "," +
                            totalDetections + "," + transmitDistanceTotal/totalDetections + "," + totalHops/totalDetections +
                            "," + totalAliveNodes / count);
                }
            }

            wsn = null;
        }

        out.close();
    }

    @Override
    public void run() {
        // Set to default if nothing has been specifically declared before
        // running this
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
        String filename = dType + "_" + sType + ".csv";

        initialize();

        if ( gsv.getRunType() == RunTypeEnum.ENERGY_DISSIPATION )
            filename = "ENERGY_DISSIPATION_" + dType + ".csv";

        try {
            out = new PrintWriter(new FileWriter(filename));
        } catch (IOException ex) {
            Logger.getLogger(IntruderDetectionDriver.class.getName()).log(Level.SEVERE, null, ex);
        }

        if ( gsv.getRunType() == RunTypeEnum.ENERGY_DISSIPATION )
            runEnergyDissipation(gsv,out);
        else
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
