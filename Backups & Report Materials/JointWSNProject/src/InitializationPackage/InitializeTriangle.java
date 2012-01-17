/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package InitializationPackage;
import JointWSNProject.*;
import NodePackage.*;
import java.io.*;

/**
 *
 * @author akutta
 */
public class InitializeTriangle extends Initialize {

    static Boolean bInit = false;  // Set to true to output board

    @Override
    public Node[] initialize(Integer numNodes, WirelessSensorNetwork wsn)
    {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();

        Double l = Math.sqrt(Math.pow(grv.getBoardSize().doubleValue(),2.0) / (numNodes-1))* 1.05;

        Double halfX = l/2;
        Double dY = Math.floor(Math.sqrt(3.0) * l / 2);
        
        Double initPosX = 0.0;
        Double initPosY = 0.0;
        
        Integer curIndex = 0;
        Integer newNumNodes = 0;

        for ( Double height = 0.0; height < grv.getBoardSize(); height += dY ) {
            for ( Double width = 0.0; width < grv.getBoardSize(); width += l) {
                int tmpX = 0, tmpY = 0;
                if ( height %  2 * dY == 0 ) {
                    tmpX = initPosX.intValue() + width.intValue();
                    tmpY = initPosY.intValue() + height.intValue();
                } else {
                    tmpX = width.intValue() + halfX.intValue();
                    tmpY = initPosY.intValue() + height.intValue();
                }

                if ( tmpX > grv.getBoardSize() || tmpY > grv.getBoardSize() ) {
                    // Node is out of range so ignore it
                    //System.out.println("Attempted to add:\t" + nodes[curIndex].getPosX() + "," + nodes[curIndex].getPosY());
                } else {
                    // Valid Node Generated so increment the current Index
                    newNumNodes++;
                }
            }
        }

        Node nodes[] = new Node[newNumNodes];
        for ( int i = 0; i < nodes.length; i++ ) {
            nodes[i] = new Node();
        }


        for ( Double height = 0.0; height < grv.getBoardSize(); height += dY ) {
            for ( Double width = 0.0; width < grv.getBoardSize(); width += l) {
                if ( curIndex < newNumNodes ) {

                    if ( height %  2 * dY == 0 ) {
                        nodes[curIndex].setPosX(initPosX.intValue() + width.intValue());
                        nodes[curIndex].setPosY(initPosY.intValue() + height.intValue());
                    } else {
                        nodes[curIndex].setPosX(initPosX.intValue() + width.intValue() + halfX.intValue());
                        nodes[curIndex].setPosY(initPosY.intValue() + height.intValue());
                    }

                    if ( nodes[curIndex].getPosX() > grv.getBoardSize() || nodes[curIndex].getPosY() > grv.getBoardSize() ) {
                        // Node is out of range so ignore it
                        //System.out.println("Attempted to add:\t" + nodes[curIndex].getPosX() + "," + nodes[curIndex].getPosY());
                    } else {
                        // Valid Node Generated so increment the current Index
                        curIndex++;
                    }
                }
            }
        }

        if ( bInit == true )
        {
            try {
                PrintWriter out = new PrintWriter(new FileWriter("TriangleDistributionTest.csv"));
                for ( int i = 0; i < curIndex; i++ ) {
                    out.write(nodes[i].getPosX() + "," + nodes[i].getPosY()+ "\n");
                }
                out.close();
            } catch (IOException ex) {
            }

            bInit = true;
            System.exit(0);
        }
        

        return nodes;
    }
}
