package Ex2;

import Ex2.Generate.GenerateGraph;
import Ex2.api.DirectedWeightedGraph;
import Ex2.api.DirectedWeightedGraphAlgorithms;
import Ex2.api.EdgeData;
import Ex2.api.NodeData;

import java.util.*;

public class MyGraphAlgo implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph g;

    public MyGraphAlgo(DirectedWeightedGraph g) {
        this.g = g;
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.g;
    }

    @Override
    public DirectedWeightedGraph copy() {
        HashMap<Integer, NodeData> node = new HashMap<>();
        Iterator<NodeData> it = g.nodeIter();
        int i = 0;
        while (it.hasNext()) {
            NodeData n = it.next();
            node.put(i, n);
            i++;
        }
        HashMap<String, EdgeData> edge = new HashMap<>();
        Iterator<EdgeData> iter = g.edgeIter();
        while (iter.hasNext()) {
            EdgeData e = iter.next();
            String key = e.getSrc() + "," + e.getDest();
            edge.put(key, e);
        }
        DirectedWeightedGraph tmp = new MyGraph(edge, node);
        return tmp;
    }

    private void BFS(HashSet<Integer> visited, Queue<NodeData> keys, MyGraph tmp) {

        while (!keys.isEmpty()) {
            NodeData d = keys.poll();
            visited.add(d.getKey());
            d.setTag(1);
            //Iterator<EdgeData> it = g.edgeIter();
            for (int i = 0; i < tmp.getConnectedTo()[d.getKey()].size(); i++) {
                int dest = tmp.getConnectedTo()[d.getKey()].get(i).getDest();
                if (!visited.contains(dest)) {
                    keys.add(tmp.getNode(dest));
                    visited.add(tmp.getNode(dest).getKey());
                    tmp.getNode(dest).setTag(1);
                }
            }
        }
    }

    private MyGraph getTranspose() {
        HashMap<String, EdgeData> edges = new HashMap<>();
        HashMap<Integer, NodeData> nodes = new HashMap<>();
        MyGraph tmp = new MyGraph(edges, nodes);
        Iterator<EdgeData> itE = this.g.edgeIter();
        EdgeData e = new Edge();
        //e = itE.next();
        Iterator<NodeData> itN = this.g.nodeIter();
        NodeData n = new Node();

        while (itN.hasNext()) {
            n = itN.next();
            tmp.addNode(n);
            n.setTag(0);
        }
        while (itE.hasNext()) {
            e = itE.next();
            int dest = e.getSrc();
            int src = e.getDest();
            tmp.connect(src, dest, e.getWeight());
        }
        tmp.setMC(0);
        return tmp;
    }

    @Override
    public boolean isConnected() {
        this.save("src/Ex2/data/new.json");
        MyGraph tmp = new MyGraph("src/Ex2/data/new.json");
        Iterator<NodeData> it = tmp.nodeIter();
        NodeData n = new Node();
        HashSet<Integer> visited = new HashSet<>();
        Queue<NodeData> qu = new LinkedList<>();
        qu.add(tmp.nodeIter().next());
        BFS(visited, qu, tmp);
        while (it.hasNext()) {
            n = it.next();
            if (n.getTag() == 0) {
                return false;
            }
        }
        tmp = this.getTranspose();
        it = tmp.nodeIter();
        while (it.hasNext()) {
            n = it.next();
            n.setTag(0);
        }
        qu.clear();
        visited.clear();
        qu.add(tmp.nodeIter().next());
        BFS(visited, qu, tmp);
        it = tmp.nodeIter();
        while (it.hasNext()) {
            n = it.next();
            if (n.getTag() == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        List<Double> ans = Dijkstra(src);
        return ans.get(dest);
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<Integer> path = DijkstraPrev(src);
        List<NodeData> ans = new ArrayList<>();
        int tmp = dest;
        while (tmp != src || this.g.getEdge(src, tmp) != null) {
            ans.add(this.g.getNode(tmp));
            tmp = path.get(tmp);
        }
        ans.add(this.g.getNode(tmp));
        return ans;
    }

    public List<Double> Dijkstra(int src) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        ArrayList<Double> dist = new ArrayList<>();
        ArrayList<Integer> prev = new ArrayList<>();
        Iterator<NodeData> it = this.g.nodeIter();
        this.save("src/Ex2/data/newJson.json");
        MyGraph m = new MyGraph("src/Ex2/data/newJson.json");
        NodeData n = new Node();

        while (it.hasNext()) {
            n = it.next();
            if (n.getKey() != src)
                dist.add(n.getKey(), Double.MAX_VALUE);
            else {
                dist.add(n.getKey(), null);
            }
            prev.add(n.getKey(), null);
            q.add(n.getKey());
        }
        dist.remove(src);
        dist.add(src, 0.0);
        int u = src, indx = 0;
        it = this.g.nodeIter();
        while (q.size() != 1) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < m.getConnectedTo()[u].size(); i++) {
                if (dist.get(m.getConnectedTo()[u].get(i).getDest()) > m.getConnectedTo()[u].get(i).getWeight() + dist.get(u)) {
                    double tmpDist = dist.get(u);
                    dist.remove(m.getConnectedTo()[u].get(i).getDest());
                    dist.add(m.getConnectedTo()[u].get(i).getDest(), m.getConnectedTo()[u].get(i).getWeight() + tmpDist);
                    prev.remove(m.getConnectedTo()[u].get(i).getDest());
                    prev.add(m.getConnectedTo()[u].get(i).getDest(), u);
                }
                if (q.contains(m.getConnectedTo()[u].get(i).getDest()) && m.getConnectedTo()[u].get(i).getWeight() < min && m.getConnectedTo()[u].get(i).getDest() != src) {
                    min = m.getConnectedTo()[u].get(i).getWeight();
                    indx = m.getConnectedTo()[u].get(i).getDest();
                }
            }
            if (u != indx) {
                u = indx;
            } else {
                u = q.peek();
            }
            q.remove(u);
            min = Double.MAX_VALUE;
            double alt = 0;
            it = this.g.nodeIter();
            for (int i = 0; i < m.getConnectedTo()[u].size(); i++) {
                alt = dist.get(u) + m.getConnectedTo()[u].get(i).getWeight();
                if (alt < dist.get(m.getConnectedTo()[u].get(i).getDest())) {
                    dist.remove(m.getConnectedTo()[u].get(i).getDest());
                    dist.add(m.getConnectedTo()[u].get(i).getDest(), alt);
                    prev.remove(m.getConnectedTo()[u].get(i).getDest());
                    prev.add(m.getConnectedTo()[u].get(i).getDest(), u);
                }
            }
        }
        int k = 0;
        while (k <= this.g.nodeSize()) {
            q.add(k);
            k++;
        }
        while (q.size() != 1) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < m.getConnectedTo()[u].size(); i++) {
                if (dist.get(m.getConnectedTo()[u].get(i).getDest()) > m.getConnectedTo()[u].get(i).getWeight() + dist.get(u)) {
                    double tmpDist = dist.get(u);
                    dist.remove(m.getConnectedTo()[u].get(i).getDest());
                    dist.add(m.getConnectedTo()[u].get(i).getDest(), m.getConnectedTo()[u].get(i).getWeight() + tmpDist);
                }
                if (q.contains(m.getConnectedTo()[u].get(i).getDest()) && m.getConnectedTo()[u].get(i).getWeight() < min && m.getConnectedTo()[u].get(i).getDest() != src) {
                    min = m.getConnectedTo()[u].get(i).getWeight();
                    indx = m.getConnectedTo()[u].get(i).getDest();
                }
            }
            if (u != indx) {
                u = indx;
            } else {
                u = q.peek();
            }
            q.remove(u);
            double alt = 0;
            for (int i = 0; i < m.getConnectedTo()[u].size(); i++) {
                alt = dist.get(u) + m.getConnectedTo()[u].get(i).getWeight();
                if (alt < dist.get(m.getConnectedTo()[u].get(i).getDest())) {
                    dist.remove(m.getConnectedTo()[u].get(i).getDest());
                    dist.add(m.getConnectedTo()[u].get(i).getDest(), alt);
                }
            }
        }
        return dist;
    }

    public double[] DijkstraMax(int src) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        List<Double> dist = new ArrayList<>();
        dist = Dijkstra(src);
        double max = Double.MIN_VALUE;
        int index = 0;
        for (double d : dist) {
            if (d > max && d != Double.MAX_VALUE) {
                max = d;
                index = dist.indexOf(d);
            }
        }
        double[] arr = new double[2];
        arr[0] = index;
        arr[1] = max;
        return arr;
    }

    public List<Integer> DijkstraPrev(int src) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        ArrayList<Double> dist = new ArrayList<>();
        ArrayList<Integer> prev = new ArrayList<>();
        Iterator<NodeData> it = this.g.nodeIter();
        this.save("src/Ex2/data/newJson.json");
        MyGraph m = new MyGraph("src/Ex2/data/newJson.json");
        NodeData n = new Node();

        while (it.hasNext()) {
            n = it.next();
            if (n.getKey() != src)
                dist.add(n.getKey(), Double.MAX_VALUE);
            else {
                dist.add(n.getKey(), null);
            }
            prev.add(n.getKey(), null);
            q.add(n.getKey());
        }
        dist.remove(src);
        dist.add(src, 0.0);
        int u = src, indx = 0;
        it = this.g.nodeIter();
        while (q.size() != 1) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < m.getConnectedTo()[u].size(); i++) {
                if (dist.get(m.getConnectedTo()[u].get(i).getDest()) > m.getConnectedTo()[u].get(i).getWeight() + dist.get(u)) {
                    double tmpDist = dist.get(u);
                    dist.remove(m.getConnectedTo()[u].get(i).getDest());
                    dist.add(m.getConnectedTo()[u].get(i).getDest(), m.getConnectedTo()[u].get(i).getWeight() + tmpDist);
                    prev.remove(m.getConnectedTo()[u].get(i).getDest());
                    prev.add(m.getConnectedTo()[u].get(i).getDest(), u);
                }
                if (q.contains(m.getConnectedTo()[u].get(i).getDest()) && m.getConnectedTo()[u].get(i).getWeight() < min && m.getConnectedTo()[u].get(i).getDest() != src) {
                    min = m.getConnectedTo()[u].get(i).getWeight();
                    indx = m.getConnectedTo()[u].get(i).getDest();
                }
            }
            if (u != indx) {
                u = indx;
            } else {
                u = q.peek();
            }
            q.remove(u);
            min = Double.MAX_VALUE;
            double alt = 0;
            it = this.g.nodeIter();
            for (int i = 0; i < m.getConnectedTo()[u].size(); i++) {
                alt = dist.get(u) + m.getConnectedTo()[u].get(i).getWeight();
                if (alt < dist.get(m.getConnectedTo()[u].get(i).getDest())) {
                    dist.remove(m.getConnectedTo()[u].get(i).getDest());
                    dist.add(m.getConnectedTo()[u].get(i).getDest(), alt);
                    prev.remove(m.getConnectedTo()[u].get(i).getDest());
                    prev.add(m.getConnectedTo()[u].get(i).getDest(), u);
                }
            }
        }
        int k = 0;
        while (k <= this.g.nodeSize()) {
            q.add(k);
            k++;
        }
        while (q.size() != 1) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < m.getConnectedTo()[u].size(); i++) {
                if (dist.get(m.getConnectedTo()[u].get(i).getDest()) > m.getConnectedTo()[u].get(i).getWeight() + dist.get(u)) {
                    double tmpDist = dist.get(u);
                    dist.remove(m.getConnectedTo()[u].get(i).getDest());
                    dist.add(m.getConnectedTo()[u].get(i).getDest(), m.getConnectedTo()[u].get(i).getWeight() + tmpDist);
                }
                if (q.contains(m.getConnectedTo()[u].get(i).getDest()) && m.getConnectedTo()[u].get(i).getWeight() < min && m.getConnectedTo()[u].get(i).getDest() != src) {
                    min = m.getConnectedTo()[u].get(i).getWeight();
                    indx = m.getConnectedTo()[u].get(i).getDest();
                }
            }
            if (u != indx) {
                u = indx;
            } else {
                u = q.peek();
            }
            q.remove(u);
            min = Double.MAX_VALUE;
            double alt = 0;
            it = this.g.nodeIter();
            for (int i = 0; i < m.getConnectedTo()[u].size(); i++) {
                alt = dist.get(u) + m.getConnectedTo()[u].get(i).getWeight();
                if (alt < dist.get(m.getConnectedTo()[u].get(i).getDest())) {
                    dist.remove(m.getConnectedTo()[u].get(i).getDest());
                    dist.add(m.getConnectedTo()[u].get(i).getDest(), alt);
                    prev.remove(m.getConnectedTo()[u].get(i).getDest());
                    prev.add(m.getConnectedTo()[u].get(i).getDest(), u);
                }
            }
        }
        return prev;
    }

    @Override
    public NodeData center() {
        if (this.isConnected()) {
            Iterator<NodeData> it = this.g.nodeIter();
            NodeData n = new Node();
            List<double[]> cen = new ArrayList<>();
            while (it.hasNext()) {
                n = it.next();
                cen.add(DijkstraMax(n.getKey())); //returns the max path
            }
            int min_index = 0;
            double min = Double.MAX_VALUE;
            for (double[] d : cen) {                   //find the minimum from all the values
                if (d[1] < min) {
                    min = d[1];
                    min_index = (int) d[0];
                }
            }
            return this.g.getNode(min_index);
        } else {
            return null;
        }
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        HashMap<Integer, List<Double>> dist = new HashMap<>();
        int src = 0, dest = 0;
        double min = Double.MAX_VALUE;
        for (NodeData n : cities) {
            dist.put(n.getKey(), Dijkstra(n.getKey()));
        }
        Iterator<Integer> itrNode = dist.keySet().iterator();

        int n1 = 0;
        Iterator<List<Double>> itrlists = dist.values().iterator();
        List<Double> tmp = new ArrayList<>();
        while (itrNode.hasNext()) {
            n1 = itrNode.next();
            tmp = itrlists.next();
            for (NodeData n : cities) {
                if (tmp.get(n.getKey()) < min && tmp.get(n.getKey()) > 0 && n.getKey() != n1) {
                    min = tmp.get(n.getKey());
                    src = n1;
                    dest = n.getKey();
                }
            }
        }
        List<NodeData> ans = new ArrayList<>();
        ans.add(this.g.getNode(src));
        ans.add(this.g.getNode(dest));
        ArrayList<NodeData> citiesCopy = new ArrayList<>();
        for (NodeData n : cities) {
            citiesCopy.add(n);
        }
        int i = 0;
        for (NodeData n : cities) {
            if (i == -1)
                i++;
            if (citiesCopy.get(i).getKey() == src) {
                citiesCopy.remove(i);
                i--;
            }
            if (i == -1)
                i++;
            if (i == citiesCopy.size())
                break;
            if (citiesCopy.get(i).getKey() == dest) {
                citiesCopy.remove(i);
                i--;
            }
            i++;
            if (i == citiesCopy.size())
                break;
        }
        double minDest = Double.MAX_VALUE;
        List<Double> tmpDest = new ArrayList<>();
        int check = dest;
        int indx = 0;
        int k = 0;
        int tmpIndx = 0;
        while (ans.size() != cities.size()) {
            k = 0;
            minDest = Double.MAX_VALUE;
            for (NodeData n : citiesCopy) {
                tmpDest = Dijkstra(check);
                if (tmpDest.get(n.getKey()) < minDest) {
                    minDest = tmpDest.get(n.getKey());
                    indx = n.getKey();
                    tmpIndx = k;
                }
                k++;
            }
            citiesCopy.remove(tmpIndx);
            ans.add(this.g.getNode(indx));
            check = indx;
        }
        return ans;
    }

    @Override
    public boolean save(String file) {
        GenerateGraph gen = new GenerateGraph(this.g);
        gen.GenerateJson(gen, file);
        return true;
    }

    @Override
    public boolean load(String file) {
        try {
            this.g = new MyGraph(file);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
