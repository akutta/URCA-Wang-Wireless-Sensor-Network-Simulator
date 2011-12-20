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

// Based on Dijkstra's Algorithm
public class ShortestPathToBase extends TransmissionMethod {
    @Override
    public Node getNextNode(Node curNode) {
        return curNode.getHeadNode();
    }

    @Override
    public void transmitAll() {
        // ignore the base station
        for ( int i = 1; i < theNodes.length; i++ ) {
            theNodes[i].transmit(theNodes[i].getHeadNode());
        }
    }
}
