package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Drawing implements Writable {
    private int width;
    private int height;
    private final ArrayList<ArrayList<Double>> pixels;
    private final String name;

    private final boolean real;

    public Drawing(String name) {
        this.name = name;
        real = false;
        pixels = null;
        height = 0;
        width = 0;
    }

    public Drawing(ArrayList<ArrayList<Double>> pixels) {
        this.pixels = pixels;
        height = pixels.size();
        if (height > 0) {
            width = pixels.get(0).size();
        } else {
            width = 0;
        }
        real = true;
        name = null;
    }

    public Drawing(JSONObject jsonObject) {
        this.real = jsonObject.getBoolean("real");
        if (real) {
            pixels = new ArrayList<>();
            this.name = null;
            this.height = 0;
            this.width = 0;
            convert(jsonObject);
        } else {
            this.name = jsonObject.getString("name");
            pixels = null;
            height = 0;
            width = 0;
        }
    }

    private void convert(JSONObject jsonObject) {
        for (Object json : jsonObject.getJSONArray("pixels")) {
            JSONArray row = (JSONArray) json;
            pixels.add(new ArrayList<>());
            for (Object v : row) {
                Double value = (Double) v;
                pixels.get(height).add(value);
                if (height == 0) {
                    this.width++;
                }
            }
            this.height++;
        }
    }

    public ArrayList<ArrayList<Double>> getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public boolean isReal() {
        return real;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("real", real);
        if (real) {
            json.put("pixels", pixels);
            json.put("height", height);
            json.put("width", width);
        } else {
            json.put("name", name);
        }
        return json;
    }
}
