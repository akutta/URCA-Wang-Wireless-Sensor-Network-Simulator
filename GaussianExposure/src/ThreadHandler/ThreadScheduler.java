/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ThreadHandler;

import GaussianExposure.GlobalRuntimeVariables;

/**
 *
 * @author KuttaA
 */
public class ThreadScheduler {
    private GlobalRuntimeVariables grv;
    private static ThreadScheduler instance = null;
    private ThreadScheduler() {
        grv = GlobalRuntimeVariables.getInstance();
        maxThreads = grv.numThreads;
        runningThreads = 0;

        threads = new GaussianThread[grv.numThreads];
        inUse = new boolean[grv.numThreads];
        for ( int i = 0; i < grv.numThreads; i++ ) {
            inUse[i] = false;
        }
        firstAvailable = 0;
        totDistance = 0.0;
        totExposure = 0.0;
    }

    public static ThreadScheduler getInstance() {
        if ( instance == null ) {
            instance = new ThreadScheduler();
        }
        return instance;
    }


    public boolean canStartNewThread() {
        for ( int i = 0; i < maxThreads; i++ ) {
            if ( inUse[i] == false ) {
                firstAvailable = i;
                return true;
            }
        }
        firstAvailable = -1;
        return false;
    }

    public void startThread(boolean bLog, Integer curNode) {
        if ( !canStartNewThread() ) {
            return;
        }

        threads[firstAvailable] = new GaussianThread();
        threads[firstAvailable].setThreadID(firstAvailable);
        threads[firstAvailable].readData(curNode);
        threads[firstAvailable].setLogging(bLog);
        threads[firstAvailable].start();
        runningThreads++;
        inUse[firstAvailable] = true;
        firstAvailable = -1;
    }
   
    void setFinished(Integer myThreadID, Double exposure, Double distTraveled) {
        runningThreads--;
        inUse[myThreadID] = false;
        threads[myThreadID] = null;
        System.out.println("Exposure:\t" + exposure + "\tDistance:\t" + distTraveled);
        totExposure += exposure;
        totDistance += distTraveled;
    }

    public Double getDistance() {
        if ( runningThreads > 0  )
            return -1.0;
        
        return totDistance;
    }

    public Double getExposure() {
        if ( runningThreads > 0  )
            return -1.0;
        
        return totExposure;
    }

    public void clearData() {
        totDistance = 0.0;
        totExposure = 0.0;
    }

    private Double totDistance;
    private Double totExposure;
    private Integer runningThreads;
    private Integer maxThreads;
    private Integer firstAvailable;
    private boolean[] inUse;
    private GaussianThread[] threads;

}
