/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GaussianExposure;

import IntruderPackage.*;
import ThreadHandler.ThreadScheduler;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akutta
 */
public class GaussianExposureDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GaussianExposureDriver gaussianExposureDriver = new GaussianExposureDriver();
    }

    GaussianExposureDriver() {
        grv = GlobalRuntimeVariables.getInstance();
        ts = ThreadScheduler.getInstance();

        PrintWriter Results = null;

        try {
            Results = new PrintWriter(new FileWriter("Results_" + grv.getTypeDist() + ".csv"),false);
        } catch (IOException ex) {
            Logger.getLogger(GaussianExposureDriver.class.getName()).log(Level.SEVERE, null, ex);
        }

        Integer LogAt = grv.getNextInteger(grv.subCount);
        System.out.println("Logging @ " + LogAt);
        Integer subCount = 0;
        Integer curNodes = 0*grv.subCount;   // change to current node to start at
        Double totDistTraveled = 0.0;
        Double totExposure = 0.0;
        while ( curNodes < 57*grv.subCount ) {
            if ( ts.canStartNewThread() && subCount < grv.subCount) {
                //System.out.println("Starting Thread for Node:\t" + curNodes);
                if ( subCount == LogAt ) {
                    //System.out.println("Logging for:\t" + curNodes);
                    ts.startThread(true, curNodes);
                } else {
                    ts.startThread(false, curNodes);
                }
                curNodes++;
                subCount++;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GaussianExposureDriver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if ( subCount == grv.subCount ) {
                do {
                    totDistTraveled = ts.getDistance();
                    totExposure = ts.getExposure();
                    try {
                        Thread.sleep(100);  // Must wait for all threads to finish so let scheduler have access to the cpu
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GaussianExposureDriver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } while ( totExposure == -1.0 || totDistTraveled == -1.0);
                totDistTraveled /= subCount;
                totExposure /= subCount;
                subCount = 0;
                System.out.println(grv.getSigma() + ":\tE = " + totExposure + "\tD = " + totDistTraveled);
                Results.write(grv.getSigma() + "," + totExposure + "," + totDistTraveled + "\n");

                ts.clearData();
                totDistTraveled = 0.0;
                totExposure = 0.0;
            }
        }
        Results.close();
        ts.killAllThreads();    // cleanup more...though java should do this just fine on it's own
    }
    
    private ThreadScheduler ts;
    private GlobalRuntimeVariables grv;
}
