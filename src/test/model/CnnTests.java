package model;

import model.ml.ConvolutionalNeuralNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CnnTests {
    Drawing drawing;
    ArrayList<LearnedSymbol> symbols;

    @BeforeEach
    public void runBefore() {
        ArrayList<ArrayList<Double>> pixels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            pixels.add(new ArrayList<>());
            for (int j = 0; j < 4; j++) {
                pixels.get(i).add((double) j);
            }
        }
        drawing = new Drawing(pixels);
        symbols = new ArrayList<>();
        symbols.add(new LearnedSymbol("1"));
        symbols.add(new LearnedSymbol("2"));
        symbols.add(new LearnedSymbol("3"));
    }

    @Test
    public void basicTest() {
        ConvolutionalNeuralNetwork cnn = new ConvolutionalNeuralNetwork();
        LearnedSymbol out = cnn.predict(drawing, symbols);
        System.out.println(out.getName());
    }

    @Test
    public void filterTest() {
        Double[][] array = {
                {1d, 0d, 0d},
                {0d, 0d, 0d},
                {0d, 0d, 0d}};
        ArrayList<ArrayList<Double>> filter = new ArrayList<>();
        for (Double[] val : array) {
            filter.add(new ArrayList<>(Arrays.asList(val)));
        }
        Drawing out = drawing.applyFilter(filter);
        System.out.println(drawing.getPixels());
        System.out.println(out.getPixels());
    }
}
