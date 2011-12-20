/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderPackage;

import IntruderDetectionWSN.GlobalRuntimeVariables;

/**
 *
 * @author akutta
 */
public class Intruder {
    protected Float posX, posY;
    protected Integer locations;
    protected Integer totLocations;

    public Float getPosX() {
        return posX;
    }

    public void setPosX(Float posX) {
        this.posX = posX;
    }

    public Float getPosY() {
        return posY;
    }

    public void setPosY(Float posY) {
        this.posY = posY;
    }

    //
    // This is for testing purposes, it is
    // meant entirely for experimental data as far as
    // seeing how many random points it took to test
    // before it was "caught"
    //
    public Double getDistanceTraveled() {
        return totLocations.doubleValue();
    }

    public Double getDistanceTraveledInField() {
        return locations.doubleValue();
    }

    public void initialize(Integer initX, Integer initY, Integer finalX, Integer finalY) {
        locations = 0;
        totLocations = 0;
    }

    //public void initialize() {
    //    locations = 0;
   // }

    public void update() {
    }

    public void reset() {
        locations = 0;
        totLocations = 0;
    }


    public Float getDX() {
        return dX;
    }

    public Float getDY() {
        return dY;
    }

    protected float dX;
    protected float dY;

    public Float getFinalX() {
        return GlobalRuntimeVariables.getInstance().getBoardWidth().floatValue();
    }
}
