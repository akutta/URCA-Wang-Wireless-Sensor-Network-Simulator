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
public class InitializeHexagon extends Initialize {

    static Boolean bInit = true;

    @Override
    public Node[] initialize(Integer numNodes, WirelessSensorNetwork wsn)
    {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();

        Double l = Math.sqrt(Math.pow(grv.getBoardSize().doubleValue(),2.0) / numNodes);
         Double initPosX = 0.0;
        Double initPosY = 0.0;
        Integer curIndex = 0;
        Integer var = 1;

        Integer newNumNodes = 0;

        {
            for ( Double height = 0.0; height < grv.getBoardSize(); height += ((Math.sqrt(3)/2)*l)*.98 ) {
                Double width = 0.0;
                while(initPosX + width <= grv.getBoardSize()){
                   // System.out.println((initPosX.intValue()+width.intValue()) + "," + (initPosY.intValue() + height.intValue()) );
                    if(var == 1){
                        var = 0;
                        width = width + l;
                    }
                    else{
                        var = 1;
                        width = width + 3*l;
                    }
                    newNumNodes++;
                }
                if(initPosX == 0.0){
                    initPosX = (2*l);
                }
                else{
                    initPosX = 0.0;
                }
            }
        }

        initPosX = 0.0;
        initPosY = 0.0;

        //System.out.println(newNumNodes);

        if ( numNodes == 26 ) {
            newNumNodes--;
        }
        Node nodes[] = new Node[newNumNodes+1];
        for ( int i = 0; i < nodes.length; i++ ) {
            nodes[i] = new Node();
        }



        for ( Double height = 0.0; height < grv.getBoardSize(); height += ((Math.sqrt(3)/2)*l)*.98 ) {
            Double width = 0.0;
            while(initPosX + width <= grv.getBoardSize()){
                //System.out.println(curIndex);
                nodes[curIndex].setPosX(initPosX.intValue() + width.intValue());
                if(var == 1){
                    var = 0;
                    width = width + l;
                }
                else{
                    var = 1;
                    width = width + 3*l;
                }
                nodes[curIndex].setPosY(initPosY.intValue() + height.intValue());
                curIndex++;
            }
            if(initPosX == 0.0){
                initPosX = (2*l);
            }
            else{
                initPosX = 0.0;
            }
        }

        //System.out.println("curIndex:\t" + curIndex);


        if ( bInit == false )
        {
            try {
                PrintWriter out = new PrintWriter(new FileWriter("HexagonalDistributionTest.csv"));
                for ( int i = 0; i < nodes.length; i++ ) {
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
