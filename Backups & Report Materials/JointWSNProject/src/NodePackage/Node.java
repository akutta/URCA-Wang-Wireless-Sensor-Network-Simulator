/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package NodePackage;

import JointWSNProject.*;

/**
 *
 * @author KuttaA
 */
public class Node {

    protected Integer posX, posY;
    protected WirelessSensorNetwork wsn;
    
    // Don't know the implementation of these yet so just leaving blank for now
    public void reset() {
    }

    public void Initialize() {
    }

    public void Update() {
    }

    public Integer getPosX() {
        return posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosX(int xPos) {
        posX = xPos;
    }

    public void setPosY(int yPos) {
        posY = yPos;
    }
}
