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
    public Float getFinalX()
    {
        if ( finalX != -1 )
            return finalX;
        else
            return GlobalRuntimeVariables.getInstance().getBoardWidth().floatValue();
    }

    @Override
    public void initialize(Integer initX, Integer initY, Integer finalX, Integer finalY) {
        // Store for later use
        this.initX = initX.floatValue();
        this.initY = initY.floatValue();
        this.finalX = finalX.floatValue();
        this.finalY = finalY.floatValue();

        // Initialize Variables
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
        locations = 0;
        totLocations = 0;
        Random numGen = new Random();

        Float X0 = initX.floatValue();
        Float X1 = finalX.floatValue();
        Float Y0 = initY.floatValue();
        Float Y1 = finalY.floatValue();

        // Figure out the initial and final positions here
        if ( Y0 == -1 ) { Y0 = numGen.nextFloat() * gsv.getBoardHeight(); }
        if ( X1 == -1 ) X1 = gsv.getBoardHeight().floatValue();
        if ( Y1 == -1 ) Y1 = Y0;

        //        Float slope = (Y1 - Y0) / (X1 - X0);
        dX = 1.0f;

        if ( X1 - X0 != 0 ) {
            dY = ( (Y1 - Y0 ) / (X1 - X0) );
        }

       posX = X0;
       posY = Y0;

        // Start the intruder randomly outside of the board
        //posY = numGen.nextFloat() * gsv.getBoardSize();

    }

    @Override
    public void update() {
        posX += dX;
        posY += dY;

        /*
        if ( finalX != -1 ) {
            if ( posX > finalX ) {
                // just in case, should automatically detect this
            } else {
                totLocations++;

                if ( posX > 0 ) { // Only Increment as soon as he gets into the sensor range
                    locations++;
                }
            }
        } else {
            totLocations++;

            if ( posX > 0 ) { // Only Increment as soon as he gets into the sensor range
                locations++;
            }
        }*/

        totLocations++;

        if ( posX > 0 ) { // Only Increment as soon as he gets into the sensor range
            locations++;
        }
    }

    @Override
    public void reset() {
        initialize(initX.intValue(),initY.intValue(),finalX.intValue(),finalY.intValue());
    }

    @Override
    public Double getDistanceTraveled() {
        return totLocations.doubleValue();// * dX + totLocations.doubleValue() * dY;
    }

    @Override
    public Double getDistanceTraveledInField() {
        return locations.doubleValue() * dX + locations.doubleValue() * dY;
    }

    private Float initX, finalX;
    private Float initY, finalY;
}
