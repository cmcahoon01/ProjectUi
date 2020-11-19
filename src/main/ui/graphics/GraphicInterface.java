package ui.graphics;

import model.InputData;
import model.LearnedSymbol;
import model.ml.Estimator;
import ui.StateManager;
import ui.UserInterface;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class GraphicInterface extends JFrame implements UserInterface {
    private final DefaultPanel panel;

    //Effects: constructs a new GraphicInterface
    public GraphicInterface() {
        super();
        int windowHeight = 900;
        int windowWidth = 1400;

        this.setTitle("GuiTest");
        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new JLabel("GraphicalInterface!"));
        this.setVisible(true);
        panel = new DefaultPanel(this);
        this.setContentPane(panel);


    }

    // Shows the main menu
    @Override
    public void drawMenu() {
        wipe();
        panel.createTitle("Create example drawing of objects, and then let the program guess what you draw");
        panel.createButton("Create a new Object", new InputData(InputData.NAVIGATION,
                StateManager.CREATE_NEW), panel.componentHeight * 3);
        drawLoadDropdown(panel.componentHeight * 4);
        if (!Estimator.getLearnedSymbols().isEmpty()) {
            panel.createButton("Save objects",
                    new InputData(InputData.NAVIGATION, StateManager.SAVE), panel.componentHeight * 5);
            drawAddToExistingDropdown(panel.componentHeight * 6);
            panel.createButton("Guess my drawing",
                    new InputData(InputData.NAVIGATION, StateManager.GUESS), panel.componentHeight * 7);
        }
        panel.showDrawings();
        draw();
    }

    // Shows the create new page
    @Override
    public void drawCreateNew() {
        wipe();
        panel.createTitle("Object Name");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    // deprecated
    @Override
    public void drawAddToExisting() {
        wipe();
        panel.createTitle("Object Name");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        ArrayList<String> objectNames = new ArrayList<>();
        for (LearnedSymbol ls : Estimator.getLearnedSymbols()) {
            objectNames.add(ls.getName());
        }
        panel.createDropDown("save objects", objectNames, panel.componentHeight * 2);
    }

    // Prompts user what drawing they want to add to
    private void drawAddToExistingDropdown(int y) {
        ArrayList<String> objects = new ArrayList<>();
        for (LearnedSymbol ls : Estimator.getLearnedSymbols()) {
            objects.add(ls.getName());
        }
        String htmlName = "<html><div style = 'text-align: center;'>add to an existing object</div></html>";
        panel.createDropDown(htmlName, objects, y);
    }

    private void drawLoadDropdown(int y) {
        ArrayList<String> files = new ArrayList<>();
        File folder = new File(Estimator.SAVE_FOLDER);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String name = listOfFiles[i].getName();
                name = name.substring(0, name.length() - 5);
                files.add(name);
            }
        }
        panel.createDropDown("load objects", files, y);
    }

    // Lets the user add drawings
    @Override
    public void teach(String name) {
        wipe();
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTitle("Add example drawings of a \"" + name + "\" (click or drag)", 0.6);
        panel.createDrawingArea(StateManager.TEACH);
        panel.showDrawings();
        draw();
    }

    // Guesses what the user is drawing
    @Override
    public void guess() {
        guess("");
    }

    // Guesses what the user is drawing
    @Override
    public void guess(String guess) {
        wipe();
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        if (guess.equals("")) {
            panel.createTitle("Draw an object");
        } else {
            panel.createTitle("I guess that is a \"" + guess + "\"");
        }
        panel.createDrawingArea(StateManager.GUESS);
        draw();
    }

    // Asks the user what the save name is
    @Override
    public void save() {
        wipe();
        panel.createTitle("File name to save as");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    // Asks the user what the load file is
    @Override
    public void load() {
        wipe();
        panel.createTitle("File name to load from");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    // Displays invalid input message
    @Override
    public void invalidInput() {
        wipe();
        panel.createTitle("Invalid Input.");
        System.out.println("invalid");
    }

    // Removes all components
    private void wipe() {
        panel.removeAll();
        draw();
    }

    // Updates JFrame and JPanel
    private void draw() {
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public InputData getInput() {
        return panel.getInput();
    }

}
