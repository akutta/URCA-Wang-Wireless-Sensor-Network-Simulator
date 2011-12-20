/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderPackage;

import GaussianExposure.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/**
 *
 * @author akutta
 */
public class Intruder {
    GlobalRuntimeVariables grv = null;
    WirelessSensorNetwork wsn = null;
    double  posX, posY;
    double  totDistance;
    double  totExposure;
    double  K;
    boolean bAllSensorModel = true;

    Integer destinationIndex = -1;
    Integer sourceIndex = -1;

    int moves = 0;

    private CGrid[] grid;

    boolean bUpdating = false;
    boolean bInit = false;
    PrintWriter logger = null;
    
    public Intruder(Vector<Node> newNodes) {
        nodes = newNodes;
        K = 2;
        bUpdating = false;
        bInit = false;
        grv = GlobalRuntimeVariables.getInstance();
        totDistance = 0.0;

        // TODO:  RANDOMIZE STARTING LOCATION
        double side = grv.getNextDouble();
        double maxValue = 2 * (grv.getRadius() + 1);

        if ( side < .25 ) { // far left side
            posX = 0;
            posY = grv.getNextDouble() * maxValue;
        } else if ( side < .5 ) { // top side
            posX = grv.getNextDouble() * maxValue;
            posY = 0;
        } else if ( side < .75 ) { // right side
            posX = maxValue;
            posY = grv.getNextDouble() * maxValue;
        } else { // bottom
            posX = grv.getNextDouble() * maxValue;
            posY = maxValue;
        }


        // This generates the locations of the grid points
        // as well as creates the edges
        // and evaluates
        GenerateGrid();
        sourceIndex = findClosestGridCoord(posX,posY);

        //System.out.println(destinationIndex + ":\t"+ grid[destinationIndex].posX + ", " + grid[destinationIndex].posY);

        bInit = true;
    }

    public boolean update(boolean bLog, Double sigma) {
        if ( !bInit ) return false;
        if ( bUpdating ) return false;
        PrintWriter outPath = null;
        //PrintWriter outGrid = null;
        PrintWriter outNetwork = null;
        PrintWriter outSensor = null;

        if ( bLog ) {
            try {
                // Save for Path Output
                boolean bExists = (new File(grv.getTypeDist() + "_Results")).exists();
                if ( !bExists ) {
                    boolean bCreated = new File(grv.getTypeDist() + "_Results").mkdir();
                    if ( bCreated ) {
                        System.out.println("Created Directory:\n\t" + grv.getTypeDist() + "_Results");
                    } else {
                        System.out.println("Error Creating Directory!!!");
                    }
                }
                outPath = new PrintWriter(new FileWriter(grv.getTypeDist() + "_Results//Path" + sigma + ".csv"));
                //outGrid = new PrintWriter(new FileWriter("Grid" + sigma + ".csv"));
                outNetwork = new PrintWriter(new FileWriter(grv.getTypeDist() + "_Results//NetworkLimits" + sigma + ".csv"));
                outSensor = new PrintWriter(new FileWriter(grv.getTypeDist() + "_Results//Sensors" + sigma + ".csv"));
            } catch (IOException ex) {
            }
        }

        bUpdating = true;

        // Dijkstra
        dijkstras();

        boolean bModifiedSource = false;
        int curIndex = sourceIndex;
        int previousIndex = sourceIndex;
        while ( grid[curIndex].getHead() != -1 ) {

            if ( distance(grid[curIndex].getPosition(),grid[destinationIndex].getPosition()) <= grv.getRadius() ) {
                // We don't want to know the information about anything outside
                // of the network
                // Radius

                if ( !bModifiedSource ) {
                    bModifiedSource = true;
                    sourceIndex = curIndex;
                }

                if ( bLog ) {
                    outPath.write(grid[curIndex].posX + "," + grid[curIndex].posY + "\n");
                }

                moves++;
                Double tmpDist = distance(grid[curIndex].getPosition(),grid[previousIndex].getPosition());
                totDistance += tmpDist;
            }

            previousIndex = curIndex;
            curIndex = grid[curIndex].getHead();
        }

        if ( bLog ) {
 /*           for ( int i = 0; i < grid.length; i++ ) {
                double[] pos = grid[i].getPosition();
                outGrid.write(pos[0] + "," + pos[1] + "\n");
            }
*/
            for ( double angle = 0.0; angle < 360.0; angle += 2 )
            {
                double posX = grv.getRadius() * Math.cos(angle) + (grv.getRadius() + 1);
                double posY = grv.getRadius() * Math.sin(angle) + (grv.getRadius() + 1);

                outNetwork.write(posX + "," + posY + "\n");
            }

            for ( int i = 0; i < nodes.size(); i++ ) {
                Node curNode = nodes.get(i);
                outSensor.println(curNode.getPosX() + "," + curNode.getPosY());
            }

            outPath.write(grid[curIndex].posX + "," + grid[curIndex].posY + "\n");
            outPath.close();
            outNetwork.close();
            outSensor.close();
        }

        moves++;
        totExposure = grid[sourceIndex].cost;
        Double tmpDist = distance(grid[curIndex].getPosition(),grid[previousIndex].getPosition());
        totDistance += tmpDist;

        bUpdating = false;
        return true;

    }

    private double distance(double[] curPos, double[] nextPos) {
        double dx = nextPos[0] - curPos[0];
        double dy = nextPos[1] - curPos[1];
        return java.lang.Math.sqrt(dx*dx + dy*dy);
    }

    private double AccurateEvaluate(double[] curPos, double[] nextPos) {
        double exposure = 0.0;
        double tmpPosX = curPos[0];
        double tmpPosY = curPos[1];

        int divisions = grv.getEvaluateDivisions();
        // These are all line segments, so we are going to figure out
        // the slope for these points.

        if ( divisions < 1 ) {
            System.err.println("Must set divisions > 0");
            System.exit(1);
        }

        // m = dY / dX
        double dx = nextPos[0] - curPos[0];
        double dy = nextPos[1] - curPos[1];

        // y = m*x + c
        // define c:
        //double c = curPos[1] - slope * curPos[0];

        double deltaX = dx / divisions;
        double deltaY = dy / divisions;

        //System.out.println("[ " + curPos[0] + ", " + curPos[1] + " ]");
        //System.out.println("[ " + nextPos[0] + ", " + nextPos[1] + " ]");

        for ( int i = 0; i < divisions; i++ ){
            double tmpExposure = 0.0;
            tmpPosX += deltaX;
            tmpPosY += deltaY;
            //System.out.print("\t[ " + tmpPosX + ", " + tmpPosY + " ]");
            tmpExposure = Evaluate(tmpPosX,tmpPosY);
            //System.out.println("\t" + tmpExposure);
            exposure += tmpExposure;
        }

        exposure = exposure / divisions;
        //System.out.println("\t" + exposure);

        return exposure;
    }


    private double Evaluate(double posX, double posY) {
        double exposure = 0.0;

        double[] curPos = { posX, posY };
  /*      double[] finishPos = { grv.getRadius() + 1, grv.getRadius() + 1 };
        if ( destinationIndex != -1 ) {
            finishPos = grid[destinationIndex].getPosition();
        }
*/
        // There is no exposure if outside of the grid
        //if ( distance(finishPos, curPos) > grv.getRadius() )
          //  return 0.0;

        if ( bAllSensorModel ) {
            for ( int i = 0; i < nodes.size()-1; i++ ) {
                double[] nextPos = nodes.get(i).getPosition();
                exposure += 1 / ( Math.pow( distance( curPos , nextPos ), K) );
            }
        } else {
            double[] nodePos = nodes.get(0).getPosition();
            double bestDist = distance(curPos, nodePos);

            // Find the closest node to current location
            for ( int i = 1; i < nodes.size() - 1; i++ ) {
                double[] tmpPos = nodes.get(i).getPosition();
                double tmpDist = distance(curPos,tmpPos);
                if ( tmpDist < bestDist) {
                    bestDist = tmpDist;
                }
            }
            return ( 1/ (Math.pow(bestDist, K)));
        }

        return exposure;
    }

    public double getExposure() {
        return totExposure;
    }

    public double getDistance() {
        return totDistance;
    }

    private void GenerateGrid() {
        int n = grv.getN();
        int m = grv.getM();
        grid = new CGrid[(m*n+1) * (m*n+1)];
        int curPos = 0;

        for ( int index = 0; index < grid.length; index++ ) {
            grid[index] = new CGrid();
        }
        
        for ( double y = 0.0; y < n; y += 1 ) {
            for ( double x = 0.0; x < n; x += 1 ) {
                //System.out.println("curPos:\t" + curPos);
                GenerateSubGrid(x,y,n,m,curPos);
                curPos += m; // increment by m 
            }
            curPos += m + n*m*(m-1);
        }

        
    }

    private void GenerateSubGrid(double x, double y, int n, int m, int curIndex) {
        double boardSize = 2*(grv.getRadius()+1);
        Vector<Integer> gridIndexies = new Vector<Integer>();
        double xCoordBase = x * boardSize / n;
        double yCoordBase = y * boardSize / n;
        double subDelta = (boardSize / n) / m;

        // Generate all sub-grid coordinates
        for ( int j = 0; j <= m; j++ ) {
            for ( int i = 0; i <= m; i++ ) {
                if ( curIndex < grid.length ) {
                    // Only add if we havn't initialized this node yet
                    // Otherwise it is just redundant
                    if ( !grid[curIndex].bInitialized )
                    {
                        double[] pos = { xCoordBase + i * subDelta, yCoordBase + j * subDelta };
                        grid[curIndex].setPosition(pos[0], pos[1]);
                        grid[curIndex].bInitialized = true;

                        if ( destinationIndex == -1 ) {
                            // destination is @ [ R+1, R+1 ]
                            double[] dest = { grv.getRadius() + 1, grv.getRadius() + 1 };
                            if ( distance(pos, dest) == 0 ) {
                                //System.out.println("Found destination index");
                                destinationIndex = curIndex;
                            }
                        }
                    }
                    gridIndexies.add(curIndex);
                }
                curIndex += 1;
            }
            curIndex += (n-1)*m;
        }

        // Add Edges
        //
        // Used a vector because otherwise it was going to require another loop that looked
        // identical to the sub-grid generations
        // which is a lot of extra process work
        for ( int i = 0; i < gridIndexies.size(); i++ ) {
            for ( int j = 0; j < gridIndexies.size(); j++ ) {
                CGrid curPosition = grid[gridIndexies.get(i)];
                double[] curPos = curPosition.getPosition();
                if ( i != j ) {
                    double[] nextPos = grid[gridIndexies.get(j)].getPosition();
                    // Compute Distance
                    double dist = distance(curPos,nextPos);

                    double exposure = AccurateEvaluate(curPos,nextPos);
/*
                    // Compute Mid-point on grid
                    double tmpPosX = (nextPos[0] - curPos[0]) / 2;
                    double tmpPosY = (nextPos[1] - curPos[1]) / 2;
                    tmpPosX += curPos[0];
                    tmpPosY += curPos[1];

                    // Evaluate here
                    double tmpExposure = Evaluate(tmpPosX,tmpPosY);
                    tmpExposure *= dist;    // ~ to estimating integral from curPos to nextPos
*/
                    grid[gridIndexies.get(i)].addIndex(gridIndexies.get(j), exposure * dist);
                }
            }
        }
    }

    private void dijkstras() {
        // Begin at pS.
        // Try and get to pD
        LinkedList<Integer> openNodeList = new LinkedList<Integer>();
        openNodeList.add(destinationIndex);
        //openNodeList.add(grid.length - 1);
        Integer curGrid = -1;

        while ( openNodeList.size() > 0 ) {
            curGrid = getBestGrid(openNodeList,curGrid);
            //System.out.println("Chose:\t" + curGrid);

            for ( int i = 0; i < grid[curGrid].edges.size() - 1; i++ ) {
                Integer tmp = grid[curGrid].edges.get(i).arrayIndex;
                if ( !grid[tmp].bFinalized && !openNodeList.contains(tmp)) {
                    //System.out.println("Adding:\t" + tmp + ":\t" + grid[tmp].cost);
                    grid[tmp].setHead(curGrid);
                    grid[tmp].cost = grid[curGrid].cost + grid[curGrid].edges.get(i).Exposure;
                    openNodeList.add(tmp);
                    //System.out.println("\tAdded:\t" + tmp);
                }
            }

            //System.out.println("Removed:\t" + curGrid);
            grid[curGrid].bFinalized = true;
            openNodeList.removeFirstOccurrence(curGrid);
        }
    }

    private Integer getBestGrid(LinkedList<Integer> openNodeList, Integer curGrid) {
        Integer curSmallest = -1;
        if ( openNodeList.size() <= 0 ) return -1;  // Sanity check
        Iterator<Integer> gridIterator = openNodeList.iterator();

        // Return the first entry on the openNodeList
        // there will only be this anyways
        if ( curGrid == -1 ) {
            curSmallest = gridIterator.next();
            grid[curSmallest].setCost(0.0);
            return curSmallest;
        }

        while ( gridIterator.hasNext() ) {
            Integer curInt = gridIterator.next();

            if ( curSmallest == -1 ) {
                curSmallest = curInt;
            } else {
                if ( grid[curSmallest].cost > grid[curInt].cost ) {
                    curSmallest = curInt;
                }
            }
        }

        return curSmallest;
    }

    private Integer findClosestGridCoord(double posX, double posY) {
        Integer bestIndex = 0;
        double[] location = { posX, posY };
        Double bestDist = distance(grid[0].getPosition(),location);

        for ( int i = 1; i < grid.length; i++ ) {
            Double tmpDist = distance(grid[i].getPosition(),location);
            if ( tmpDist < bestDist ) {
                if ( grv.getRadius() < distance(grid[i].getPosition(),grid[destinationIndex].getPosition()) ) {
                    bestDist = tmpDist;
                    bestIndex = i;
                }
            }
        }

        return bestIndex;
    }

    public void cleanup() {
        for ( int i = 0; i < grid.length; i++ ) {
            grid[i].edges.removeAllElements();
        }
    }

    private Vector<Node> nodes;
}