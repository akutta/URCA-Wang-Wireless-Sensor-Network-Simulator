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
        Node nodes[] = new Node[numNodes + 1];
        Double l = Math.sqrt(Math.pow(grv.getBoardSize().doubleValue(),2.0) / numNodes);
        Node base = initializeBase(sRange, cRange);
        nodes[0] = base;
        
        Double initPosX = l/2;
        Double initPosY = l/2;
        Integer curIndex = 1;

        for ( Double height = 0.0; height < grv.getBoardSize(); height += l ) {
            for ( Double width = 0.0; width < grv.getBoardSize(); width += l) {
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
