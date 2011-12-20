/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JointWSNProject;

import InitializationPackage.*;
import IntruderPackage.Intruder;
import NodePackage.Node;

/**
 *
 * @author KuttaA
 */
public class WirelessSensorNetwork {
    private Intruder intruder;
    private boolean bInitialized;
    private DistributionTypeEnum dType;
    private Integer numNodes;
    private Node[] nodes;
    private boolean bUpdating;
    private boolean bFinishedRun;
    private double totalExposure;
    private double totalDistance;

    public double getDistance() {
        return totalDistance;
    }
    
    public double getExposure() {
        return totalExposure;
    }

    public WirelessSensorNetwork() {
        GlobalRuntimeVariables grv = GlobalRuntimeVariables.getInstance();
        dType = grv.getGlobalDType();
        totalExposure = 0.0;
        totalDistance = 0.0;
    }

    public void reset() {
        bFinishedRun = false;
    }

    public void initialize(boolean bLog)
    {
        bUpdating = false;
        bInitialized = false;
        bFinishedRun = false;

        Initialize initializer = null;

        if ( dType == DistributionTypeEnum.RANDOM  ) {
            initializer = new InitializeRandom();
        } else if ( dType == DistributionTypeEnum.TRIANGLE ) {
            initializer = new InitializeTriangle();
        } else if ( dType == DistributionTypeEnum.SQUARE ) {
            initializer = new InitializeSquare();
        } else if ( dType == DistributionTypeEnum.HEXAGONAL ) {
            initializer = new InitializeHexagon();
        } else if ( dType == DistributionTypeEnum.CROSS ) {
            initializer = new InitializeCross();
        }

        if ( initializer != null ) {
            nodes = initializer.initialize(GlobalRuntimeVariables.getInstance().getNumNodes(), this);
        }

        if ( nodes == null ) {
            System.out.println("Error Initializing Nodes!!!\n\tDistributionType:\t" + dType);
            System.exit(0);
            return;
        }

        intruder = new Intruder(this,bLog);

        bInitialized = true;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    void update(boolean bLog) {
        if ( !bInitialized )
            return;
        
        bUpdating = true;

        // Run shit in here

        // Since this is a program purely based around the intruder itself
        // We do not need to worry about energy consuption fo the nodes
        // rather just where they are relative to the intruder
        //
        // As such only update the intruder
        if ( intruder.update(32,4,bLog) ) {
            intruder.update(8,1,bLog);
            intruder.update(16,2,bLog);
            totalExposure = intruder.getExposure();
            totalDistance = intruder.getDistance();
            bFinishedRun = true;
        }

        bUpdating = false;
    }
    
    boolean finishedRun() {
        return bFinishedRun;
    }

    boolean isUpdating() {
        return bUpdating;
    }


}
