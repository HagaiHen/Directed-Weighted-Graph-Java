package Ex2;

import Ex2.api.DirectedWeightedGraph;
import Ex2.api.EdgeData;
import Ex2.api.NodeData;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 This class implements the interface DirectedWeightedGraph which represent
 the whole variable of graph and more
 */
public class MyGraph implements DirectedWeightedGraph {


    int nodesSize, edgesSize, MC;
    HashMap<String, EdgeData> edges;
    HashMap<Integer, NodeData> nodes;
    Iterator<EdgeData> edgeItr;
    Iterator<NodeData> nodeItr;
    ArrayList<EdgeData>[] OutEdge;
    ArrayList<EdgeData>[] InEdge;

    //this function read Json file and implements the data on the variables
    public MyGraph(String json_file) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(json_file));  //save the Json file on reader variable
            HashMap<?, ?> map = gson.fromJson(reader, HashMap.class);      //this hashmap will help us to save nodes
            String E = map.get("Edges").toString();
            E = E.replace("{", "");
            E = E.substring(1, E.length() - 2);
            String[] Edges = E.split("}, ");
            String N = map.get("Nodes").toString();
            N = N.replace("{", "");
            N = N.substring(1, N.length() - 2);
            String[] Nodes = N.split("}, ");
            this.nodesSize = Nodes.length;
            this.edgesSize = Edges.length;

            this.edges = new HashMap<>();
            this.nodes = new HashMap<>();
            this.OutEdge = new ArrayList[nodesSize];
            this.InEdge = new ArrayList[nodesSize];
            for (int i = 0; i < this.nodesSize; i++) {    //keeps the data of nodes and separates it to dedicated arrays
                this.nodes.put(i, new Node(Nodes[i]));
                this.OutEdge[i] = new ArrayList<>();
                this.InEdge[i] = new ArrayList<>();
            }
            String key = "";
            Edge e = new Edge();
            for (int i = 0; i < this.edgesSize; i++) {    //save the edges data and separates it to dedicated arrays
                e = new Edge(Edges[i]);
                key = Integer.toString(e.getSrc());
                key = key + ",";
                key = key + Integer.toString(e.getDest());
                this.edges.put(key, e);
                //int tmp = e.getSrc();
                this.OutEdge[e.getSrc()].add(e);
                this.InEdge[e.getDest()].add(e);
            }
            this.MC = 0;            //This variable keeps the number of actions that we did on the graph
            if (!nodes.isEmpty() && !edges.isEmpty()) {
                this.nodeItr = nodes.values().iterator();
                this.edgeItr = edges.values().iterator();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //class constructor, this class will help us to creat a new MyGraph

    public MyGraph(HashMap<String, EdgeData> edges, HashMap<Integer, NodeData> nodes) {
        this.edges = edges;
        this.nodes = nodes;
        this.MC = 0;
        this.edgesSize = edges.size();
        this.nodesSize = nodes.size();
        if (!nodes.isEmpty() && !edges.isEmpty()) {
            this.nodeItr = nodes.values().iterator();
            this.edgeItr = edges.values().iterator();
        }
        this.OutEdge = new ArrayList[nodesSize];
        this.InEdge = new ArrayList[nodesSize];
    }

    public ArrayList<EdgeData>[] getConnectedTo() {
        return OutEdge;
    }

    public HashMap<String, EdgeData> getEdges() {
        return edges;
    }

    public HashMap<Integer, NodeData> getNodes() {
        return nodes;
    }

    @Override
    public NodeData getNode(int key) {
        try {
            return this.nodes.get(key);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    //this class gets a two ID's of nodes and give us the desired edge
    @Override
    public EdgeData getEdge(int src, int dest) {
        String key = Integer.toString(src) + "," + Integer.toString(dest);
        try {
            return this.edges.get(key);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Add a NodeData to the graph
    @Override
    public void addNode(NodeData n) {
        this.nodesSize++;                   //change the amount of nodes
        Node tmp = new Node();
        tmp.setTag(n.getTag());
        tmp.setLocation(n.getLocation());
        tmp.setWeight(n.getWeight());
        tmp.setInfo(n.getInfo());
        tmp.setId(nodesSize - 1);
        this.nodes.put(nodesSize - 1, tmp); //add the node to right place
        MC++;
        ArrayList<EdgeData>[] tmpOut = new ArrayList[nodesSize];
        ArrayList<EdgeData>[] tmpIn = new ArrayList[nodesSize];
        for (int i = 0; i < this.nodesSize - 1; i++) {
            tmpOut[i] = OutEdge[i];
            tmpIn[i] = InEdge[i];

        }
        tmpOut[nodesSize - 1] = new ArrayList<>();
        tmpIn[nodesSize - 1] = new ArrayList<>();
        this.OutEdge = tmpOut;                      // add the node to the out edge
        this.InEdge = tmpIn;                         // add the node to the except edge
    }


    //connect between two nodes and declare a new edge
    @Override
    public void connect(int src, int dest, double w) {
        this.edgesSize++;                           // change the amount of edges
        Edge tmpEdge = new Edge();
        tmpEdge.setDest(dest);
        tmpEdge.setSrc(src);
        tmpEdge.setWeight(w);
        String key = Integer.toString(src) + "," + Integer.toString(dest);
        this.edges.put(key, tmpEdge);               // insert the edge in the next index
        this.OutEdge[src].add(tmpEdge);             // add the edge to the source nodes
        this.InEdge[dest].add(tmpEdge);             // add the edge to the destination nodes

        MC++;
    }

    //the iterator of node
    @Override
    public Iterator<NodeData> nodeIter() throws RuntimeException {
        this.nodeItr = nodes.values().iterator();
        return this.nodeItr;
    }

    //the iterator of edge
    @Override
    public Iterator<EdgeData> edgeIter() throws RuntimeException {
        this.edgeItr = edges.values().iterator();
        return this.edgeItr;

    }

    //the iterator of node by node ID
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) throws RuntimeException {
        ArrayList<EdgeData> tmp = new ArrayList<>();
        for (EdgeData e : this.edges.values()) {
            if (e.getSrc() == node_id) {
                tmp.add(e);
            }
        }
        return tmp.iterator();
    }

    //gets a key of Node and remove it from the graph
    @Override
    public NodeData removeNode(int key) {
        this.nodesSize--;
        NodeData tmp = this.nodes.get(key);
        this.nodes.remove(key);
        MC++;
        int size = this.OutEdge[key].size();
        for (int i = 0; i < size; i++) {        //remove the whole out edges
            removeEdge(this.OutEdge[key].get(i).getSrc(), this.OutEdge[key].get(i).getDest());
        }
        size = this.InEdge[key].size();
        for (int i = 0; i < size; i++) {        //remove the whole in edges
            removeEdge(this.InEdge[key].get(i).getSrc(), this.InEdge[key].get(i).getDest());
        }
        this.OutEdge[key].clear();              //deletes the whole edges from the arrays
        this.InEdge[key].clear();
        return tmp;
    }

    //remove edge by nodes
    @Override
    public EdgeData removeEdge(int src, int dest) {
        String key = Integer.toString(src) + "," + Integer.toString(dest);
        EdgeData tmp = this.edges.get(key);
        this.edges.remove(key);
        this.edgesSize--;
        MC++;
        return tmp;
    }

    public void setMC(int MC) {
        this.MC = MC;
    }

    @Override
    public int nodeSize() {
        return nodesSize;
    }

    @Override
    public int edgeSize() {
        return edgesSize;
    }

    @Override
    public int getMC() {
        return this.MC;
    }
}