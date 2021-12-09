package Ex2.tests;

import Ex2.Landmark;

import static org.junit.jupiter.api.Assertions.*;

class LandmarkTest {

    Landmark l = new Landmark("src/Ex2/data/G3.json", 0);
    Landmark l2 = new Landmark("src/Ex2/data/G3.json", 1);
    @org.junit.jupiter.api.Test
    void x() {
        assertEquals(35.212217299435025, l.x());
    }

    @org.junit.jupiter.api.Test
    void y() {
        assertEquals(32.106235628571426, l.y());
    }

    @org.junit.jupiter.api.Test
    void z() {
        assertEquals(0.0, l.z());
    }

    @org.junit.jupiter.api.Test
    void distance() {
        assertEquals(0.0012446544252953176, l.distance(l2));
    }
}