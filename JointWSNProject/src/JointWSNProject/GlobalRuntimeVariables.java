/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JointWSNProject;

/**
 *
 * @author KuttaA
 */
public class GlobalRuntimeVariables {
    private Integer                 boardSize   = 1000;
    private DistributionTypeEnum    globalDType = DistributionTypeEnum.RANDOM;

    private int                     K           = 2;
    private int                     numNodes    = 50;

    
    private static GlobalRuntimeVariables   instance = null;

    private GlobalRuntimeVariables() {
        // SINGLETON RAWR
    }

    // Singleton Design Pattern
    public static GlobalRuntimeVariables getInstance() {
        if ( instance == null ) {
            instance = new GlobalRuntimeVariables();
        }
        return instance;
    }

//
// Getter and Setters
//

    public int getNumNodes() {
        return numNodes;
    }

    public int getK() {
        return K;
    }

    public void setK(int K) {
        this.K = K;
    }

    public DistributionTypeEnum getGlobalDType() {
        return globalDType;
    }

    public void setGlobalDType(DistributionTypeEnum globalDType) {
        this.globalDType = globalDType;
    }

    public Integer getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(Integer boardSize) {
        this.boardSize = boardSize;
    }

}
