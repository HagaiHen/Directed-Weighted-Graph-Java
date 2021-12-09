package Ex2.Graph_GUI;

import Ex2.MyGraph;
import Ex2.MyGraphAlgo;
import Ex2.api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ButtonListener extends JPanel implements ActionListener {

    private GraphGUI gui;

    public ButtonListener(GraphGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyGraph g = this.gui.canvas.graphDrawing;
        MyGraphAlgo algo = new MyGraphAlgo(g);

        if (e.getActionCommand() == "Save") {

            //algo.save(this.gui.saveInput.getText());
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this.gui.saveInput) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // save to file
            }
            algo.save(fileChooser.getSelectedFile().getAbsolutePath());
        }
        if (e.getActionCommand() == "Load") {

            //algo.load(this.gui.saveInput.getText());
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // load from file
            }
            algo.load(fileChooser.getSelectedFile().getAbsolutePath());
            //gui.setComponents(fileChooser.getSelectedFile().getAbsolutePath());
            gui.canvas.reloadGraph(fileChooser.getSelectedFile().getAbsolutePath());
            //gui.canvas.graphDrawing = fileChooser.getSelectedFile().getAbsolutePath();
            //gui = new GraphGUI(fileChooser.getSelectedFile().getAbsolutePath());
            //gui.canvas = new GraphCanvas(gui.canvas.getFrame(), fileChooser.getSelectedFile().getAbsolutePath());
        }
        if (e.getActionCommand() == "Center") {
            int indx = algo.center().getKey();
            gui.canvas.paintNode(indx);
        }

        if (e.getActionCommand() == "Remove Node") {
            String chosen = this.gui.WhichNode.getText();
            int num = Integer.valueOf(chosen);
            //MyGraph n = this.gui.canvas.graphDrawing.removeNode(num);
//            gui.canvas.reloadGraph();
            this.gui.canvas.graphDrawing.removeNode(num);
            gui.canvas.rePaint();
        }

        if (e.getActionCommand() == "ShortestPath") {
            algo = new MyGraphAlgo(this.gui.canvas.graphDrawing);
            List<NodeData> ans = new ArrayList<>();
            int src = Integer.parseInt(gui.src.getText());
            int dest = Integer.parseInt(gui.dest.getText());
            ans = algo.shortestPath(src, dest);
            for (NodeData n : ans) {
                gui.canvas.paintNode(n.getKey());
                System.out.println(n.getKey());
            }
            for (int i = 0; i < ans.size()-1; i++) {
                gui.canvas.paintEdge(ans.get(i).getKey(), ans.get(i+1).getKey());
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.gui.canvas.paintComponent(g);
    }
}
