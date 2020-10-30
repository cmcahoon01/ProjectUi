package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LearnedSymbol implements Writable {
    private String name;
    private ArrayList<Drawing> drawings;

    public LearnedSymbol(String name) {
        this.name = name;
        drawings = new ArrayList<>();
    }

    public LearnedSymbol(JSONObject jsonObject) {
        this.name = jsonObject.getString("name");
        drawings = new ArrayList<>();
        for (Object json : jsonObject.getJSONArray("drawings")) {
            JSONObject nextSymbol = (JSONObject) json;
            drawings.add(new Drawing(nextSymbol));
        }
    }

    public String getName() {
        return name;
    }

    public void add(Drawing drawing) {
        drawings.add(drawing);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        JSONArray array = new JSONArray();
        for (Drawing d : drawings) {
            array.put(d.toJson());
        }
        json.put("drawings", array);
        return json;
    }
}
