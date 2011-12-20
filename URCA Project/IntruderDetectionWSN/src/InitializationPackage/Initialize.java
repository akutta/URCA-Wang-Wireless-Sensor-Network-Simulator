/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package InitializationPackage;

import IntruderDetectionWSN.*;
import NodePackage.*;

/**
 *
 * @author akutta
 */
public class Initialize {
    public Node[] initialize(Integer numNodes,
            Float sRange, Float cRange, WirelessSensorNetwork wsn)
    {
        Node nodes[] = new Node[numNodes + 1];
        return nodes;
    }

    protected Node initializeBase(Float sRange, Float cRange) {
        Node base = new BaseStation();
        base.Initialize();
        base.setCommunicationRange(cRange);
        base.setSensingRange(sRange);
        base.setShortestDistanceToBase(0.0);
        return base;
    }
/*
    protected Integer getBoardSize() {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();
        return grv.getBoardSize();
    }*/
}
