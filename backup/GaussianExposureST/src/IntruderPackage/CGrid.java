 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderPackage;

import java.util.Vector;

/**
 *
 * @author akutta
 */
public class CGrid {

    public class edge {
        public Integer arrayIndex;
        public Double Exposure;
    }

    public Vector<edge> edges;

    double cost;

    double posX, posY;
    boolean bInitialized = false, bFinalized = false;
    //Vector<Integer> connectedGridPoints;
    private Integer headIndex;

    public CGrid() {
        cost = 0;
        edges = new Vector<edge>();
        //connectedGridPoints = new Vector<Integer>();
        posX = -1;
        posY = -1;
        bInitialized = false; // initializiation
        bFinalized = false; // Dijkstra
        headIndex = -1;
    }


    void setCost(double d) {
        cost = d;
    }

    double[] getPosition() {
        double[] tmp = {posX,posY};
        return tmp;
    }

    void setPosition(double x, double y) {
        posX = x;
        posY = y;
    }


    public void addIndex(Integer newIndex, double exposure) {
        edge tmp = new edge();

        tmp.arrayIndex = newIndex;
        tmp.Exposure = exposure;

        // essentially Vector.contains() but we only care about the index
        boolean bContains = false;
        for ( int i = 0; i < edges.size() - 1; i++ ) {
            if ( edges.get(i).arrayIndex == newIndex ) {
                bContains = true;
            }
        }

        if ( !bContains ) 
            edges.add(tmp);
    }

    void setHead(Integer curGrid) {
        headIndex = curGrid;
    }
    
    Integer getHead() {
        return headIndex;
    }
}
