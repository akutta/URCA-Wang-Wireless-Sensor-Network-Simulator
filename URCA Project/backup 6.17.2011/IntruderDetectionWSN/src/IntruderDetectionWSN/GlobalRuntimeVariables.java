/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN;

import EnergyComputations.*;
import EnumDefinitionPackage.*;

/**
 *
 * @author akutta
 */
public class GlobalRuntimeVariables {
    private Integer                 kCoverage = 3;
    private Integer                 maxIterationsPerSensingRange = 1000;
    private Integer                 boardSize   = 1000;
    
    private DistributionTypeEnum    globalDType = DistributionTypeEnum.RANDOM;
    private IntruderTypeEnum        globalIType = IntruderTypeEnum.STRAIGHT_LINE;
    private SensingTypeEnum         globalSType = SensingTypeEnum.ALL;

    
    // Do not change these variables if doing intruder detection
    // Only used for Energy Computations and telling it that it's energy computations
    private EnergyComputationEnum   globalEType = EnergyComputationEnum.DIJKSTRA;
    private RunTypeEnum             globalRType = RunTypeEnum.INTRUDER_DETECTION;  // RunTypeEnum.ENERGY_DISSIPATION;
    private Double                  initialJoules = 0.5;
    private Integer                 globalIterations = 1500;

    private TransmissionMethod transmitter;

    static GlobalRuntimeVariables   instance = null;

    private GlobalRuntimeVariables() {
        // SINGLETON RAWR
    }
    
    public static GlobalRuntimeVariables getInstance() {
        if ( instance == null ) {
            instance = new GlobalRuntimeVariables();
        }
        return instance;
    }

    public RunTypeEnum getRunType() {
        return globalRType;
    }

    public void setRunType(RunTypeEnum globalRType) {
        this.globalRType = globalRType;
    }

    public Double getInitialJoules() {
        return initialJoules;
    }

    public void setInitialJoules(Double initialJoules) {
        this.initialJoules = initialJoules;
    }

    public EnergyComputationEnum getEnergyType() {
        return globalEType;
    }

    public Integer getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(Integer boardSize) {
        this.boardSize = boardSize;
    }

    public DistributionTypeEnum getGlobalDType() {
        return globalDType;
    }

    public void setGlobalDType(DistributionTypeEnum globalDType) {
        this.globalDType = globalDType;
    }

    public IntruderTypeEnum getGlobalIType() {
        return globalIType;
    }

    public void setGlobalIType(IntruderTypeEnum globalIType) {
        this.globalIType = globalIType;
    }

    public SensingTypeEnum getGlobalSType() {
        return globalSType;
    }

    public void setGlobalSType(SensingTypeEnum globalSType) {
        this.globalSType = globalSType;
    }

    public Integer getMaxIterations() {
        return maxIterationsPerSensingRange;
    }

    public void setMaxIterations(Integer maxIterationsPerSensingRange) {
        this.maxIterationsPerSensingRange = maxIterationsPerSensingRange;
    }

    public Integer getK() {
        return kCoverage;
    }

    public void setK(Integer K) {
        kCoverage = K;
    }

    public TransmissionMethod getTransmitter() {
        if ( transmitter == null )
            initializeTransmitter();

        return transmitter;
    }

    public void initializeTransmitter() {

        if ( globalEType == EnergyComputationEnum.DIJKSTRA ) {
            transmitter = new ShortestPathToBase();
        } else if ( globalEType == EnergyComputationEnum.LEACH ) {
            transmitter = new LEACH();
        } else if ( globalEType == EnergyComputationEnum.MTE ) {
            transmitter = new MTE();
        } else {
            System.out.println("Unrecognized Energy Computation Type\n\tDefaulting to Djikstra");
            transmitter = new ShortestPathToBase();
        }
    }

    public Integer getIterations() {
        return globalIterations;
    }
}
