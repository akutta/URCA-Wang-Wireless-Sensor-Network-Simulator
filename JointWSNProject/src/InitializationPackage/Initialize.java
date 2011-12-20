/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package InitializationPackage;

import JointWSNProject.*;
import NodePackage.*;

/**
 *
 * @author akutta
 */
public class Initialize {
    public Node[] initialize(Integer numNodes, WirelessSensorNetwork wsn)
    {
        Node nodes[] = new Node[numNodes + 1];
        return nodes;
    }

    protected Integer getBoardSize() {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();
        return grv.getBoardSize();
    }
}
