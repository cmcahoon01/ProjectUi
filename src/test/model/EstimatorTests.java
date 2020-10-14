package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstimatorTests {
    Estimator estimator;

    @Test
    void runBefore() {
        Estimator.wipe();
    }

    @Test
    void testContainsAndAddSymbol(){
        assertFalse(Estimator.contains("bob"));
        Estimator.addSymbol("bob");
        assertTrue(Estimator.contains("bob"));
    }

    @Test
    void testAddDrawing(){
        Estimator.addDrawing("John", new Drawing());
        // by design, there is no way to access the drawings outside of the class. I am unsure how to test this
    }
}