/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package InitializationPackage;
import JointWSNProject.*;
import NodePackage.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author akutta
 */
public class InitializeSquare extends Initialize {
    private boolean bInit = true;  // Set to true to output board

    @Override
    public Node[] initialize(Integer numNodes, WirelessSensorNetwork wsn)
    {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();
        Node nodes[] = new Node[numNodes];
        for ( int i = 0; i < numNodes; i++ ) {
            nodes[i] = new Node();
        }

        Double l = grv.getBoardSize().doubleValue() / (Math.sqrt(numNodes) - 1);

        //Double l = Math.sqrt(Math.pow(grv.getBoardSize().doubleValue(),2.0) / (numNodes));
        
        //Double initPosX = l/2;
        //Double initPosY = l/2;
        
        Double initPosX = 0.0;
        Double initPosY = 0.0;
        
        Integer curIndex = 0;


        for ( Double height = 0.0; height <= grv.getBoardSize(); height += l ) {
            for ( Double width = 0.0; width <= grv.getBoardSize(); width += l) {
                nodes[curIndex].setPosX(initPosX.intValue() + width.intValue());
                nodes[curIndex].setPosY(initPosY.intValue() + height.intValue());

                //System.out.println(curIndex + ":\t" + nodes[curIndex].getPosX() + "," + nodes[curIndex].getPosY());
                //System.out.println(curIndex);
                curIndex++;
            }
        }
        
        if ( bInit == false )
        {
            try {
                PrintWriter out = new PrintWriter(new FileWriter("SquareDistributionTest.csv"));
                for ( int i = 0; i < curIndex; i++ ) {
                    out.write(nodes[i].getPosX() + "," + nodes[i].getPosY()+ "\n");
                }
                out.close();
            } catch (IOException ex) {
            }

            bInit = true;
        }

        //System.exit(0);

        return nodes;
    }
}
