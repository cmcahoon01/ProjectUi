package model;

import java.util.ArrayList;

public class Estimator {
    //will be keeping track of data and estimating in the guess page
    private static ArrayList<LearnedSymbol> learnedSymbols = new ArrayList<>();

    public static boolean contains(String name) {
        for (LearnedSymbol symbol : learnedSymbols) {
            if (symbol.getName().equals(name)) {
                return true;
            }

        }
        return false;
    }

    public static void addSymbol(String name) {
        Estimator.learnedSymbols.add(new LearnedSymbol(name));
    }

    public static void addDrawing(String name, Drawing drawing) {
        for (LearnedSymbol symbol : learnedSymbols) {
            if (symbol.getName().equals(name)) {
                symbol.add(drawing);
            }
        }
    }

    public static String guess(Drawing drawing) {
        return "[not currently implemented]";
    }
}
