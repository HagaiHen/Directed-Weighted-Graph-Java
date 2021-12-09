package Ex2.Graph_GUI;

import javax.swing.*;
import java.awt.*;


public class GraphGUI extends JFrame {

    private JPanel sideMenu;
    protected String file;
    protected GraphCanvas canvas;
    protected JTextField saveInput, WhichNode, src, dest;
    private JButton save, load, center, ShortestPath, tsp, Remove_Node, Add_Node;
    private ButtonListener bl;

    public GraphGUI(String file) {
        setTitle("Graph GUI");
        setSize(1000, 700);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bl = new ButtonListener(this);
        this.file = file;
        setComponents(file);
        setVisible(true);
    }

    public void setComponents(String file) {
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        sideMenu = new JPanel();
        sideMenu.setAlignmentX(LEFT_ALIGNMENT);
        sideMenu.setMaximumSize(new Dimension(0, 800));
        sideMenu.setLocation(0, 0);
        setSideMenu();
        add(sideMenu);

        canvas = new GraphCanvas(this, this.file);
        canvas.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(canvas);

    }

    private void setSideMenu() {
        //mb = new JMenuBar();


        save = new JButton("Save");
        save.setBounds(0, 0, 100, 20);
        save.addActionListener(bl);
        sideMenu.add(save);


        load = new JButton("Load");
        load.setBounds(0, 30, 100, 20);
        load.addActionListener(bl);
        sideMenu.add(load);

        //saveInput = new JTextField(10);
        //weightPanel.add(saveInput);
        //weightPanel.add(save);
        //saveInput.setBounds(0, 0, 100, 20);
        //sideMenu.add(saveInput);

        //weightPanel.add(load);

        center = new JButton("Center");
        center.setBounds(0, 60, 100, 20);
        center.addActionListener(bl);
        sideMenu.add(center);

        tsp = new JButton("TSP");
        tsp.addActionListener(bl);
        sideMenu.add(tsp);

        Remove_Node = new JButton("Remove Node");
        Remove_Node.setBounds(0, 60, 100, 50);
        Remove_Node.addActionListener(bl);
        sideMenu.add(Remove_Node);

        ShortestPath = new JButton("ShortestPath");
        ShortestPath.setBounds(0, 60, 100, 20);
        ShortestPath.addActionListener(bl);
        sideMenu.add(ShortestPath);

    }


}
