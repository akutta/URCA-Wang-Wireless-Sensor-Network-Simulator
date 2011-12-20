/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderPackage;

/**
 *
 * @author akutta
 */
public class Intruder {
    protected Float posX, posY;
    protected Integer locations;

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
        return locations.doubleValue();
    }

    public void initialize() {
        locations = 0;
    }

    public void update() {
    }

    public void reset() {
        locations = 0;
    }
}
