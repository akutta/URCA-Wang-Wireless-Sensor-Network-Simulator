/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN;

import EnergyComputations.*;
import EnumDefinitionPackage.*;
import NodePackage.*;
import IntruderPackage.*;
import InitializationPackage.*;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author akutta
 */
public class WirelessSensorNetwork { 
    private Boolean bWasDetection;
    private Integer unConnectedNodes;
    
    private Float sRange;
    private Float cRange;

    private DistributionTypeEnum dType;
    private SensingTypeEnum sType;
    private IntruderTypeEnum iType;
    private EnergyComputationEnum eType;

    private Integer numNodes;

    private BaseStation base;
    private Node[] nodes;
    private Intruder intruderTest;

    private Boolean bFinishedRun;
    private Boolean bUpdating;
    private Boolean bInitialized;

    private long startTime, endTime;

    public Intruder getIntruder() {
        return intruderTest;
    }

    public Integer wasDetection() {
        if ( bWasDetection )
            return 1;
        return 0;
    }

    public Boolean isUpdating() {
        return bUpdating;
    }

    public long getTimeToDetection() {
        return endTime - startTime;
    }

    public Double getTotalTransmitDistance() {
        return base.getTotalTransmitDistance();
    }

    public Integer getUnconnectedNodeCount() {
        return unConnectedNodes;
    }
    //
    //  Reset the intruder as well as any detection information
    //  so the same board can be reused.
    //
    public void reset() {
        intruderTest.reset();
        base.resetDistances();

        bFinishedRun = false;
        startTime = 0;
        endTime = 0;

        for ( int i = 0; i < nodes.length; i++ ) {
            nodes[i].reset();
        }

        // Rerun Dijkstra  Only necessary for energy dissipation calculation
        //dijkstra();
    }

    public void updateEnergy() {
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
        TransmissionMethod transmitter = gsv.getTransmitter();
        bUpdating = true;
        transmitter.setNodeList(nodes);

        transmitter.transmitAll();

        nodes = transmitter.getNodeList();

        dijkstra();
        
        bUpdating = false;
    }

    public void updateDetection() {
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
        bUpdating = true;
        if ( bInitialized ) {
            if ( intruderTest != null ) {
                if ( base != null ) {
                    intruderTest.update();
                    base.Update();
                    if ( intruderTest.getPosX() >= 0 && startTime == 0 ) {
                        startTime = System.nanoTime();
                    }
                    checkDetectedLocation( intruderTest.getPosX(),intruderTest.getPosY() );
                    //if ( base.successfullyDetectedIntruder() || intruderTest.getPosX() >= 1000 ) {
                    if ( base.successfullyDetectedIntruder() || intruderTest.getPosX() >= gsv.getBoardSize() ) {
                        endTime = System.nanoTime();
                        //if ( intruderTest.getPosX() >= 1000 ) {
                        if ( intruderTest.getPosX() >= gsv.getBoardSize() ) {
                            bWasDetection = false;
                        } else {
                            bWasDetection = true;
                        }
                        bFinishedRun = true;
                    }
                } else {
                    System.out.println("Base Not Instatiated");
                }
            } else {
                System.out.println("No Intruder Created");
            }
        } else {
            System.out.println("Not Initialized yet");
        }
        bUpdating = false;
    }


    public void sendToBase(Integer node) {
        base.recieveDetection(nodes[node]);
    }

    public void checkDetectedLocation(Float posX, Float posY) {
        for ( int i = 0; i < nodes.length - 1; i++ ) {
            if ( nodes[i].checkWithinSensingRange(posX,posY) && posX > 0 ) {
                sendToBase(i);
            }
        }
    }

    public Integer getTotalHopsToBase() {
        return base.getTotalHopsToBase();
    }

    public Double getDistanceTraveled() {
        return intruderTest.getDistanceTraveled();
    }

    public Boolean finishedRun() {
       // if ( bFinishedRun )
        //    System.out.println(intruderTest.numberOfLocations());
        return bFinishedRun;
    }

    public void setRunParameters(Float sensingRange, Float communicationRange,
            DistributionTypeEnum distributionType, SensingTypeEnum sensingType,
            IntruderTypeEnum intruderType, EnergyComputationEnum energyType,
            Integer numberOfNodes, Integer baseDistanceFromCenter)
    {
        bInitialized = false;
        bUpdating = false;
        bFinishedRun = false;
        sRange = sensingRange;
        cRange = communicationRange;
        dType = distributionType;
        sType = sensingType;
        iType = intruderType;
        eType = energyType;
        numNodes = numberOfNodes;

        intruderTest = null;
        nodes = null;

        initialize(baseDistanceFromCenter);
    }

    private void initialize(Integer baseDistanceFromCenter)
    {
        bInitialized = false;

        unConnectedNodes = 0;
        
        startTime = 0;
        endTime = 0;

        Initialize initializer = null;

        // ADD INITIALIZERS TO HERE
        if ( iType == IntruderTypeEnum.RANDOM_INTRUDER ) {
            intruderTest = new RandomIntruder();
        } else if ( iType == IntruderTypeEnum.STRAIGHT_LINE ) {
            intruderTest = new StraightLineIntruder();
        } else {
            System.out.println("Unrecognized Intruder Type\n\tDefaulting to Stright Line");
            intruderTest = new StraightLineIntruder();
        }

        intruderTest.initialize();

        if ( dType == DistributionTypeEnum.RANDOM  ) {
            initializer = new InitializeRandom();
        } else if ( dType == DistributionTypeEnum.TRIANGLE ) {
            initializer = new InitializeTriangle();
        } else if ( dType == DistributionTypeEnum.SQUARE ) {
            initializer = new InitializeSquare();
        } else if ( dType == DistributionTypeEnum.HEXAGONAL ) {
            initializer = new InitializeHexagon();
        }

        if ( initializer != null ) {
            nodes = initializer.initialize(numNodes, sRange, cRange,this);
            base = (BaseStation)nodes[0];
            base.setSensingType(sType);
            base.updatePosition(baseDistanceFromCenter);
        } else {
            System.out.println("Error!!! Could not initialize Nodes");
        }

        if ( nodes == null ) {
            System.out.println("Error Initializing Nodes!!!");
            return;
        }
        dijkstra();
        bInitialized = true;
    }

    // Allow access to enable running when energy dissipation calculations are enabled
    private void dijkstra() {
        unConnectedNodes = 0;
        
        LinkedList<Integer> openNodeList = new LinkedList<Integer>();
        openNodeList.add(0);    // 0 is the base

        while ( openNodeList.size() > 0 ) {
            Integer curNode = getSmallestNode(openNodeList);

            for ( int i = 1; i < nodes.length; i++ ) {
                try {
                    if ( !nodes[i].getFinalized() ) {
                        if ( nodes[curNode].checkWithinCommunicationRange(nodes[i]) )
                        {
                            if ( !openNodeList.contains(i) ) {
                                nodes[i].setHeadNode(nodes[curNode]);
                                openNodeList.add(i);
                            }
                        }
                    }
                } catch (java.lang.NullPointerException e ) {
                    System.out.println("Max Nodes:\t" + nodes.length);
                    System.out.println("Error at:\t" + i);
                    System.exit(0);
                }
            }

            nodes[curNode].setFinalized(true);
            openNodeList.removeFirstOccurrence(curNode);
        }

        Integer j = 0, k = 0;
        for ( int i = 1; i < nodes.length; i++ ) {
            if ( nodes[i].getFinalized() )
                j++;
            else
                unConnectedNodes++;

            k++;
        }
        
    }

    private Integer getSmallestNode(LinkedList<Integer> open) {
        Integer curSmallest = -1;
        if ( open.size() <= 0 ) return -1;
        Iterator<Integer> nodeIterator = open.iterator();

        while ( nodeIterator.hasNext() ) {
            Integer curInt = nodeIterator.next();
            if ( curSmallest == -1 )
                curSmallest = curInt;
            else {

                if ( nodes[curSmallest].getShortestDistanceToBase() > nodes[curInt].getShortestDistanceToBase() )
                  curSmallest = curInt;
            }
        }
        return curSmallest;
    }

    public Float getSensingRange() {
        if ( nodes != null )
            if ( nodes[1] != null )
                return nodes[1].getSensingRange();

        return 0.0f;
    }

    public Integer getNodesAlive() {
        Integer nodesAlive = 0;
        for ( Integer i = 1; i < nodes.length; i++ ) {
            if ( nodes[i].getRemainingJoules() > 0 )
                nodesAlive++;
        }
        return nodesAlive;
    }

    public Node[] getNodes() {
        if ( bInitialized == false )
            return null;
        return nodes;
    }
}
