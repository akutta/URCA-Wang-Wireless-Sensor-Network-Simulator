/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EnergyComputations;

import NodePackage.Node;

/**
 *
 * @author akutta
 */
public class MTE extends TransmissionMethod {
    @Override
    public Node getNextNode(Node curNode) {
        Double closestDistance = getDistance(curNode,theNodes[1]);
        Integer nodeNumber = 1;
        for ( int i = 2; i < theNodes.length; i++ ) {
            if ( curNode != theNodes[i] ) {
                Double tmpDistance = getDistance(theNodes[nodeNumber],theNodes[i]);
                if ( tmpDistance < closestDistance ) {
                    nodeNumber = i;
                    closestDistance = tmpDistance;
                }
            }
        }
        return theNodes[nodeNumber];
    }

    @Override
    public void transmitAll() {
        // ignore the base station
        for ( int i = 1; i < theNodes.length; i++ ) {
            theNodes[i].transmit(getNextNode(theNodes[i]));
        }
    }
}
