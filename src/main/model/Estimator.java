package model;

import java.util.ArrayList;

public class Estimator {
    //will be keeping track of data and estimating in the guess page
    private static ArrayList<LearnedSymbol> learnedSymbols = new ArrayList<>();


    // Effects: checks if any of the known symbols have this name
    public static boolean contains(String name) {
        for (LearnedSymbol symbol : learnedSymbols) {
            if (symbol.getName().equals(name)) {
                return true;
            }

        }
        return false;
    }

    // Effects: adds a new symbol
    public static void addSymbol(String name) {
        Estimator.learnedSymbols.add(new LearnedSymbol(name));
    }

    // Effects: adds a drawing to a known symbol
    public static void addDrawing(String name, Drawing drawing) {
        for (LearnedSymbol symbol : learnedSymbols) {
            if (symbol.getName().equals(name)) {
                symbol.add(drawing);
            }
        }
    }

    // Effects: returns the name of the symbol the drawing most resembles
    public static String guess(Drawing drawing) {
        return "[not currently implemented]";
    }

    // UNUSED method for testing purposes
    public static void wipe() {
        learnedSymbols = new ArrayList<>();
    }
}
