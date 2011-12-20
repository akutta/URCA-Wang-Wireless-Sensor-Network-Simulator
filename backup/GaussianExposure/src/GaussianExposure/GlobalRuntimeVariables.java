/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GaussianExposure;

import EnumerationDefinitions.*;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author akutta
 */
public class GlobalRuntimeVariables {
    private Vector<Node> nodes;
    private Double BoardRadius = 100.0;
    private Double sigma = 0.0;

    private Integer n = 32;
    private Integer m = 4;
    private Integer maxNodes = 400;
    private int EvaluateDivisions = 10;
    public Integer subCount = 10;

    private Random numGen = null;

    //
    // If the type is Gaussian, then the program will generate on it's own
    //
    private DistributionTypeEnum typeDist = DistributionTypeEnum.GAUSSIAN;


    //
    //  BEGIN Singleton Implementation
    //
    private static GlobalRuntimeVariables   instance = null;
    private GlobalRuntimeVariables() {
        // SINGLETON RAWR
        numGen = new Random();
    }

    // Singleton Design Pattern
    public static GlobalRuntimeVariables getInstance() {
        if ( instance == null ) {
            instance = new GlobalRuntimeVariables();
        }
        return instance;
    }
    //
    // END Singleton Implementation
    //

    public Integer getNextInteger(Integer max) {
        return numGen.nextInt(max);
    }

    public Double getNextDouble() {
        return numGen.nextDouble();
    }

    public void setSigma(Double newSigma) {
        sigma = newSigma;
    }

    public Double getSigma() {
        return sigma;
    }

    public Vector<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Vector<Node> nodes) {
        this.nodes = nodes;
    }

    public Double getRadius() {
        return BoardRadius;
    }

    public void setRadius(Double radius) {
        BoardRadius = radius;
    }

    public DistributionTypeEnum getTypeDist() {
        return typeDist;
    }

    public Integer getM() {
        return m;
    }

    public Integer getN() {
        return n;
    }

    public Integer getMaxNodes() {
        return maxNodes;
    }

    public int getEvaluateDivisions() {
        return EvaluateDivisions;
    }
}
