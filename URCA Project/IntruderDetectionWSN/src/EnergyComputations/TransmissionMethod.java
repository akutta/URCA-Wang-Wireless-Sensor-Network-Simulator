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
public class TransmissionMethod {
    protected Node[] theNodes;

    public Node getNextNode(Node curNode) {
        return null;
    }

    public void setNodeList(Node[] nodes) {
        theNodes = nodes;
    }

    public Node[] getNodeList() {
        return theNodes;
    }

    public void transmitAll() {
        System.out.println("ERROR:\tTransmissionMethod.transmitAll()");
    }

    protected Double getDistance(Node curNode, Node node) {
        return Math.sqrt(Math.pow(curNode.getPosX() - node.getPosX(),2.0) + Math.pow(curNode.getPosY() - node.getPosY(),2.0));
    }
}
