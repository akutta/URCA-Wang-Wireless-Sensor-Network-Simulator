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

        Node nodes[] = new Node[numNodes + 1];
        //Node nodes[] = new Node[numberNodes.intValue() + 1];
        Node base = initializeBase(sRange, cRange);
        nodes[0] = base;

        Double l = Math.sqrt(Math.pow(grv.getBoardSize().doubleValue(),2.0) / numNodes)* 1.05;

        Double halfX = l/2;
        Double dY = Math.floor(Math.sqrt(3.0) * l / 2);
        
        Double initPosX = 0.0;
        Double initPosY = dY;
        
        Integer curIndex = 1;

        for ( Double height = 0.0; height < grv.getBoardSize(); height += dY ) {
            for ( Double width = halfX/2; width < grv.getBoardSize() - sRange; width += l) {
                WirelessSensorNode wsNode = new WirelessSensorNode();
                if ( curIndex <= numNodes ) {

                    if ( height %  2 * dY == 0 ) {
                        wsNode.setPosX(initPosX.intValue() + width.intValue());
                        wsNode.setPosY(initPosY.intValue() + height.intValue());
                    } else {
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
        }

        //System.out.println("curIndex:\t" + curIndex);

        if ( bInit == false ) {
            try {
                PrintWriter out = new PrintWriter(new FileWriter("TriangleDistributionTest.csv"));
                for ( int i = 0; i < curIndex - 1; i++ ) {
                    out.write(nodes[i+1].getPosX() + "," + nodes[i+1].getPosY()+ "\n");
                }
                out.close();
            } catch (IOException ex) {
            }

            bInit = true;
        }
        //java.lang.System.exit(0);

        return nodes;
    }
}
