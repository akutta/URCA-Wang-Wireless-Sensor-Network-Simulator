/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderPackage;

import JointWSNProject.GlobalRuntimeVariables;
import JointWSNProject.WirelessSensorNetwork;
import NodePackage.Node;
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

    WirelessSensorNetwork wsn = null;
    double  posX, posY;
    double  totDistance;
    double  totExposure;
    double  K;
    boolean bAllSensorModel = false;

    int moves = 0;


    double lastExposure;
    private Node[] nodes;
    private CGrid[] grid;

    boolean bUpdating = false;
    boolean bInit = false;
    boolean bLogging = false;
    PrintWriter logger = null;

    public Intruder(WirelessSensorNetwork wsn, boolean bLog) {
        bUpdating = false;
        bInit = false;
        this.wsn = wsn;
        nodes = wsn.getNodes();

        totDistance = 0.0;
        bLogging = bLog;
        posX = 0.0;
        posY = 0.0;
        
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();

        K = gsv.getK();

        lastExposure = Evaluate(posX,posY);

        bInit = true;
    }

    public boolean update(int n, int m, boolean bLog) {

        if ( !bInit ) return false;
        if ( bUpdating ) return false;
        PrintWriter out = null;

        if ( bLog ) {
            try {
                out = new PrintWriter(new FileWriter("nodes.csv"));
                for ( int i = 0; i < nodes.length; i++ ) {
                    // Output Node Locations
                    out.write(nodes[i].getPosX() + "," + nodes[i].getPosY() + "\n");
                }
                out.close();

                // Save for Path Output
                out = new PrintWriter(new FileWriter("path_" + n + "_" + m + ".csv"));

            } catch (IOException ex) {
            }
        }

        bUpdating = true;

        // This generates the locations of the grid points
        // as well as creates the edges
        // and evaluates
        GenerateGrid(n,m);

        // Dijkstra
        dijkstras();

        int curIndex = 0;
        int previousIndex = 0;
        while ( grid[curIndex].getHead() != -1 ) {

            if ( bLog ) {
                out.write(grid[curIndex].posX + "," + grid[curIndex].posY + "\n");
            }


            moves++;
            totDistance += distance(grid[curIndex].getPosition(),grid[previousIndex].getPosition());

            previousIndex = curIndex;
            curIndex = grid[curIndex].getHead();
        }

        if ( bLog ) {
            out.write(grid[curIndex].posX + "," + grid[curIndex].posY + "\n");
            out.close();
        }

        moves++;
        totExposure = grid[0].cost;
        totDistance += distance(grid[curIndex].getPosition(),grid[previousIndex].getPosition());


        bUpdating = false;
        return true;

    }

    public boolean finished() {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();

        // Return when we get to the extreme edge of the board
        if ( posX == posY && posY == grv.getBoardSize() ) {
            if ( logger != null ) {
                logger.close();
                logger = null;
            }
            return true;
        }

        return false;
    }

    private double distance(double[] curPos, double[] nextPos) {
        double dx = nextPos[0] - curPos[0];
        double dy = nextPos[1] - curPos[1];
        return java.lang.Math.sqrt(dx*dx + dy*dy);
    }

    private double Evaluate(double posX, double posY) {
        double exposure = 0.0;
        if ( bAllSensorModel ) {
            for ( int i = 0; i < nodes.length; i++ ) {
                double[] curPos = {posX, posY};
                double[] nextPos = { nodes[i].getPosX(), nodes[i].getPosY() };
                exposure += 1 / ( Math.pow( distance( curPos , nextPos ), K) );
            }
        } else {
            double[] curPos = {posX, posY};
            double[] nodePos = { nodes[0].getPosX(), nodes[0].getPosY() };

            double bestDist = distance(curPos, nodePos);

            // Find the closest node to current location
            for ( int i = 1; i < nodes.length; i++ ) {
                double[] tmpPos = { nodes[i].getPosX(), nodes[i].getPosY() };
                double tmpDist = distance(curPos, tmpPos);
                if ( tmpDist < bestDist ) {
                    bestDist = tmpDist;
                }
            }

            return ( 1 / ( Math.pow( bestDist, K)));
        }

        return exposure;
    }

    public double getExposure() {
        return totExposure;
    }

    public double getDistance() {
        return totDistance;
    }

    private void GenerateGrid(int n, int m) {
        grid = new CGrid[(m*n+1) * (m*n+1)];
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
        int curPos = 0;

        for ( int i = 0; i < grid.length; i++ ) {
            grid[i] = new CGrid();
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
        Vector<Integer> gridIndexies = new Vector<Integer>();
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
        double xCoordBase = x * gsv.getBoardSize() / n;
        double yCoordBase = y * gsv.getBoardSize() / n;
        double subDelta = (gsv.getBoardSize() / n) / m;

        // Generate all sub-grid coordinates
        for ( int j = 0; j <= m; j++ ) {
            for ( int i = 0; i <= m; i++ ) {
                if ( curIndex < grid.length ) {
                    //System.out.println("curIndex:\t" + curIndex);
                    if ( !grid[curIndex].bInitialized )
                    {
                        double[] pos = { xCoordBase + i * subDelta, yCoordBase + j * subDelta };
                        grid[curIndex].setPosition(pos[0], pos[1]);
                        grid[curIndex].bInitialized = true;
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
            //System.out.println("\t\tcurIndex:\t" + gridIndexies.get(i) );
            for ( int j = 0; j < gridIndexies.size(); j++ ) {
                CGrid curPosition = grid[gridIndexies.get(i)];
                double[] curPos = curPosition.getPosition();
                if ( i != j ) {
                    double[] nextPos = grid[gridIndexies.get(j)].getPosition();
                    // Compute Distance
                    double dist = distance(curPos,nextPos);

                    // Compute Mid-point on grid
                    double tmpPosX = (nextPos[0] - curPos[0]) / 2;
                    double tmpPosY = (nextPos[1] - curPos[1]) / 2;
                    tmpPosX += curPos[0];
                    tmpPosY += curPos[1];

                    // Evaluate here
                    double tmpExposure = Evaluate(tmpPosX,tmpPosY);
                    tmpExposure *= dist;    // ~ to estimating integral from curPos to nextPos
                    if ( tmpExposure == 0.0 ) {
                        //System.out.println("dist:\t" + dist + "\tCur:" + curPos[0] + "," + curPos[1] + "\tNext:" + nextPos[0] + "," + nextPos[1]);;
                    }

                    //System.out.print("\t\t\tAttaching:\t" + gridIndexies.get(j));
                    grid[gridIndexies.get(i)].addIndex(gridIndexies.get(j), tmpExposure);
                    //grid[gridIndexies.get(i)].addIndex(gridIndexies.get(j));
                }
            }
        }
    }

    private void dijkstras() {
        // Begin at pS.
        // Try and get to pD
        LinkedList<Integer> openNodeList = new LinkedList<Integer>();
        openNodeList.add(grid.length - 1);
        Integer curGrid = -1;

        while ( openNodeList.size() > 0 ) {
            curGrid = getBestGrid(openNodeList,curGrid);

            for ( int i = 0; i < grid[curGrid].edges.size() - 1; i++ ) {
                Integer tmp = grid[curGrid].edges.get(i).arrayIndex;
                if ( !grid[tmp].bFinalized && !openNodeList.contains(tmp)) {
                    //System.out.println("Adding:\t" + tmp + ":\t" + grid[tmp].cost);
                    grid[tmp].setHead(curGrid);
                    grid[tmp].cost = grid[curGrid].cost + grid[curGrid].edges.get(i).Exposure;
                    openNodeList.add(tmp);
                }
            }

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
            
            //System.out.println("Potentials:\t" + curInt + ":\t" + grid[curInt].cost);

            if ( curSmallest == -1 ) {
                curSmallest = curInt;
            } else {
                if ( grid[curSmallest].cost > grid[curInt].cost ) {
                    curSmallest = curInt;
                }
            }
        }

        //System.out.println("Chose:\t" + curSmallest + ":\t" + grid[curSmallest].cost);

        //System.exit(1);
        return curSmallest;
    }

}