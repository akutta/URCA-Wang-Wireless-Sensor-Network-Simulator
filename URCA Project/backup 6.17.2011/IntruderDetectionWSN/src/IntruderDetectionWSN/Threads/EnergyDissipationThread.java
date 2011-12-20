/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN.Threads;

import IntruderDetectionWSN.*;
import java.io.PrintWriter;

/**
 *
 * @author KuttaA
 */
public class EnergyDissipationThread extends BaseThread {

    private void runTest(GlobalRuntimeVariables gsv, PrintWriter out) {
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
                wsn.setRunParameters(sRange,2*sRange,dType, sType, iType, eType, 400,-1);

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
}
