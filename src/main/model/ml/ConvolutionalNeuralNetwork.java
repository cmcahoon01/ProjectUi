package model.ml;

import model.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConvolutionalNeuralNetwork {
    private final ArrayList<Integer> tempModel;

    public ConvolutionalNeuralNetwork(JSONArray jsonArray) {
        tempModel = new ArrayList<>();
        for (Object json : jsonArray) {
            tempModel.add((Integer) json);
        }
    }

    public ConvolutionalNeuralNetwork() {
        tempModel = new ArrayList<>();
        tempModel.add(1);
        tempModel.add(2);
        tempModel.add(3);
    }

    public ArrayList<Integer> getTempModel() {
        return tempModel;
    }
}
