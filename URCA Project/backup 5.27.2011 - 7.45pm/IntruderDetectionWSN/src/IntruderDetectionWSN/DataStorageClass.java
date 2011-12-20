/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IntruderDetectionWSN;

/**
 *
 * @author KuttaA
 */
public class DataStorageClass {
    // sRange,iteration,numAlive,numConnected,400
    private Integer numDataPoints;
    private Integer totalNumAlive;
    private Integer totalNumConnected;

    DataStorageClass() {
        numDataPoints       = 0;
        totalNumAlive       = 0;
        totalNumConnected   = 0;
    }

    public void setData(Integer numAlive, Integer numConnected) {
        this.totalNumAlive += numAlive;
        this.totalNumConnected += numConnected;
        this.numDataPoints += 1;
    }


    public Integer getAvgNumAlive() {
        if ( numDataPoints == 0 )
            return 0;
        return totalNumAlive / numDataPoints;
    }

    public Integer getAvgNumConnected() {
        if ( numDataPoints == 0 )
            return 0;
        return totalNumConnected / numDataPoints;
    }

}
