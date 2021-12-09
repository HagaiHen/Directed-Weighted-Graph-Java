package Ex2.tests;

import Ex2.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Node n = new Node("src/Ex2/data/G3.json", 0);
    Node n2 = new Node("src/Ex2/data/G3.json", 0);
    Node n3 = new Node("src/Ex2/data/G3.json", 1);

    @Test
    void getKey() {
        assertEquals(0,n.getKey());
    }

    @Test
    void getLocation() {
        assertEquals(n.getLocation().toString(),n2.getLocation().toString());
    }

    @Test
    void setLocation() {
        n2.setLocation(n3.getLocation());
        assertEquals(n3.getLocation().toString(), n2.getLocation().toString());
    }

    @Test
    void getWeight() {
    }

    @Test
    void setWeight() {
    }

    @Test
    void getInfo() {
    }

    @Test
    void setInfo() {
    }

    @Test
    void getTag() {
        assertEquals(0, n.getTag());
    }

    @Test
    void setTag() {
        n2.setTag(5);
        assertEquals(5, n2.getTag());
    }

    @Test
    void testToString() {
        assertEquals("Node{pos=Landmark{x=35.212217299435025, y=32.106235628571426, z=0.0}, id=0, tag=0, info='null'}", n.toString());
    }
}