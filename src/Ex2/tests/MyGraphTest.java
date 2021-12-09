package Ex2.tests;

import Ex2.Edge;
import Ex2.Landmark;
import Ex2.MyGraph;
import Ex2.Node;
import Ex2.api.EdgeData;
import Ex2.api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;


class MyGraphTest {
    MyGraph g = new MyGraph("src/Ex2/data/G3.json");
    Landmark pos = new Landmark(35.212217299435025, 32.106235628571426, 0.0);
    Node n = new Node(pos, "", 0);
    Edge e = new Edge(0, 1, 1.0286816758196655, "");

    @Test
    void getNode() {
        g.getNode(0);
        assertEquals(g.getNode(0).getLocation().x(), n.getLocation().x());
        assertEquals(g.getNode(0).getLocation().y(), n.getLocation().y());
        assertEquals(g.getNode(0).getLocation().z(), n.getLocation().z());
        assertEquals(g.getNode(0).getKey(), n.getKey());
    }

    @Test
    void getEdge() {
        assertEquals(e.getSrc(), g.getEdge(0, 1).getSrc());
        assertEquals(e.getDest(), g.getEdge(0, 1).getDest());
        assertEquals(e.getTag(), g.getEdge(0, 1).getTag());
        assertEquals(e.getInfo(), g.getEdge(0, 1).getInfo());
        assertEquals(e.getWeight(), g.getEdge(0, 1).getWeight());
    }

    @Test
    void addNode() {
        Landmark tmpPos = new Landmark(8, 4, 5);
        int tmpSize = g.nodeSize();
        boolean b = false;
        Node tmpNode = new Node(tmpPos, "", g.nodeSize());
        g.addNode(tmpNode);
        assertEquals(tmpSize + 1, g.nodeSize());
        if (g.getNode(tmpSize) != null)
            b = true;
        assertTrue(b);
    }

    @Test
    void connect() {
        boolean b = false;
        g.connect(0, 7, 1.2);
        if (g.getEdge(0, 7) != null) {
            b = true;
        }
        assertEquals(g.getEdge(0, 7).getWeight(), 1.2);
        assertTrue(b);
        b = false;
        for (EdgeData e : g.getConnectedTo()[0]) {
            if (e.equals(g.getEdge(0, 7))) {
                b = true;
            }
        }
        assertTrue(b);
        g.connect(0, 1, 0.5);
        assertEquals(g.getEdge(0, 1).getWeight(), 0.5);

    }

    @Test
    void nodeIter() {
        Iterator<NodeData> it = g.nodeIter();
        int i = 0;
        while (it.hasNext()) {
            NodeData n = it.next();
            assertEquals(n.getKey(), g.getNodes().get(i).getKey());
            assertEquals(n.getInfo(), g.getNodes().get(i).getInfo());
            assertEquals(n.getLocation(), g.getNodes().get(i).getLocation());
            assertEquals(n.getTag(), g.getNodes().get(i).getTag());
            i++;
        }
    }

    @Test
    void edgeIter() {
        Iterator<EdgeData> it = g.edgeIter();
        int i = 0;
        while (it.hasNext()) {
            EdgeData e = it.next();
            String key = e.getSrc() + "," + e.getDest();
            assertEquals(e.getWeight(), g.getEdges().get(key).getWeight());
            assertEquals(e.getSrc(), g.getEdges().get(key).getSrc());
            assertEquals(e.getDest(), g.getEdges().get(key).getDest());
            assertEquals(e.getInfo(), g.getEdges().get(key).getInfo());
            assertEquals(e.getTag(), g.getEdges().get(key).getTag());
            i++;
        }

    }

    @Test
    void testEdgeIter() {
        Iterator<EdgeData> it = g.edgeIter(0);

        while (it.hasNext()) {
            EdgeData e = it.next();
            String key = e.getSrc() + "," + e.getDest();
            assertEquals(e.getSrc(), 0);
            assertEquals(e.getDest(), g.getEdge(0, e.getDest()).getDest());
            assertEquals(e.getWeight(), g.getEdge(e.getSrc(), e.getDest()).getWeight());
            assertEquals(e.getTag(), g.getEdge(e.getSrc(), e.getDest()).getTag());
        }
    }

    @Test
    void removeNode() {
        boolean b = false;
        int size = g.getConnectedTo()[0].size();
        g.removeNode(0);
        if (g.getNode(0) == null) {
            b = true;
        }
        assertTrue(b);
        b = false;
        assertEquals(0, g.getConnectedTo()[0].size());
        if (g.getEdge(0, 1) == null && g.getEdge(0, 2) == null && g.getEdge(0, 8) == null && g.getEdge(0, 9) == null) {
            b = true;
        }
        assertTrue(b);
        b = false;
        assertEquals(1 + size + 4, g.getMC());
        size = g.getConnectedTo()[1].size();
        g.removeNode(1);
        assertEquals(5 + 1 + size + 9, g.getMC());
    }

    @Test
    void removeEdge() {
        int size = g.edgeSize();
        boolean b = false;
        g.removeEdge(0, 1);
        assertEquals(size, g.edgeSize() + 1);
        if (g.getEdge(0, 1) == null) {
            b = true;
        }
        assertTrue(b);
    }

    @Test
    void nodeSize() {
        assertTrue(g.nodeSize() == 48);
        g.removeNode(0);
        assertTrue(g.nodeSize() == 47);
    }

    @Test
    void edgeSize() {
        assertTrue(g.edgeSize() == 166);
        g.removeEdge(0, 1);
        assertTrue(g.edgeSize() == 165);
    }

    @Test
    void getMC() {
        assertTrue(g.getMC() == 0);
        g.removeEdge(4, 5);
        assertTrue(g.getMC() == 1);
        g.removeNode(0);
        assertTrue(g.getMC() == 10);
    }
}