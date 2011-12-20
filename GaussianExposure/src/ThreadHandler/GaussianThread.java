/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ThreadHandler;

import EnumerationDefinitions.*;
import GaussianExposure.*;
import IntruderPackage.*;

/**
 *
 * @author KuttaA
 */
public class GaussianThread extends Thread {
    private Double distTraveled;
    private Double exposure;
    private boolean bInitialized = false;
    private boolean bLog;

    public GaussianThread() {
        bInitialized = false;
        wsn = new WirelessSensorNetwork();
        grv = GlobalRuntimeVariables.getInstance();
        exposure = 0.0;
        distTraveled = 0.0;
        bLog = false;
        bInitialized = true;
    }
    
    public void setLogging(boolean bLogAt) {
        bLog = bLogAt;
    }

    public void readData(Integer curNodes) {
        //System.out.println("Current Nodes:\t" + curNodes);
        if ( grv.getTypeDist() == DistributionTypeEnum.TRUNCATED_GAUSSIAN )
            wsn.readPointsFromFile("TruncatedGaussianNodes//nodes" + curNodes + ".csv");
        else if ( grv.getTypeDist() == DistributionTypeEnum.GAUSSIAN ) {
            wsn.readPointsFromFile("GaussianNodes//nodes" + curNodes + ".csv");
        } else if ( grv.getTypeDist() == DistributionTypeEnum.NORMALIZED_RANDOM ) {
            wsn.readPointsFromFile("RandomizedNodes//nodes" + curNodes + ".csv");
        }
    }

    @Override
    public void run() {
        if ( !bInitialized )
            return;
        
        intruder = new Intruder(wsn.getNodes());
        intruder.update(bLog,grv.getSigma());

        distTraveled = intruder.getDistance();
        exposure = intruder.getExposure();

        wsn.getNodes().removeAllElements();

        ThreadScheduler.getInstance().setFinished(myThreadID,exposure,distTraveled);

        intruder.cleanup();
        intruder = null;
    }

    public Double getExposure() {
        return exposure;
    }

    public Double getDistance() {
        return distTraveled;
    }
    
    public void setThreadID(Integer threadID) {
        myThreadID = threadID;
    }

    private Integer myThreadID;
    private WirelessSensorNetwork wsn;
    private GlobalRuntimeVariables grv;
    private Intruder intruder;
}
