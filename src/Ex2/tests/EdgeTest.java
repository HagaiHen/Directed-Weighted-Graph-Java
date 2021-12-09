package Ex2.tests;

import Ex2.Edge;
import Ex2.api.EdgeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EdgeTest {

    EdgeData e = new Edge("src/Ex2/data/G3.json", 0);

    @Test
    void getSrc() {
        assertEquals(0, e.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(1, e.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(1.0286816758196655, e.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("", e.getInfo());
    }

    @Test
    void setInfo() {
        e.setInfo("hello");
        assertEquals("hello", e.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0, e.getTag());
    }

    @Test
    void setTag() {
        e.setTag(1);
        assertEquals(e.getTag(), 1);
    }
}