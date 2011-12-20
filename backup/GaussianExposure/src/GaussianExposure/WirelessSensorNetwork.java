/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GaussianExposure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author akutta
 */
public class WirelessSensorNetwork {

    private GlobalRuntimeVariables gsv;

    public WirelessSensorNetwork() {
        gsv = GlobalRuntimeVariables.getInstance();
    }

    //
    // fileName input must not include a file extension
    // and file format must be .csv
    //
    // Set filename to increment with first number starting at 0 and then having a number appended
    //
    public void readPointsFromFile(String fileName) {
        Vector<Node> nodes = new Vector<Node>();
        Integer index = -1;

        if ( (index = fileName.indexOf(".")) > -1) {
            if ( fileName.contains(".csv") ) {
                fileName = fileName.substring(0, index);
            } else {
                System.out.println("ERROR:  Unknown Input File Type");
                System.exit(0);
            }
        }

        System.out.println("Reading:\t" + fileName);
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName + ".csv"));
            try {
                String line = null;

                // First line defines Radius
                if ( (line = in.readLine()) != null ) {
                    gsv.setRadius(Double.parseDouble(line));
                } else {
                    in.close();
                    System.exit(0);
                }

                // Second line defines sigma
                if ( ( line = in.readLine()) != null ) {
                    gsv.setSigma(Double.parseDouble(line));
                } else {
                    in.close();
                    System.exit(0);
                }

                // Rest of lines contain a single
                // data point that is the radius of the current node
                while (( line = in.readLine()) != null ) {
                    Double randAngle = 180.0 * gsv.getNextDouble();
                    Double radius = Double.parseDouble(line);
                    Double x = radius * Math.cos(randAngle) + gsv.getRadius() + 1;
                    Double y = radius * Math.sin(randAngle) + gsv.getRadius() + 1;
                    
                    Node newNode = new Node();
                    newNode.setPos(x,y);
                    nodes.add(newNode);
                }
            }
            finally {
                in.close();
            }
        } catch (IOException ex) {
        }
        
        gsv.setNodes(nodes);
    }
}
