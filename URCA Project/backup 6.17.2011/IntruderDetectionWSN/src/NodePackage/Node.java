/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package NodePackage;

import IntruderDetectionWSN.GlobalRuntimeVariables;
import IntruderDetectionWSN.WirelessSensorNetwork;

/**
 *
 * @author akutta
 */
public class Node {
    protected Boolean bAlive;
    protected Boolean bFinalized;
    protected Integer posX, posY;
    protected Node headNode;
    protected Double distanceToHead;
    protected Double distanceToBase;
    protected Float communicationRange;
    protected Float sensingRange;
    protected Double remainingJoules;
    protected WirelessSensorNetwork wsn;
    protected GlobalRuntimeVariables gsv;

    protected Double Eelec = 0.00000005;      // 50 nJ
    protected Double eAmp = 0.0000000001;     // 100 pJ
    protected Integer k = 2000;               // Number of Bits Transmitted

    public void reset() {
        remainingJoules = GlobalRuntimeVariables.getInstance().getInitialJoules();
    }

    //
    // Used only by BaseStation
    // But allows for Sensor Node to
    // Update if needed
    //
    
    public void Initialize() {
        headNode = null;
        bFinalized = false;
        distanceToHead = -1.0;
        distanceToBase = -1.0;
        communicationRange = -1.0f;
        sensingRange = -1.0f;
        remainingJoules = GlobalRuntimeVariables.getInstance().getInitialJoules();
    }

    public Double getRemainingJoules() {
        return remainingJoules;
    }

    public void setRemainingJoules(Double remainingJoules) {
        this.remainingJoules = remainingJoules;
    }

    public void Update() {
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Double getShortestDistanceToBase()
    {
        return 0.0;
    }

    public void setHeadNode(Node hNode) {
        if ( distanceToHead == -1 || distanceToHead > getDistance(hNode.getPosX().floatValue(),hNode.getPosY().floatValue()) ) {
            //System.out.println("Setting Head Node");
            this.headNode = hNode;
            distanceToHead = getDistance(headNode.getPosX().floatValue(), headNode.getPosY().floatValue());
            distanceToBase = headNode.getShortestDistanceToBase() + distanceToHead;
        }
    }

    public Node getHeadNode() {
        return headNode;
    }

    public void setShortestDistanceToBase(Double distanceToBase) {
        this.distanceToBase = distanceToBase;
    }

    public float checkWithinCommunicationRange(Float intruderX, Float intruderY) {
        Double distance = java.lang.Math.sqrt(
                java.lang.Math.pow((intruderX - posX),2.0f)
                + java.lang.Math.pow((intruderY - posY),2.0f));

        if (  distance <= communicationRange )
            return distance.floatValue();
        return -1.0f;
    }

    public Boolean checkWithinCommunicationRange(Node testNode) {
        Double distance = getDistance(testNode.getPosX().floatValue(), testNode.getPosY().floatValue());
       // System.out.println(communicationRange);
        if ( distance < communicationRange ) {
            return true;
        }
        
        return false;
    }

    protected Double getDistance(Node nextNode) {
         Double distance = java.lang.Math.sqrt(
                java.lang.Math.pow((nextNode.getPosX() - posX),2.0f)
                + java.lang.Math.pow((nextNode.getPosY() - posY),2.0f));
         return distance;
    }

    protected Double getDistance(Float intruderX, Float intruderY)
    {
         Double distance = java.lang.Math.sqrt(
                java.lang.Math.pow((intruderX - posX),2.0f)
                + java.lang.Math.pow((intruderY - posY),2.0f));
         return distance;
    }

    public Boolean getFinalized() {
        return bFinalized;
    }

    public void setFinalized(Boolean bFinalized) {
        this.bFinalized = bFinalized;
    }

    public Float getCommunicationRange() {
        return communicationRange;
    }

    public void setCommunicationRange(Float commRange) {
        this.communicationRange = commRange;
    }

    public Boolean checkWithinSensingRange(Float intruderX, Float intruderY) {
        if (  getDistance(intruderX,intruderY) <= sensingRange )
            return true;
        return false;
    }

    public Float getSensingRange() {
        return sensingRange;
    }

    public void setSensingRange(Float sensingRange) {
        this.sensingRange = sensingRange;
    }

    public void print() {
        System.out.println("Node:\t[" + posX + ", " + posY + "]");
    }

    public void sendToBase() {
    }

    public Integer getShortestHopsToBase() {
        if ( headNode != null )
            return headNode.getShortestHopsToBase() + 1;
        
        return 0;
    }

    public void transmit(Node transmitTo) {
        if ( transmitTo != null ) {
            remainingJoules -= k*Eelec + eAmp * k * Math.pow(getDistance(transmitTo),2.0);
            transmitTo.recieve();
            //remainingJoules -= transmit.computeDeltaJ(this);
            if ( remainingJoules <= 0 ) {
                bAlive = false;
                remainingJoules = 0.0;
            }
        }
    }

    public void recieve() {
        if ( gsv == null )
            gsv = GlobalRuntimeVariables.getInstance();
        
        remainingJoules -= k * Eelec;
        transmit(gsv.getTransmitter().getNextNode(this));
        if ( remainingJoules <= 0 )
            bAlive = false;
    }
}
