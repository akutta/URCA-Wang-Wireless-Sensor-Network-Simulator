/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN.Drivers;

import EnumDefinitionPackage.SensingTypeEnum;
import IntruderDetectionWSN.GlobalRuntimeVariables;
import IntruderDetectionWSN.Threads.VariableBaseLocationThread;

/**
 *
 * @author KuttaA
 */
public class VariableBaseLocationDriver {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        new VariableBaseLocationDriver();
    }

    //
    // This is used for none GUI Simulation work
    //
    public VariableBaseLocationDriver() {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();
        idThread = new VariableBaseLocationThread[3];  // 3 types of sensing can be done

        //
        // Create threads for all sensing types requested
        //
        if ( grv.getGlobalSType() == SensingTypeEnum.ALL ) {
            // SINGLE DETECTION
            idThread[0] = new VariableBaseLocationThread();
            idThread[0].setSensingType(SensingTypeEnum.SINGLE_DETECTION);
            idThread[0].setIntruderType(grv.getGlobalIType());
            idThread[0].setDistributionType(grv.getGlobalDType());

            // 3 - INDIVIDUAL DETECTION
            idThread[1] = new VariableBaseLocationThread();
            idThread[1].setSensingType(SensingTypeEnum.K_INDIVIDUAL);
            idThread[1].setIntruderType(grv.getGlobalIType());
            idThread[1].setDistributionType(grv.getGlobalDType());

            // 3 - SIMULTANEOUS DETECTION
            idThread[2] = new VariableBaseLocationThread();
            idThread[2].setSensingType(SensingTypeEnum.K_SIMULTANEOUS);
            idThread[2].setIntruderType(grv.getGlobalIType());
            idThread[2].setDistributionType(grv.getGlobalDType());

            idThread[0].start();
            idThread[1].start();
            idThread[2].start();
        } else {
            idThread[0] = new VariableBaseLocationThread();
            idThread[0].setSensingType(grv.getGlobalSType());
            idThread[0].setIntruderType(grv.getGlobalIType());
            idThread[0].setDistributionType(grv.getGlobalDType());
            idThread[0].start();
        }
    }

    VariableBaseLocationThread idThread[];

}
