/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JointWSNProject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KuttaA
 */
class WSNRuntimeThread extends Thread {

    private double distance;
    private double exposure;
    private DistributionTypeEnum dType;
    private PrintWriter out;
    private GlobalRuntimeVariables gsv;
    private WirelessSensorNetwork wsn;


    @Override
    public void run() {
        exposure = 0.0;
        distance = 0.0;
        // Set to default if nothing has been specifically declared before
        // running this
        gsv = GlobalRuntimeVariables.getInstance();

        //
        // There should be more setup for random since we have to run it multiple times
        // May only mean doing a loop of iterations to get an average
        //
      //  if ( dType == DistributionTypeEnum.RANDOM || dType == DistributionTypeEnum.SQUARE ) {
            // I Think we need to run this 50 times
        int runs = 0;
        for ( int i = 0; i < 50; i++ ) {
            // Sometimes it returns Infinity depending on the distribution of nodes
            if ( i == 10 ) {
                if ( !runDistributionType(true,i))
                    i--;
                else
                    runs++;
            }
            if ( !runDistributionType(false,i) )
                i--;
            else
                runs++;
        }
        System.out.println("Average Exposure:\t" + exposure / runs);
        System.out.println("Average Distance:\t" + distance / runs);
        System.out.println("Runs:\t"+ runs);
    }

    private boolean runDistributionType(boolean bLog, int curRun) {
        wsn = new WirelessSensorNetwork();
        wsn.initialize(bLog);

        while ( !wsn.finishedRun() )
        {
            // calls intruder update internally
            // this allows to continue threaded operation
            if ( !wsn.isUpdating() )    // Basic Mutex
                wsn.update(bLog);
        }



        // Add in getters to figure out what has
        // actually happened with the wsn

        if ( wsn.getExposure() != Double.POSITIVE_INFINITY ) {
            System.out.println("Finished:\t" + curRun);
            exposure += wsn.getExposure();
            distance += wsn.getDistance();
            return true;
        }
        return false;
    }

    //
    // Getter and Setters
    //
    public DistributionTypeEnum getDistributionType() {
        return dType;
    }

    void setDistributionType(DistributionTypeEnum distributionTypeEnum) {
        dType = distributionTypeEnum;
    }


}
