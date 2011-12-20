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
public class RandomIntruder extends Intruder {
    @Override
    public void update() {
        GlobalRuntimeVariables gsv = GlobalRuntimeVariables.getInstance();
        Random numGen = new Random();

//        posX = numGen.nextFloat() * 1000;
//        posY = numGen.nextFloat() * 1000;
        //posX = numGen.nextFloat() * gsv.getBoardSize();
        //posY = numGen.nextFloat() * gsv.getBoardSize();
        posX = numGen.nextFloat() * gsv.getBoardWidth();
        posY = numGen.nextFloat() * gsv.getBoardHeight();


        locations++;
        totLocations++;
    }
}
