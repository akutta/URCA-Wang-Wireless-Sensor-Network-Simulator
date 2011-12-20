/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package NodePackage;

import EnumDefinitionPackage.SensingTypeEnum;
import IntruderDetectionWSN.GlobalRuntimeVariables;

/**
 *
 * @author akutta
 */
public class BaseStation extends Node {
    private int detections = 0;
    private SensingTypeEnum sType;
    private Boolean successfulDetection;

    private Node[] nodesWithDetection;
    private Integer curNode;

    private Double totalDetectionDistance;
    private Integer totalDetectionHops;

    public void resetDistances() {
        totalDetectionDistance = 0.0;
        curNode = 0;
        successfulDetection = false;
        detections = 0;
        totalDetectionHops = 0;
        remainingJoules = -1.0; // Doesn't matter base is powered
    }

    @Override
    public void Update() {

        if ( gsv == null ) {
            gsv = GlobalRuntimeVariables.getInstance();
        }

        if ( sType == SensingTypeEnum.K_INDIVIDUAL ) {
            if ( detections >= gsv.getK() ) {
                successfulDetection = true;
            }
        } else if ( sType == SensingTypeEnum.K_SIMULTANEOUS ) {
            Integer tmpPositiveDetections = 0;
            if ( curNode >= gsv.getK() ) {
                for ( int i = 0; i < gsv.getK(); i++ ) {
                    for ( int j = 0; j < gsv.getK(); j++ ) {
                        if ( i != j ) {
                            if ( nodesWithDetection[i].checkWithinSensingRange(nodesWithDetection[j].getPosX().floatValue(),
                                    nodesWithDetection[j].getPosY().floatValue())) {
                                tmpPositiveDetections++;
                            }
                        }
                    }
                }
            }
            if ( tmpPositiveDetections >= gsv.getK() )
                successfulDetection = true;
            curNode = 0;
        }
    }

    public Double getTotalTransmitDistance() {
        return totalDetectionDistance;
    }

    public Boolean successfullyDetectedIntruder() {
        return successfulDetection;
    }

    public Integer getTotalHopsToBase() {
        return totalDetectionHops;
    }

    public void recieveDetection(Node node) {
        if ( node.getShortestDistanceToBase() > 0 ) // Means it actually Connects
        {
            if ( notPreviousNode(node) )    // Don't proceed if we already know it's not legit
                detections++;
            else
                return;
            
            totalDetectionHops += node.getShortestHopsToBase();
            totalDetectionDistance += node.getShortestDistanceToBase();
            if ( sType != null ) {
                if ( sType == SensingTypeEnum.SINGLE_DETECTION ) {
                    successfulDetection = true;
                }  else if (sType == SensingTypeEnum.K_SIMULTANEOUS) {
                    //
                    // In this case recieve will be called
                    // Every time there is a detection and each detection won't be handled quickly
                    // As a result reset all the "detected" nodes in the update() function
                    //
                    // That way when three are detected in the same loop, we know for certain
                    // that they are all lumped together
                    //
                    nodesWithDetection[curNode] = node;
                    curNode++;
                } else {
                    if ( notPreviousNode(node) && curNode < 3 ) {
                        nodesWithDetection[curNode] = node;
                        curNode++;
                    }
                }
            }
        }
    }

    private Boolean notPreviousNode(Node node) {
        for ( int i = 0; i < curNode; i++ ) {
            if ( nodesWithDetection[i] == node )
                return false;
        }
        return true;
    }

    public void setSensingType(SensingTypeEnum sensingType) {
        sType = sensingType;
        if ( sType == SensingTypeEnum.K_SIMULTANEOUS )
            nodesWithDetection = new Node[400];
    }

    @Override
    public void Initialize() {
        // Base Station located in middle of network at far right side
        if ( gsv == null )
            gsv = GlobalRuntimeVariables.getInstance();

        // For Energy Conservation, putting Base station in the center of the board
/*        posX = gsv.getBoardSize() / 2;
        posY = gsv.getBoardSize() / 2;*/
        posX = gsv.getBoardSize();
        posY = gsv.getBoardSize() / 2;

        detections = 0;
        totalDetectionHops = 0;
        bFinalized = false;
        distanceToHead = -1.0;
        distanceToBase = -1.0;
        communicationRange = -1.0f;
        sType = null;
        successfulDetection = false;
        nodesWithDetection = new Node[3];
        curNode = 0;
        totalDetectionDistance = 0.0;
    }

    @Override
    public void recieve() {
    }

    @Override
    public void transmit(Node transmitTo) {
    }

    public void updatePosition(Integer baseDistanceFromCenter) {
        if ( baseDistanceFromCenter != -1 )
            posX = gsv.getBoardSize() / 2 + baseDistanceFromCenter;
    }
}
