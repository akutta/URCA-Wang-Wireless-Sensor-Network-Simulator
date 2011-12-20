/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package InitializationPackage;
import IntruderDetectionWSN.*;
import NodePackage.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author akutta
 */
public class InitializeRandom extends Initialize {

    private Random numberGen;

    
    @Override
    public Node[] initialize(Integer numNodes,
            Float sRange, Float cRange, WirelessSensorNetwork wsn)
    {
        
        Node nodes[] = new Node[numNodes + 1];
        Node base = initializeBase(sRange, cRange);
        nodes[0] = base;

        numberGen = new Random();
        
        for ( Integer i = 1; i < numNodes + 1; i++ ) {
            WirelessSensorNode wsNode = new WirelessSensorNode();

//            wsNode.setPosX(numberGen.nextInt(1000));
//            wsNode.setPosY(numberGen.nextInt(1000));
            
            GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
            wsNode.setPosX(numberGen.nextInt(gsv.getBoardSize()));
            wsNode.setPosY(numberGen.nextInt(gsv.getBoardSize()));


            wsNode.Initialize();
            wsNode.setCommunicationRange(cRange);
            wsNode.setSensingRange(sRange);
            wsNode.setWSN(wsn);
            
            nodes[i] = wsNode;
            //nodes.add(wsNode);
        }

        return nodes;
    }

}
