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



//this class implements the whole actions
public class ButtonListener extends JPanel implements ActionListener {

    private GraphGUI gui;

    public ButtonListener(GraphGUI gui) {
        this.gui = gui;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        MyGraph g = this.gui.canvas.graphDrawing;
        MyGraphAlgo algo = new MyGraphAlgo(g);

        //Save the final graph and export it to json
        if (e.getActionCommand() == "Save") {

            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this.gui.saveInput) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
            }
            algo.save(fileChooser.getSelectedFile().getAbsolutePath());
        }

        //import a json file and expose the graph
        if (e.getActionCommand() == "Load") {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

            }
            algo.load(fileChooser.getSelectedFile().getAbsolutePath());
            gui.canvas.reloadGraph(fileChooser.getSelectedFile().getAbsolutePath());
        }

        //call the center function
        if (e.getActionCommand() == "Center") {
            int indx = algo.center().getKey();
            gui.canvas.paintNode(indx);
        }

        //gets a number of nodes and return the solution for TSP
        if (e.getActionCommand() == "TSP") {
            boolean check = false;                                  //while helper
            List<NodeData> TspList = new ArrayList <NodeData>();    //add the nodes input from the user
            do{
                String input = JOptionPane.showInputDialog("Enter Node:");  //get a node from the user
                TspList.add(this.gui.canvas.graphDrawing.getNode(Integer.valueOf(input))); // add the input to the final result
                int n = JOptionPane.showConfirmDialog(          //yes or not dialog
                        null,
                        "Enter Node:",
                        "Enter Node:",
                        JOptionPane.YES_NO_OPTION);
                check = false;
                if(n == JOptionPane.YES_OPTION)                 // if the user chose yes
                {
                    check = true;
                }
            } while (check == true);
            MyGraphAlgo algo1 = new MyGraphAlgo(this.gui.canvas.graphDrawing);
            List<NodeData> res = algo1.tsp(TspList);
            String result ="";
            for (int i = 0; i < res.size(); i++) {              // run all over the list that we get from TSP function
                if(i==0){
                    result = result + String.valueOf(res.get(i).getKey());
                }
                else
                    result = result + "->"+ String.valueOf(res.get(i).getKey());
            }

            JOptionPane.showMessageDialog(null, "The final TSP path is " + result, "TSP Path", JOptionPane.PLAIN_MESSAGE);
        }

        //call for remove node function
        if (e.getActionCommand() == "Remove Node") {
            String input = JOptionPane.showInputDialog("Enter Input:");
            int num = Integer.valueOf(input);
            this.gui.canvas.graphDrawing.removeNode(num);
            gui.canvas.rePaint();
        }

        //call for shortestpath function
        if (e.getActionCommand() == "ShortestPath") {
            String srcTextBox = JOptionPane.showInputDialog("source:");     //gets two variable
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
