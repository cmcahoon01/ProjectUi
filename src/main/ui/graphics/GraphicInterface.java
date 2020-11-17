package ui.graphics;

import model.InputData;
import model.ml.Estimator;
import ui.StateManager;
import ui.UserInterface;

import javax.swing.*;

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
        panel.createTitle("Menu");
        panel.createButton("Create a new Drawing", new InputData(InputData.NAVIGATION,
                StateManager.CREATE_NEW), panel.componentHeight * 2);
        panel.createButton("Load",
                new InputData(InputData.NAVIGATION, StateManager.LOAD), panel.componentHeight * 3);
        if (!Estimator.getLearnedSymbols().isEmpty()) {
            panel.createButton("Save",
                    new InputData(InputData.NAVIGATION, StateManager.SAVE), panel.componentHeight * 4);
            panel.createButton("Add drawings to an existing symbol",
                    new InputData(InputData.NAVIGATION, StateManager.ADD_TO_EXISTING), panel.componentHeight * 5);
            panel.createButton("Guess my drawing",
                    new InputData(InputData.NAVIGATION, StateManager.GUESS), panel.componentHeight * 6);
            panel.showDrawings(0);
            draw();
        }
    }

    // Shows the create new page
    @Override
    public void drawCreateNew() {
        wipe();
        panel.createTitle("Name of the new drawing");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    // Prompts user what drawing they want to add to
    @Override
    public void drawAddToExisting() {
        wipe();
        panel.createTitle("Name of the drawing");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    // Lets the user add drawings
    @Override
    public void teach() {
        wipe();
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTitle("Create Reference Drawings");
        panel.createDrawingArea(StateManager.TEACH);
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
            panel.createTitle("Draw");
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
        panel.createTitle("Save name");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    // Asks the user what the load file is
    @Override
    public void load() {
        wipe();
        panel.createTitle("Save name");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    // Displays invalid input message
    @Override
    public void invalidInput() {
        wipe();
        panel.createTitle("Invalid Input.");
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
