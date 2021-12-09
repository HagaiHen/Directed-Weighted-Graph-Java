package Ex2.Graph_GUI;

import Ex2.Edge;
import Ex2.MyGraph;
import Ex2.Node;
import Ex2.api.EdgeData;
import Ex2.api.NodeData;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;

//this class has some function that help us to show the graph
public class GraphCanvas extends JPanel {

    private String file;
    private GraphGUI frame;
    protected MyGraph graphDrawing;
    private HashMap<Integer, Point2D> coordinates;

    public GraphGUI getFrame() {
        return frame;
    }

    public GraphCanvas(GraphGUI frame, String file) {
        this.file = file;
        this.frame = frame;
        graphDrawing = new MyGraph(file);
    }


    public void reloadGraph(String file) {
        graphDrawing = new MyGraph(file);
        paintComponent(this.getGraphics());
    }

    //call for repaint the canvas
    public void rePaint() {
        paintComponent(this.getGraphics());
    }

    //help us to paint a node for the user interface
    public void paintNode(int key) {
        Graphics2D g1 = (Graphics2D) this.getGraphics();
        double x = this.coordinates.get(key).getX();
        double y = this.coordinates.get(key).getY();
        g1.setColor(Color.black);
        g1.fill(new Ellipse2D.Double(x, y, 8, 8));
    }

    //help us to paint an edge for the user interface
    public void paintEdge(int src, int dest) {
        Graphics2D g1 = (Graphics2D) this.getGraphics();
        g1.setColor(Color.black);
        double x1 = this.coordinates.get(src).getX();
        double y1 = this.coordinates.get(src).getY();
        double x2 = this.coordinates.get(dest).getX();
        double y2 = this.coordinates.get(dest).getY();
        drawArrowLine(g1, x1 + 5, y1 + 5, x2 + 5, y2 + 5, 10, 10);
    }

    //run the whole graph graphics
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D) g;
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();

        double x_max = Double.MIN_VALUE, x_min = Double.MAX_VALUE,
                y_max = Double.MIN_VALUE, y_min = Double.MAX_VALUE;     //help us to understand the proportion of the graph
        Iterator<NodeData> it = this.graphDrawing.nodeIter();
        NodeData n = new Node();
        while (it.hasNext()) {
            n = it.next();
            NodeData Point1 = this.graphDrawing.getNode(n.getKey());
            x_max = Math.max(Point1.getLocation().x(), x_max);
            x_min = Math.min(Point1.getLocation().x(), x_min);
            y_max = Math.max(Point1.getLocation().y(), y_max);
            y_min = Math.min(Point1.getLocation().y(), y_min);
        }
        double proportion_x = Math.abs(x_max - x_min),
                proportion_y = Math.abs(y_max - y_min);

        this.coordinates = new HashMap<>();
        Point2D p = new Point2D.Double(0, 0);

        double scaleX = (width - 100) / proportion_x;           //scale for proportion
        double scaleY = (height - 100) / proportion_y;
        it = this.graphDrawing.nodeIter();
        n = new Node();
        while (it.hasNext()) {                                  // run all over the nodes with iterator
            n = it.next();                                      // and draw them in the panel
            double x1 = 55 + (this.graphDrawing.getNode(n.getKey()).getLocation().x() - x_min) * scaleX;
            double y1 = 40 + (this.graphDrawing.getNode(n.getKey()).getLocation().y() - y_min) * scaleY;
            p = new Point2D.Double(x1, y1);
            coordinates.put(this.graphDrawing.getNode(n.getKey()).getKey(), p);
        }

        Iterator<EdgeData> iter = this.graphDrawing.edgeIter();
        EdgeData e = new Edge();
        while (iter.hasNext()) {                                //run all over the edges with iterator
            e = iter.next();                                    // and draw them in the panel
            if (this.coordinates.get(e.getSrc()) != null && this.coordinates.get(e.getDest()) != null) {
                int src = e.getSrc(), dest = e.getDest();
                double x1 = this.coordinates.get(src).getX(),
                        y1 = this.coordinates.get(src).getY(),
                        x2 = this.coordinates.get(dest).getX(),
                        y2 = this.coordinates.get(dest).getY();
                drawArrowLine(g1, (int) x1 + 4, (int) y1 + 4, (int) x2 + 4, (int) y2 + 4, 8, 8);
            }
        }

        Iterator<Point2D> ptr = coordinates.values().iterator();
        while (ptr.hasNext()) {                                 // draw the whole points
            g1.setColor(Color.BLUE);
            p = ptr.next();
            g1.fill(new Ellipse2D.Double(p.getX(), p.getY(), 8, 8));
        }
    }


    //draw an arrow line for show what is the direct of the edge
    private void drawArrowLine(Graphics g, double x1, double y1, double x2, double y2, double d, double h) {
        double dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;
        xm = Math.round(xm);
        x2 = Math.round(x2);
        xn = Math.round(xn);
        ym = Math.round(ym);
        y2 = Math.round(y2);
        yn = Math.round(yn);

        int[] xpoints = {(int) x2, (int) xm, (int) xn};
        int[] ypoints = {(int) y2, (int) ym, (int) yn};
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        g.setColor(Color.RED);
        g.fillPolygon(xpoints, ypoints, 3);
    }
}