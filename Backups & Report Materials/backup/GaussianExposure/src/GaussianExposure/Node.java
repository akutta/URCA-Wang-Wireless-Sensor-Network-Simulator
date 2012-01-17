/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GaussianExposure;

/**
 *
 * @author akutta
 */
public class Node {
    private Double posX, posY;

    public void setPos(Double posX, Double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
    }

    public double[] getPosition() {
        double[] retVal = { posX, posY };
        return retVal;
    }
}
