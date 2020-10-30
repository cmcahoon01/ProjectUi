package model.ml;

import model.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConvolutionalNeuralNetwork implements Writable {
    private final ArrayList<Integer> tempModel;

    public ConvolutionalNeuralNetwork(JSONObject jsonObject) {
        tempModel = new ArrayList<>();
        for (Object json : jsonObject.getJSONArray("tmpModel")) {
            tempModel.add((Integer) json);
        }
    }

    public ConvolutionalNeuralNetwork() {
        tempModel = new ArrayList<>();
        tempModel.add(1);
        tempModel.add(2);
        tempModel.add(3);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tmpModel", tempModel);
        return json;
    }

    public ArrayList<Integer> getTempModel() {
        return tempModel;
    }
}
