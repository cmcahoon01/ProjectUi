package model;

import java.util.ArrayList;

public class LearnedSymbol {
    private String name;
    private ArrayList<Drawing> drawings;

    public LearnedSymbol(String name) {
        this.name = name;
        drawings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void add(Drawing drawing) {
        drawings.add(drawing);
    }
}
