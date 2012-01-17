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
 * @author KuttaA
 */
public class InitializeCross extends Initialize {
    private boolean bInit = false;  // Set to true to output board

    @Override
    public Node[] initialize(Integer numNodes, WirelessSensorNetwork wsn)
    {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();
        Node nodes[] = new Node[numNodes+1];
        for ( int i = 0; i < nodes.length; i++ ) {
            nodes[i] = new Node();
        }

        Double l = grv.getBoardSize().doubleValue() / Math.floor((numNodes-1)/2);

        //Double l = grv.getBoardSize().doubleValue() / (Math.sqrt(numNodes)-1);
        double size = (grv.getBoardSize()/2.0);
        Double initPosX = 0.0;
        Double initPosY = 0.0;
        Integer curIndex = 0;


        //do work here
        for(Double j = 0.0; j <= (grv.getBoardSize()); j += l){
                nodes[curIndex].setPosX((int)size);
                nodes[curIndex].setPosY(initPosY.intValue() + j.intValue());
                curIndex++;
        }
        for(Double k = 0.0; k <= grv.getBoardSize(); k+=l){
                nodes[curIndex].setPosX(initPosX.intValue() + k.intValue());
                nodes[curIndex].setPosY((int)size);
                curIndex++;
        }

      //  System.out.println("NumNodes:\t" + (curIndex - 1));
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

//        System.exit(0);
        return nodes;

    }
}
