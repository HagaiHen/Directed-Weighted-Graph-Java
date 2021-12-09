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

        if (e.getActionCommand() == "TSP") {
            boolean check = false;
            List<NodeData> TspList = new ArrayList <NodeData>();
            do{
                String input = JOptionPane.showInputDialog("Enter Node:");
                TspList.add(this.gui.canvas.graphDrawing.getNode(Integer.valueOf(input)));
                int n = JOptionPane.showConfirmDialog(
                        null,
                        "Enter Node:",
                        "Enter Node:",
                        JOptionPane.YES_NO_OPTION);
                check = false;
                if(n == JOptionPane.YES_OPTION)
                {
                    check = true;
                }
            } while (check == true);
            MyGraphAlgo algo1 = new MyGraphAlgo(this.gui.canvas.graphDrawing);
            List<NodeData> res = algo1.tsp(TspList);
            int [] print = new int[res.size()];
            String result ="";
            for (int i = 0; i < res.size(); i++) {
                print [i] = res.get(i).getKey();
                if(i==0){
                    result = result + String.valueOf(res.get(i).getKey());
                }
                else
                    result = result + "->"+ String.valueOf(res.get(i).getKey());
            }

            JOptionPane.showMessageDialog(null, "The final TSP path is " + result, "TSP Path", JOptionPane.PLAIN_MESSAGE);


//            this.gui.canvas.graphDrawing.tsp(TspList);
//            algo1.canvas.rePaint();
//            this.gui.canvas.rePaint();
        }

        if (e.getActionCommand() == "Remove Node") {
            String input = JOptionPane.showInputDialog("Enter Input:");
            int num = Integer.valueOf(input);
            //MyGraph n = this.gui.canvas.graphDrawing.removeNode(num);
//            gui.canvas.reloadGraph();
            this.gui.canvas.graphDrawing.removeNode(num);
            gui.canvas.rePaint();
        }

        if (e.getActionCommand() == "ShortestPath") {
            String srcTextBox = JOptionPane.showInputDialog("source:");
            String destTextBox = JOptionPane.showInputDialog("dest:");
            algo = new MyGraphAlgo(this.gui.canvas.graphDrawing);
            List<NodeData> ans = new ArrayList<>();
            int src = Integer.valueOf(srcTextBox);
            int dest = Integer.valueOf(destTextBox);
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
