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
/**
 *
 * @author akutta
 */

//
// Not fully implemented yet.
//      I have some seperate code that works decently for smaller boards, working on the
//      scaling problem though.  Semi-Outsourced this portion for a different project and
//      the project return was terrible.
//
public class InitializeHexagon extends Initialize {

    static Boolean bInit = false;

    @Override
    public Node[] initialize(Integer numNodes,
            Float sRange, Float cRange, WirelessSensorNetwork wsn)
    {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();
        Node nodes[] = new Node[numNodes + 1];
        Node base = initializeBase(sRange, cRange);
        nodes[0] = base;

        Double l = Math.sqrt(Math.pow(grv.getBoardSize().doubleValue(),2.0) / numNodes);
        Integer curIndex = 1;

        if ( bInit == false ) {
            try {
                PrintWriter out = new PrintWriter(new FileWriter("HexagonalDistributionTest.csv"));
                for ( int i = 0; i < curIndex - 1; i++ ) {
                    out.write(nodes[i+1].getPosX() + "," + nodes[i+1].getPosY()+ "\n");
                }
                out.close();
            } catch (IOException ex) {
            }

            bInit = true;
        }
        java.lang.System.exit(0);

        return nodes;
    }
}
