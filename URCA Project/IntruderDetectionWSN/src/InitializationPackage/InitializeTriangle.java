/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package InitializationPackage;
import IntruderDetectionWSN.*;
import NodePackage.*;
import java.io.*;

/**
 *
 * @author akutta
 */
public class InitializeTriangle extends Initialize {

    static Boolean bInit = false;

    @Override
    public Node[] initialize(Integer numNodes,
            Float sRange, Float cRange, WirelessSensorNetwork wsn)
    {

        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();

        //Double l = Math.sqrt(Math.pow(grv.getBoardSize().doubleValue(),2.0) / numNodes);//* 1.05;
        //Double l = Math.sqrt(Math.pow(grv.getBoardWidth().doubleValue(),2.0) / numNodes);//* 1.05;
        Double l = Math.sqrt((grv.getBoardHeight().doubleValue() * grv.getBoardWidth().doubleValue())/numNodes);

        Double halfX = l/2;
        Double dY = Math.sqrt(3.0) * l / 2;//Math.floor(Math.sqrt(3.0) * l / 2);

        //System.out.println("dx:\t" + l + "\ndy:\t" + dY);
        
        Double initPosX = 0.0;
        Double initPosY = dY;
        
        Integer curIndex = 1;

        Integer numNeeded = 0;
        // Count the number of nodes we need
        for ( Double height = 0.0; height < grv.getBoardHeight() - dY / 2; height += dY ) {
            for ( Double width = halfX/2; width < grv.getBoardWidth(); width += l ) {
                numNeeded++;
            }
        }

        Node nodes[] = new Node[numNeeded + 1];
        //Node nodes[] = new Node[numberNodes.intValue() + 1];
        Node base = initializeBase(sRange, cRange);
        nodes[0] = base;

        //for ( Double height = 0.0; height < grv.getBoardSize() - dY / 2; height += dY ) {
        //    for ( Double width = halfX/2; width < grv.getBoardSize() ; width += l) {
        boolean bDoHalfX = false;
        for ( Double height = 0.0; height < grv.getBoardHeight() - dY / 2; height += dY ) {
            for ( Double width = halfX/2; width < grv.getBoardWidth() ; width += l) {
                WirelessSensorNode wsNode = new WirelessSensorNode();
                if ( curIndex <= numNeeded ) {

                    //System.out.println(Math.floor(height % (2 * dY)));
                    //if ( Math.floor(height % (2 * dY)) == 0 ) {
                    if ( !bDoHalfX ) {
                        //System.out.println("Skipping HalfX");
                        wsNode.setPosX(initPosX.intValue() + width.intValue());
                        wsNode.setPosY(initPosY.intValue() + height.intValue());
                    } else {
                        //System.out.println("Doing HalfX");
                        wsNode.setPosX(initPosX.intValue() + width.intValue() + halfX.intValue());
                        wsNode.setPosY(initPosY.intValue() + height.intValue());
                    }

                    wsNode.Initialize();
                    wsNode.setCommunicationRange(cRange);
                    wsNode.setSensingRange(sRange);
                    wsNode.setWSN(wsn);

                    nodes[curIndex] = wsNode;
                    curIndex++;
                }
            }
            bDoHalfX = !bDoHalfX;
        }
        return nodes;
    }
}
