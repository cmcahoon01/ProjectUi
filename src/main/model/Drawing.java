package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Drawing implements Writable {
    private int height;
    private int width;
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

    public Drawing applyFilter(ArrayList<ArrayList<Double>> filter) {
        ArrayList<ArrayList<Double>> buffered = createBuffered(filter.size(), filter.get(0).size());
        ArrayList<ArrayList<Double>> filtered = sweepFilter(buffered, filter);
        return new Drawing(filtered);
    }

    private ArrayList<ArrayList<Double>> createBuffered(int rows, int columns) {
        ArrayList<ArrayList<Double>> buffered = new ArrayList<>();
        rows -= 1;
        columns -= 1;
        int above = rows / 2;
        int left = columns / 2;
        for (int i = 0; i < height + rows; i++) {
            buffered.add(new ArrayList<>());
            if (i >= above && i < above + height) {
                for (int j = 0; j < width + columns; j++) {
                    if (j >= left && j < left + width) {
                        buffered.get(i).add(pixels.get(i - above).get(j - left));
                    } else {
                        buffered.get(i).add(0.);
                    }
                }
            } else {
                for (int j = 0; j < width + columns; j++) {
                    buffered.get(i).add(0.);
                }
            }
        }
        return buffered;
    }

    private ArrayList<ArrayList<Double>> sweepFilter(ArrayList<ArrayList<Double>> buffered,
                                                     ArrayList<ArrayList<Double>> filter) {
        ArrayList<ArrayList<Double>> filtered = new ArrayList<>();
        int above = (filter.size() - 1) / 2;
        int left = (filter.get(0).size() - 1) / 2;
        for (int i = 0; i < height; i++) {
            filtered.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                double value = filterLocation(filter, buffered, i, j);
                filtered.get(i).add(value);
            }
        }
        return filtered;
    }

    private ArrayList<ArrayList<Double>> normalizeFilter(ArrayList<ArrayList<Double>> filter) {
        double sum = 0.;
        for (ArrayList<Double> row : filter) {
            for (Double val : row) {
                sum += val;
            }
        }
        ArrayList<ArrayList<Double>> normalized = new ArrayList<>();
        for (int i = 0; i < filter.size(); i++) {
            normalized.add(new ArrayList<>());
            for (int j = 0; j < filter.get(0).size(); j++) {
                double newVal = filter.get(j).get(i) / 2;
                normalized.get(i).add(newVal);
            }
        }
        return normalized;
    }

    private double filterLocation(ArrayList<ArrayList<Double>> filter,
                                  ArrayList<ArrayList<Double>> buffered, int x, int y) {
        double out = 0;
        for (int i = 0; i < filter.size(); i++) {
            for (int j = 0; j < filter.get(i).size(); j++) {
                double filterValue = filter.get(i).get(j);
                out += buffered.get(x + i).get(y + j) * filterValue;
            }
        }
        return out;
    }

    private ArrayList<ArrayList<Double>> removeBuffer(int rows, int columns,
                                                      ArrayList<ArrayList<Double>> tooBig) {
        int above = (rows - 1) / 2;
        int left = (columns - 1) / 2;
        ArrayList<ArrayList<Double>> reduced = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            reduced.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                double v = tooBig.get(i + above).get(j + left);
                reduced.get(i).add(v);
            }
        }
        return reduced;
    }
}
