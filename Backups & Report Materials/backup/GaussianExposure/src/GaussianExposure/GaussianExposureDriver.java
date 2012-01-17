/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GaussianExposure;

import EnumerationDefinitions.*;
import IntruderPackage.*;
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
        wsn = new WirelessSensorNetwork();

        PrintWriter Results = null;

        try {
            Results = new PrintWriter(new FileWriter("Results_" + grv.getTypeDist() + ".csv"),false);
        } catch (IOException ex) {
            Logger.getLogger(GaussianExposureDriver.class.getName()).log(Level.SEVERE, null, ex);
        }

        Integer LogAt = grv.getNextInteger(grv.subCount);
        System.out.println("Logging @ " + LogAt);
        Integer subCount = 0;
        Integer curNodes = 0;
        Double totDistTraveled = 0.0;
        Double totExposure = 0.0;
        for ( int i = 0; i < 57*grv.subCount; i++ ) {
            
            //System.out.println("Current Nodes:\t" + curNodes);
            if ( grv.getTypeDist() == DistributionTypeEnum.TRUNCATED_GAUSSIAN )
                wsn.readPointsFromFile("TruncatedGaussianNodes//nodes" + curNodes + ".csv");
            else if ( grv.getTypeDist() == DistributionTypeEnum.GAUSSIAN ) {
                wsn.readPointsFromFile("GaussianNodes//nodes" + curNodes + ".csv");
            } else if ( grv.getTypeDist() == DistributionTypeEnum.NORMALIZED_RANDOM ) {
                wsn.readPointsFromFile("RandomizedNodes//nodes" + curNodes + ".csv");
            }
            intruder = new Intruder();
            if ( subCount == LogAt )
                intruder.update(true,grv.getSigma());
            else
                intruder.update(false,0.0);
            
            totDistTraveled += intruder.getDistance();
            totExposure += intruder.getExposure();

            grv.getNodes().removeAllElements();
            curNodes++;
            subCount++;

            if ( subCount == grv.subCount ) {
                subCount = 0;
                totDistTraveled /= grv.subCount;
                totExposure /= grv.subCount;
                System.out.println(grv.getSigma() + ":\tE = " + totExposure + "\tD = " + totDistTraveled);
                Results.write(grv.getSigma() + "," + totExposure + "," + totDistTraveled + "\n");

                totDistTraveled = 0.0;
                totExposure = 0.0;
            }

            intruder.cleanup();
            intruder = null;
        }
        Results.close();
    }
    

    private Intruder intruder;
    private WirelessSensorNetwork wsn;
    private GlobalRuntimeVariables grv;
}
