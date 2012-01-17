/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ThreadHandler;

import EnumerationDefinitions.*;
import GaussianExposure.*;
import IntruderPackage.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KuttaA
 */
public class GaussianThread extends Thread {
    private Double distTraveled;
    private Double exposure;
    private boolean bInitialized = false;
    private boolean bLog;
    private boolean bKeepThreadAlive;
    private boolean bRunTask;

    public GaussianThread() {
        bInitialized = false;
        wsn = new WirelessSensorNetwork();
        grv = GlobalRuntimeVariables.getInstance();
        exposure = 0.0;
        distTraveled = 0.0;
        bLog = false;
        bKeepThreadAlive = true;
        bInitialized = true;
    }
    
    public void killThread() {
        bKeepThreadAlive = false;
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

        while ( bKeepThreadAlive ) {
            if ( bRunTask ) {
                runTask();
                pause();
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GaussianThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Controllers to actually run the "Task"
    public void begin() {
        bRunTask = true;
    }

    // Controller to pause the current thread but not actually finish
    private void pause() {
        bRunTask = false;
    }

    private void runTask() {
        intruder = new Intruder(wsn.getNodes());
        intruder.update(bLog,grv.getSigma());

        distTraveled = intruder.getDistance();
        exposure = intruder.getExposure();

        wsn.getNodes().removeAllElements();

        ThreadScheduler.getInstance().setFinished(myThreadID,exposure,distTraveled);

        // Reset all variables here
        // so next run doesn't have previous data stored
        exposure = 0.0;
        distTraveled = 0.0;
        bLog = false;

        intruder.cleanup();
        intruder = null;
        pause();
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
