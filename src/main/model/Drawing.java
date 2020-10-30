package model;

import java.util.ArrayList;

public class Drawing {
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
        width = pixels.size();
        if (width > 0) {
            height = pixels.get(0).size();
        } else {
            height = 0;
        }
        real = true;
        name = null;
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
}
