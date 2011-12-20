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
public class InitializeSquare extends Initialize {
    @Override
    public Node[] initialize(Integer numNodes,
            Float sRange, Float cRange, WirelessSensorNetwork wsn)
    {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();
        Double l = Math.sqrt((grv.getBoardWidth().doubleValue() * grv.getBoardHeight().doubleValue()) / numNodes);
        
        Double initPosX = l/2;
        Double initPosY = l/2;
        Integer curIndex = 1;

        Integer numNeeded = 0;
        // Count the number of nodes we need
        for ( Double height = 0.0; height < grv.getBoardHeight(); height += l ) {
            for ( Double width = 0.0; width < grv.getBoardWidth(); width += l) {
                numNeeded++;
            }
        }

        Node nodes[] = new Node[numNeeded + 1];
        Node base = initializeBase(sRange, cRange);
        nodes[0] = base;

        //for ( Double height = 0.0; height < grv.getBoardSize(); height += l ) {
          //  for ( Double width = 0.0; width < grv.getBoardSize(); width += l) {
        for ( Double height = 0.0; height < grv.getBoardHeight(); height += l ) {
            for ( Double width = 0.0; width < grv.getBoardWidth(); width += l) {
                WirelessSensorNode wsNode = new WirelessSensorNode();
                
                wsNode.setPosX(initPosX.intValue() + width.intValue());
                wsNode.setPosY(initPosY.intValue() + height.intValue());

                wsNode.Initialize();
                wsNode.setSensingRange(sRange);
                wsNode.setCommunicationRange(cRange);
                wsNode.setWSN(wsn);

                nodes[curIndex] = wsNode;
                curIndex++;
            }
        }

        return nodes;
    }
}
