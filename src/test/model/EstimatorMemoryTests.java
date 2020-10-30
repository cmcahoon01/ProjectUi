package model;

import model.ml.ConvolutionalNeuralNetwork;
import model.ml.Estimator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        File file = new File("saved/tempFile.json");
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
        File file = new File("saved/tempFile.json");
        file.delete();
    }

    @Test
    void testImproperLoad() {
        File file = new File("saved/tempFile.json");
        if (file.exists()) {
            fail("This file should not exist");
        }
        assertFalse(Estimator.load("tempFile"));
    }
}
