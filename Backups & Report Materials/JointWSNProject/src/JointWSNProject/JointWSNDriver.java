/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JointWSNProject;

/**
 *
 * @author KuttaA
 */
public class JointWSNDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new JointWSNDriver();
    }

    public JointWSNDriver() {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();
        idThread = new WSNRuntimeThread[1];    // Only running one thread atm.  Set it up for multiple later
        // Brett, my desktop can only run a maximum of four threads at a time.  Any idea if yours can run more.
        // be nice to have the entire program run in one pass without changing the parameters
        //
        // Potentially easier answer:
        //      Run cross with the two parameters as well as three other distribution types.
        //      whichever thread finishes first destroy, create a new on with new parameters for untested
        //      distribution type.
        //
        //  I don't think the runs will take very long and all of them are predictable except for random
        //  which will take the longest
        //


        idThread[0] = new WSNRuntimeThread();
        //idThread[0].setSensingType(grv.getGlobalSType());     // Add this in later
        idThread[0].setDistributionType(grv.getGlobalDType());
        idThread[0].start();
    }

    private WSNRuntimeThread idThread[];
}
