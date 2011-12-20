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
public class WirelessSensorNode extends Node {
    @Override
    public Double getShortestDistanceToBase() {
        return distanceToBase;
    }

    @Override
    public void Initialize() {
        bFinalized = false;
        distanceToHead = -1.0;
        distanceToBase = -1.0;
        communicationRange = -1.0f;

        remainingJoules = GlobalRuntimeVariables.getInstance().getInitialJoules();
    }

    @Override
    public Boolean checkWithinSensingRange(Float intruderX, Float intruderY) {
        Double distance = getDistance(intruderX,intruderY);
        if ( distance  <= sensingRange )
            return true;
        return false;
    }

    @Override
    public Float getSensingRange() {
        return sensingRange;
    }

    @Override
    public void setSensingRange(Float sensingRange) {
        this.sensingRange = sensingRange;
    }

    public void setWSN(WirelessSensorNetwork wsn) {
        this.wsn = wsn;
    }

}
