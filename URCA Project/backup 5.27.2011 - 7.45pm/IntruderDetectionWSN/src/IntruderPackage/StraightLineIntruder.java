/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderPackage;

import IntruderDetectionWSN.GlobalRuntimeVariables;
import java.util.Random;

/**
 *
 * @author KuttaA
 */
public class StraightLineIntruder extends Intruder {
    @Override
    public void initialize() {
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
        locations = 0;
        Random numGen = new Random();

        // Start the intruder randomly outside of the board
        posX = -1.0f;
        //posY = numGen.nextFloat() * 1000;
        posY = numGen.nextFloat() * gsv.getBoardSize();

        dX = 1.0f;
    }

    @Override
    public void update() {
        posX += dX;
        if ( posX > 0 ) { // Only Increment as soon as he gets into the sensor range
            locations++;
        }
    }

    @Override
    public void reset() {
        initialize();
    }

    @Override
    public Double getDistanceTraveled() {
        return locations.doubleValue() * dX;
    }

    private float dX;
}
