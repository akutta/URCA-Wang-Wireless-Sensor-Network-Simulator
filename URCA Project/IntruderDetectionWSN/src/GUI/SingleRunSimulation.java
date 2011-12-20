/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SingleRunSimulation.java
 *
 * Created on May 28, 2011, 1:15:26 AM
 */

package GUI;

import EnumDefinitionPackage.*;
import IntruderDetectionWSN.*;
import IntruderPackage.Intruder;
import NodePackage.Node;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author KuttaA
 */
public class SingleRunSimulation extends javax.swing.JFrame
    implements Runnable
{
    private final Integer boardHeight, boardWidth;

    private final Float[] ellipses;
    private final double[] esize;
    private final float[] estroke;
    private Thread thread;
    private BufferedImage bimg;

    private Node[] nodes;
    private final WirelessSensorNetwork wsn;
    private final java.lang.Float dX, dY;

    class Line {
        public java.lang.Float initPosX, initPosY, finPosX, finPosY;
    };

    // For drawing connected nodes
    private Line[] lines;
    private Integer maxLines;

    // For drawing intruder
    private Intruder intruder;
    private java.lang.Float intruderPosX;
    private java.lang.Float intruderPosY;
    private Double distanceTraveled;
    private Integer iter;

    private Double scaleWidth, scaleHeight;

    /** Creates new form SingleRunSimulation */
    public SingleRunSimulation(
            java.lang.Float sRange,
            java.lang.Float cRange,
            DistributionTypeEnum dType,
            SensingTypeEnum sType,
            IntruderTypeEnum iType,
            EnergyComputationEnum eType,
            Integer K,
            Integer boardWidth,
            Integer boardHeight,
            Integer numNodes,
            Integer basePosX,
            Integer initPosX, Integer initPosY,
            Integer finalPosX, Integer finalPosY
            )
    {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;

        scaleWidth = 500.0 / boardWidth.doubleValue();
        scaleHeight = 500.0 / boardHeight.doubleValue();

        GlobalRuntimeVariables.getInstance().setBoardSize(boardWidth,boardHeight);
        GlobalRuntimeVariables.getInstance().setK(K);
        maxLines = 0;
        iter = 0;
        initComponents();
        //setBackground(Color.black);

        wsn = new WirelessSensorNetwork();
        wsn.setRunParameters(sRange, cRange, dType, sType, iType, eType,
                numNodes,basePosX,
                initPosX, initPosY,
                finalPosX, finalPosY);

        intruder = wsn.getIntruder();
        intruderPosX = intruder.getPosX();
        intruderPosY = intruder.getPosY();

        do {
            nodes = wsn.getNodes();
        } while ( nodes == null );

        lines = new Line[nodes.length];
        ellipses = new Ellipse2D.Float[nodes.length];
        esize = new double[ellipses.length];
        estroke = new float[ellipses.length];

        // Figure out how far we ended up going!!!
        while ( !wsn.finishedRun() )
        {
            // calls intruder update internally
            // this allows to continue threaded operation
            if ( !wsn.isUpdating() )    // Basic Mutex
                wsn.updateDetection();
        }

        nodes = null;
        // update nodes
        do {
            nodes = wsn.getNodes();
        } while ( nodes == null );

        for (int i = 0; i < ellipses.length; i++) {
            ellipses[i] = new Ellipse2D.Float();
            esize[i] = 5.0;
            estroke[i] = 1.0f;

            ellipses[i].setFrame((nodes[i].getPosX() * scaleWidth) + 50 - 5, (nodes[i].getPosY() * scaleHeight) + 50 - 5,10.0,10.0);
            //ellipses[i].setFrame((nodes[i].getPosX() / 2) + 50 - 5, (nodes[i].getPosY() / 2) + 50 - 5,10.0,10.0);

            if ( nodes[i].getHeadNode() != null ) {
                lines[maxLines] = new Line();

                lines[maxLines].initPosX = nodes[i].getPosX().floatValue() * scaleWidth.floatValue() + 50;
                lines[maxLines].initPosY = nodes[i].getPosY().floatValue() * scaleHeight.floatValue() + 50;

                lines[maxLines].finPosX = nodes[i].getHeadNode().getPosX().floatValue() * scaleWidth.floatValue() + 50;
                lines[maxLines].finPosY = nodes[i].getHeadNode().getPosY().floatValue() * scaleHeight.floatValue() + 50;
                /*
                lines[maxLines].initPosX = nodes[i].getPosX().floatValue() / 2 + 50;
                lines[maxLines].initPosY = nodes[i].getPosY().floatValue() / 2 + 50;

                lines[maxLines].finPosX = nodes[i].getHeadNode().getPosX().floatValue() / 2 + 50;
                lines[maxLines].finPosY = nodes[i].getHeadNode().getPosY().floatValue() / 2 + 50;*/
                maxLines++;
            }
        }

        // Enlarge the Base Station
        ellipses[0].height += 10;
        ellipses[0].width += 10;
        ellipses[0].x -= 5;
        ellipses[0].y -= 5;

        dX = intruder.getDX();
        dY = intruder.getDY();
        distanceTraveled = intruder.getDistanceTraveled();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables



    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }


    public synchronized void stop() {
        thread = null;
    }

    public void drawDemo(int w, int h, Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        //
        // Compute Current Intruder Position
        //
        java.lang.Float posX = (intruderPosX + (iter)*dX)*scaleWidth.floatValue() + 49;
        java.lang.Float posY = (intruderPosY + (iter)*dY)*scaleHeight.floatValue() + 50;
        /*java.lang.Float posX = (intruderPosX + (iter)*dX)/2 + 49;
        java.lang.Float posY = (intruderPosY + (iter)*dY)/2 + 50;*/

        //if ( iter < 100 ) {
        //    posX = (intruderPosX + iter)/2;
        //    posY = (intruderPosY)/2 + 50;
        //}

        if ( iter > distanceTraveled) {
            posX = 50 + (intruderPosX + distanceTraveled.floatValue() * dX)*scaleWidth.floatValue();
            posY = 50 + (intruderPosY + distanceTraveled.floatValue() * dY)*scaleHeight.floatValue();
            //posX = 50 + (intruderPosX + distanceTraveled.floatValue() * dX)/2.0f;
            //posY = 50 + (intruderPosY + distanceTraveled.floatValue() * dY)/2.0f;
        }

        //
        // Draw Frame
        //
        g2.setColor(Color.black);
        g2.draw(new Line2D.Double(49,49,49,551));
        g2.draw(new Line2D.Double(49,49,551,49));
        g2.draw(new Line2D.Double(551,49,551,551));
        g2.draw(new Line2D.Double(49,551,551,551));

        //
        // Draw Connecting Lines
        //
        g2.setStroke(new BasicStroke(2.0f));
        for (int i = 0; i < ellipses.length; i++) {
            if ( i < maxLines ) {
                g2.setColor(Color.black);
                g2.draw(new Line2D.Float(lines[i].initPosX, lines[i].initPosY, lines[i].finPosX, lines[i].finPosY));
            }
        }

        //
        // Draw the Sensor Nodes
        //
        for (int i = 0; i < ellipses.length; i++) {
            if ( i == 0 ) {
                // Draw later
               //g2.setColor(Color.GREEN);
                //g2.fill(ellipses[i]);
            }  else {
                if (nodes[i].getHeadNode() != null) {
                    if ( nodes[i].checkWithinSensingRange((posX-50)*2, (posY-50)*2) ) {
                        g2.setColor(Color.BLUE);
                    }  else
                        g2.setColor(Color.GREEN);

                    g2.fill(ellipses[i]);
                } else {
                    //g2.setColor(Color.red);
                    //g2.fill(ellipses[i]);
                }

                g2.setColor(Color.BLACK);
                g2.draw(ellipses[i]);
            }
        }

        g2.setColor(Color.BLACK);
        g2.fill(ellipses[0]);

        //
        // Draw the Intruder
        //
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(5.0f));
        g2.draw(new Line2D.Float(intruderPosX.floatValue()*scaleWidth.floatValue() + 51, intruderPosY.floatValue()*scaleHeight.floatValue() + 50,
            posX, posY));
        //g2.draw(new Line2D.Float(intruderPosX.floatValue()/2 + 51, intruderPosY.floatValue()/2 + 50,
            //posX, posY));
    }

    @Override
    public void paint(Graphics g) {
        //setBackground(Color.black);
        Dimension d = getSize();
        Graphics2D g2 = createGraphics2D(d.width, d.height);
        drawDemo(d.width, d.height, g2);
        g2.dispose();
        g.drawImage(bimg, 0, 0, this);
        iter++;
    }


    public void run() {
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) { break; }
        }
        thread = null;
    }

    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D g2 = null;
        if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
            bimg = (BufferedImage) createImage(w, h);
        }
        g2 = bimg.createGraphics();
        g2.setBackground(getBackground());
        g2.clearRect(0, 0, w, h);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        return g2;
    }
}
