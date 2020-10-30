package model.ml;

import model.Drawing;
import model.LearnedSymbol;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Estimator {
    //will be keeping track of data and estimating in the guess page
    private static ArrayList<LearnedSymbol> learnedSymbols = new ArrayList<>();
    private static ConvolutionalNeuralNetwork cnn = new ConvolutionalNeuralNetwork();

    private static final String SAVE_FOLDER = "./data/saved/";

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
    public static boolean addDrawing(String name, Drawing drawing) {
        for (LearnedSymbol symbol : learnedSymbols) {
            if (symbol.getName().equals(name)) {
                symbol.add(drawing);
                return true;
            }
        }
        return false;
    }

    // Effects: returns the name of the symbol the drawing most resembles
    public static String guess(Drawing drawing) {
        if (learnedSymbols.isEmpty()) {
            return "I have not yet been trained";
        } else {
            return learnedSymbols.get((int) (Math.random() * learnedSymbols.size())).getName(); //TODO Make not random
        }

    }

    // Effects: Saves the model
    // Modifies: files
    public static boolean save(String location) {
        location = SAVE_FOLDER + location + ".json";
        PrintWriter writer;
        File file = new File(location);

        try {
            file.createNewFile();
            writer = new PrintWriter(file);
        } catch (IOException e) {
            return false;
        }

        JSONObject json = createJson();
        writer.print(json.toString(4));
        writer.close();
        return true;
    }

    // Effects: if possible, loads the model and returns true
    //              otherwise returns false
    // Modifies: files
    public static boolean load(String location) {
        location = SAVE_FOLDER + location + ".json";
        try {
            String jsonData = readFile(location);
            JSONObject jsonObject = new JSONObject(jsonData);
            cnn = new ConvolutionalNeuralNetwork(jsonObject.getJSONArray("cnn"));
            learnedSymbols.clear();
            for (Object json : jsonObject.getJSONArray("symbols")) {
                JSONObject nextSymbol = (JSONObject) json;
                learnedSymbols.add(new LearnedSymbol(nextSymbol));
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // EFFECTS: reads source file as string and returns it
    // Taken from example project
    private static String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // UNUSED method for testing purposes
    public static void wipe() {
        learnedSymbols = new ArrayList<>();
        cnn = new ConvolutionalNeuralNetwork();
    }

    // For testing purposes
    public static ConvolutionalNeuralNetwork getCnn() {
        return cnn;
    }

    public static JSONObject createJson() {
        JSONObject json = new JSONObject();
        json.put("cnn", cnn.getTempModel());
        JSONArray array = new JSONArray();
        for (LearnedSymbol ls : learnedSymbols) {
            array.put(ls.toJson());
        }
        json.put("symbols", array);
        return json;
    }

    public static ArrayList<LearnedSymbol> getLearnedSymbols() {
        return learnedSymbols;
    }
}
