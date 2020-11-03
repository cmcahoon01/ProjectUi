package model.ml;

import model.Drawing;
import model.LearnedSymbol;
import org.json.JSONArray;

import java.util.ArrayList;

public class ConvolutionalNeuralNetwork {
    private static final int[] CONVOLUTIONAL_LAYERS = new int[]{2, 4};
    // every number must be a multiple of the number before it

    private static final int[] FILTER_SIZE = new int[]{3, 3};
    // must be the same length as CONVOLUTIONAL_LAYERS

    private static final int NEURAL_LAYERS = 3;

    private int numClassifications;
    private ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> filters;


    public ConvolutionalNeuralNetwork(JSONArray jsonArray) {
//        tempModel = new ArrayList<>();
//        for (Object json : jsonArray) {
//            tempModel.add((Integer) json);
//        }
    }

    public ConvolutionalNeuralNetwork() {
        numClassifications = 3;
        initializeFilters();
    }

    private void initializeFilters() {
        filters = new ArrayList<>();
        filters.add(new ArrayList<>());
        for (int i = 0; i < CONVOLUTIONAL_LAYERS[0]; i++) {
            filters.get(0).add(example3by3());      // should not be example
        }
        for (int i = 1; i < CONVOLUTIONAL_LAYERS.length; i++) {
            filters.add(new ArrayList<>());
            for (int j = 0; j < CONVOLUTIONAL_LAYERS[i]; j++) {
                filters.get(i).add(example3by3());
            }
        }
    }

    private ArrayList<ArrayList<Double>> example3by3() {
        ArrayList<ArrayList<Double>> out = new ArrayList<>();
        ArrayList<Double> row = new ArrayList<>();
        row.add(-1.);
        row.add(1.);
        row.add(0.);
        out.add(row);
        out.add(row);
        out.add(row);
        return out;
    }

    public ArrayList<Integer> getTempModel() {
        return new ArrayList<>();
    }

    // Requires learnedSymbols.size() == numClassifications
    public LearnedSymbol predict(Drawing drawing, ArrayList<LearnedSymbol> learnedSymbols) {
        if (drawing.isReal()) {
            ArrayList<Drawing> lastConvolutionalLayer = allFilters(drawing);
            ArrayList<Double> flattened = flatten(lastConvolutionalLayer);
            ArrayList<Double> classifications = allNeurals(flattened);
            LearnedSymbol guess = matchSymbol(classifications, learnedSymbols);
            return guess;
        }
        return null;
    }

    private ArrayList<Drawing> allFilters(Drawing drawing) {
        ArrayList<Drawing> currentLayer = new ArrayList<>();
        currentLayer.add(drawing);
        for (int i = 0; i < filters.size(); i++) {
            currentLayer = oneFilterLayer(currentLayer, i);
        }
        return currentLayer;
    }

    private ArrayList<Drawing> oneFilterLayer(ArrayList<Drawing> inLayer, int layerNumber) {
        ArrayList<Drawing> outLayer = new ArrayList<>();
        for (int i = 0; i < filters.get(layerNumber).size(); i++) {
            outLayer.add(oneFilter(inLayer.get(i % inLayer.size()), layerNumber, i));
        }
        return outLayer;
    }

    public Drawing oneFilter(Drawing drawing, int layerNumber, int filterNumber) {
        return drawing.applyFilter(filters.get(layerNumber).get(filterNumber));
    }


    private ArrayList<Double> flatten(ArrayList<Drawing> lastConvolutionalLayer) {
        ArrayList<Double> flat = new ArrayList<>();
        for (Drawing drawing : lastConvolutionalLayer) {
            for (ArrayList<Double> arr : drawing.getPixels()) {
                flat.addAll(arr);
            }
        }

        return flat;
    }

    private ArrayList<Double> allNeurals(ArrayList<Double> flattened) {
        return new ArrayList<>(flattened.subList(0, numClassifications));
    }

    private LearnedSymbol matchSymbol(ArrayList<Double> classifications, ArrayList<LearnedSymbol> learnedSymbols) {
        int index = 0;
        double value = classifications.get(0);
        for (int i = 1; i < numClassifications; i++) {
            if (classifications.get(i) > value) {
                index = i;
                value = classifications.get(i);
            }
        }
        return learnedSymbols.get(index);
    }
}
