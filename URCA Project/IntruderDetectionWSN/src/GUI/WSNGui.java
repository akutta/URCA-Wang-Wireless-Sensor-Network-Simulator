/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import javax.swing.*;

/**
 *
 * @author KuttaA
 */
public class WSNGui extends JPanel {
    
    public WSNGui() {
    }

    private static void createAndShowGUI() {
        WSNFrame frame = new WSNFrame();
        frame.setSize(356,321);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
