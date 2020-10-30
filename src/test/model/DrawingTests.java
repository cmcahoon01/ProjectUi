package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DrawingTests {
    Drawing realDrawing;
    Drawing fakeDrawing;

    @BeforeEach
    void runBefore() {
        ArrayList<ArrayList<Double>> pixels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pixels.add(new ArrayList<>());
            for (int j = 0; j < 5; j++) {
                pixels.get(i).add(i + j / 10.);
            }
        }
        realDrawing = new Drawing(pixels);
        fakeDrawing = new Drawing("faker");
    }

    @Test
    void testGetters() {
        assertEquals(5, realDrawing.getWidth());
        assertEquals(10, realDrawing.getHeight());
        assertEquals("faker", fakeDrawing.getName());
        assertTrue(realDrawing.isReal());
        assertFalse(fakeDrawing.isReal());
        assertNull(realDrawing.getName());
        assertEquals(0, fakeDrawing.getHeight());
        assertEquals(0, fakeDrawing.getHeight());
    }

    @Test
    void testNoSize() {
        Drawing drawing = new Drawing(new ArrayList<>());
        ArrayList<ArrayList<Double>> pixels = drawing.getPixels();
        assertEquals(0, pixels.size());
    }
}
