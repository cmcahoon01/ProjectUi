package model;

import model.ml.ConvolutionalNeuralNetwork;
import model.ml.Estimator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

public class EstimatorMemoryTests {
    @BeforeEach
    void runBefore() {
        Estimator.wipe();
    }

    @Test
    void testWriterInvalidFile() {
        assertFalse(Estimator.save("badFile\\name"));
    }

    @Test
    void testCreateNewFile() {
        File file = new File("./data/saved/tempFile.json");
        if (file.exists()) {
            file.delete();
        }
        assertFalse(file.exists());
        assertTrue(Estimator.save("tempFile"));
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    void testProperSaveLoadData() {
        assertTrue(Estimator.save("tempFile"));
        ConvolutionalNeuralNetwork cnn = Estimator.getCnn();
        Estimator.load("tempFile");
        ConvolutionalNeuralNetwork new_cnn = Estimator.getCnn();
        File file = new File("./data/saved/tempFile.json");
        file.delete();
    }

    @Test
    void testImproperLoad() {
        File file = new File("./data/saved/tempFile.json");
        if (file.exists()) {
            fail("This file should not exist");
        }
        assertFalse(Estimator.load("tempFile"));
    }

    void createAndSaveComplex() {
        Estimator.addSymbol("symbol_1");
        Estimator.addSymbol("symbol_2");
        Estimator.addDrawing("symbol_1", new Drawing("drawn"));
        ArrayList<ArrayList<Double>> a = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            a.add(new ArrayList<>());
            a.get(i).add(i + 0.1);
            a.get(i).add(i + 0.2);
            a.get(i).add(i + 0.3);
        }
        Estimator.addDrawing("symbol_2", new Drawing(a));
        Estimator.addDrawing("symbol_2", new Drawing(a));
        Estimator.save("testing");
    }

    @Test
    void testComplexLoad() {
        Estimator.wipe();
        createAndSaveComplex();
        Estimator.load("testing");
        ArrayList<LearnedSymbol> a = Estimator.getLearnedSymbols();
        assertEquals(2, a.size());
        File file = new File("./data/saved/testing.json");
        file.delete();
    }
}
