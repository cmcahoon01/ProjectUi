package model;

import model.ml.Estimator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstimatorTests {

    @BeforeEach
    void runBefore() {
        Estimator.wipe();
    }

    @Test
    void testContainsAndAddSymbol() {
        assertFalse(Estimator.contains("bob"));
        Estimator.addSymbol("bob");
        assertTrue(Estimator.contains("bob"));
        Estimator.addSymbol("jack");
        assertTrue(Estimator.contains("bob"));
        assertTrue(Estimator.contains("jack"));
    }

    @Test
    void testAddDrawing() {
        assertFalse(Estimator.addDrawing("John", new Drawing("tester")));
        Estimator.addSymbol("bob");
        assertTrue(Estimator.addDrawing("bob", new Drawing("tester")));
    }

    @Test
    void testGuess() {
        assertEquals("I have not yet been trained", Estimator.guess(new Drawing("test")));
        Estimator.addSymbol("bob");
        Estimator.addSymbol("jack");
        assertTrue(Estimator.contains(Estimator.guess(new Drawing("test"))));
    }

    @Test
    void testSave() {
        Estimator.save("saveTest");
    }

    @Test
    void testLoad() {
        System.out.println(Estimator.load("saveTest"));
        System.out.println(Estimator.getCnn().getTempModel());
    }
}