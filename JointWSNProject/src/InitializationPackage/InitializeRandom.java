/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package InitializationPackage;
import JointWSNProject.*;
import NodePackage.*;
import java.util.Random;

/**
 *
 * @author akutta
 */
public class InitializeRandom extends Initialize {

    private Random numberGen;

    
    @Override
    public Node[] initialize(Integer numNodes, WirelessSensorNetwork wsn)
    {   
        Node nodes[] = new Node[numNodes];
        for ( int i = 0; i < numNodes; i++ ) {
            nodes[i] = new Node();
        }

        numberGen = new Random();
        
        for ( Integer i = 0; i < numNodes; i++ ) {
            //WirelessSensorNode wsNode = new WirelessSensorNode();

            GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
            nodes[i].setPosX(numberGen.nextInt(gsv.getBoardSize()));
            nodes[i].setPosY(numberGen.nextInt(gsv.getBoardSize()));
        }

        return nodes;
    }

}
