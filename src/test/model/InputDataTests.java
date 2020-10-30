package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputDataTests {
    InputData in;

    @Test
    void testConstructor() {
        in = new InputData(InputData.NAVIGATION, 0);
        assertEquals(InputData.NAVIGATION, in.type);
        assertEquals(in.navigation, 0);
        assertNull(in.name);
        in = new InputData(InputData.NAME, "bob");
        assertEquals(InputData.NAME, in.type);
        assertEquals(in.name, "bob");
        Drawing d = new Drawing("jack");
        in = new InputData(InputData.DRAWING, d);
        assertEquals(InputData.DRAWING, in.type);
        assertEquals(in.drawing, d);
    }
}
