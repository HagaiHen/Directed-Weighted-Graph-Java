package Ex2;

import Ex2.api.EdgeData;
import Ex2.api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;

public class GUI extends JPanel implements ActionListener {
    private MyGraph graph;
    private int mar = 50;
    private JFrame frame;
    private HashMap<Integer, Point2D> coordinates;
    private static JButton btnSave;
    private static JButton btnLoad;

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        this.graph = new MyGraph("src/Ex2/data/G3.json");
        Graphics2D g1 = (Graphics2D) g;
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();

        double x_max = Double.MIN_VALUE, x_min = Double.MAX_VALUE,
                y_max = Double.MIN_VALUE, y_min = Double.MAX_VALUE;
        for (int i = 0; i < graph.nodeSize(); i++) {
            NodeData Point1 = this.graph.getNode(i);
            x_max = Math.max(Point1.getLocation().x(), x_max);
            x_min = Math.min(Point1.getLocation().x(), x_min);
            y_max = Math.max(Point1.getLocation().y(), y_max);
            y_min = Math.min(Point1.getLocation().y(), y_min);
        }
        double proportion_x = Math.abs(x_max - x_min), proportion_y = Math.abs(y_max - y_min);
        this.coordinates = new HashMap<>();
        Point2D p = new Point2D.Double(0, 0);

        double scaleX = (width - 100) / proportion_x;
        double scaleY = (height - 100) / proportion_y;

        for (int i = 0; i < this.graph.nodesSize - 1; i++) {
            System.out.println("plot");
            double x1 = 55 + (this.graph.getNode(i).getLocation().x() - x_min) * scaleX;
            System.out.println(x1);
            double y1 = 40 + (this.graph.getNode(i).getLocation().y() - y_min) * scaleY;
            System.out.println(y1);
            double x2 = 55 + (this.graph.getNode(i + 1).getLocation().x() - x_min) * scaleX;
            double y2 = 40 + (this.graph.getNode(i + 1).getLocation().y() - y_min) * scaleY;
            System.out.println(x2);
            System.out.println(y2);
            p = new Point2D.Double(x1, y1);
            coordinates.put(this.graph.getNode(i).getKey(), p);

        }
        double tmp1 = (this.graph.getNode(graph.nodesSize - 1).getLocation().x() - x_min) * scaleX;
        double tmp2 = (this.graph.getNode(graph.nodesSize - 1).getLocation().y() - y_min) * scaleY;
        g1.fill(new Ellipse2D.Double(tmp1, tmp2, 4, 4));
        p = new Point2D.Double(tmp1, tmp2);
        coordinates.put(this.graph.getNode(graph.nodesSize - 1).getKey(), p);
        Iterator<EdgeData> it = this.graph.edgeItr;
        EdgeData e = new Edge();
        while (it.hasNext()) {
            e = it.next();
            int src = e.getSrc(), dest = e.getDest();
            double x1 = this.coordinates.get(src).getX(),
                    y1 = this.coordinates.get(src).getY(),
                    x2 = this.coordinates.get(dest).getX(),
                    y2 = this.coordinates.get(dest).getY();
            drawArrowLine(g1, (int) x1 + 4, (int) y1 + 4, (int) x2 + 4, (int) y2 + 4, 8, 8);
        }

        Iterator<Point2D> iter = coordinates.values().iterator();

        while (iter.hasNext()) {
            g1.setColor(Color.BLUE);
            p = iter.next();
            g1.fill(new Ellipse2D.Double(p.getX(), p.getY(), 8, 8));
        }
        btnSave.addActionListener(this);
        btnLoad.addActionListener(this);

    }

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
        g.setColor(Color.BLACK);
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        g.setColor(Color.RED);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    public static void main(String[] args) {


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GUI());

        frame.setSize(700, 700);
        frame.setVisible(true);


        btnSave = new JButton("Save");
        btnSave.setBounds(0, 0, 100, 20);
        btnSave.setSize(100, 20);
        //btnSave.setVisible(true);

        frame.add(btnSave);

        btnLoad = new JButton("Load");
        btnLoad.setBounds(100, 0, 100, 20);
        btnLoad.setSize(100, 20);
        //btnLoad.setVisible(true);
        frame.add(btnLoad);
        frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == "Save") {
            MyGraphAlgo g = new MyGraphAlgo(this.graph);
            g.save("src/Ex2/data/json.json");
            System.out.println("Save");
        }
        if (e.getActionCommand() == "Load") {
            MyGraphAlgo g = new MyGraphAlgo(this.graph);
            System.out.println("Load");
            //g.load("");
        }
    }
}