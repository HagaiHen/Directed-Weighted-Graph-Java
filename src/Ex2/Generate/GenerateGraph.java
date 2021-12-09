package Ex2.Generate;

import Ex2.Edge;
import Ex2.Node;
import Ex2.api.DirectedWeightedGraph;
import Ex2.api.EdgeData;
import Ex2.api.NodeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.Random;

public class GenerateGraph {
    GenerateEdge[] Edges;
    GenerateNode[] Nodes;

    public GenerateGraph(int EdgeSize, int NodeSize) {
        this.Edges = new GenerateEdge[EdgeSize];
        this.Nodes = new GenerateNode[NodeSize];
        int i = 0, j = 0;
        String pos = "";
        Random r = new Random();
        while (i < NodeSize) {
            pos = (35 + r.nextDouble()) + "," + (32 + r.nextDouble()) + "," + "0.0";
            this.Nodes[i] = new GenerateNode(pos, i);
            i++;
        }
        while (j < EdgeSize) {
            this.Edges[j] = new GenerateEdge(r.nextInt(NodeSize), r.nextDouble(), r.nextInt(NodeSize));
            j++;
        }
    }

    public GenerateGraph(DirectedWeightedGraph g) {
        this.Edges = new GenerateEdge[g.edgeSize()];
        this.Nodes = new GenerateNode[g.nodeSize()];
        String pos = "";
        int i = 0;
        Iterator<NodeData> itN = g.nodeIter();
        Iterator<EdgeData> itE = g.edgeIter();
        NodeData n = new Node();
        EdgeData e = new Edge();
        while (itN.hasNext()) {
            n = itN.next();
            pos = (n.getLocation().x()) + "," + (n.getLocation().y()) + "," + n.getLocation().z();
            this.Nodes[i] = new GenerateNode(pos, n.getKey());
            i++;
        }
        i = 0;
        while (itE.hasNext()) {
            e = itE.next();
            this.Edges[i] = new GenerateEdge(e.getSrc(), e.getWeight(), e.getDest());
            i++;
        }
    }

    public boolean GenerateJson(GenerateGraph g,  String file_name) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter w = new FileWriter(file_name);
            gson.toJson(g, w);
            w.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
