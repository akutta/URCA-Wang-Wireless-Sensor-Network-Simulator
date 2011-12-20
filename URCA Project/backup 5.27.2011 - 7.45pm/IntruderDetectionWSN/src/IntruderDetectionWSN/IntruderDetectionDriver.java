/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN;

import EnumDefinitionPackage.*;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author akutta
 */
public class IntruderDetectionDriver {

    /**
     * @param args the command line arguments
     */

    public IntruderDetectionDriver() {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();
        idThread = new IntruderDetectionThread[3];  // 3 types of sensing can be done

        //
        // Create threads for all sensing types requested
        //
        if ( grv.getGlobalSType() == SensingTypeEnum.ALL ) {
            // SINGLE DETECTION
            idThread[0] = new IntruderDetectionThread();
            idThread[0].setSensingType(SensingTypeEnum.SINGLE_DETECTION);
            idThread[0].setIntruderType(grv.getGlobalIType());
            idThread[0].setDistributionType(grv.getGlobalDType());

            // 3 - INDIVIDUAL DETECTION
            idThread[1] = new IntruderDetectionThread();
            idThread[1].setSensingType(SensingTypeEnum.K_INDIVIDUAL);
            idThread[1].setIntruderType(grv.getGlobalIType());
            idThread[1].setDistributionType(grv.getGlobalDType());

            // 3 - SIMULTANEOUS DETECTION
            idThread[2] = new IntruderDetectionThread();
            idThread[2].setSensingType(SensingTypeEnum.K_SIMULTANEOUS);
            idThread[2].setIntruderType(grv.getGlobalIType());
            idThread[2].setDistributionType(grv.getGlobalDType());

            idThread[0].start();
            idThread[1].start();
            idThread[2].start();
        } else {
            idThread[0] = new IntruderDetectionThread();
            idThread[0].setSensingType(grv.getGlobalSType());
            idThread[0].setIntruderType(grv.getGlobalIType());
            idThread[0].setDistributionType(grv.getGlobalDType());
            idThread[0].start();
        }
    }

    IntruderDetectionThread idThread[];

}
