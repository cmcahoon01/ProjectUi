package ui.interaction;

import model.InputData;
import model.ml.Estimator;
import ui.StateManager;

import javax.swing.*;

public class GraphicInterface extends JFrame implements UserInterface {
    private int windowWidth = 1400;
    private int windowHeight = 900;
    private DefaultPanel panel;

    //Effects: constructs a new GraphicInterface
    public GraphicInterface() {
        super();
        this.setTitle("GuiTest");
        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new JLabel("GraphicalInterface!"));
        this.setVisible(true);
        panel = new DefaultPanel(this);
        this.setContentPane(panel);
    }


    @Override
    public InputData getInput() {
        return panel.getInput();
    }

    @Override
    public void drawMenu() {
        wipe();
        panel.createTitle("Menu");
        panel.createButton("Create a new Symbol", new InputData(InputData.NAVIGATION,
                StateManager.CREATE_NEW), panel.componentHeight * 2);
        panel.createButton("Load",
                new InputData(InputData.NAVIGATION, StateManager.LOAD), panel.componentHeight * 3);
        if (!Estimator.getLearnedSymbols().isEmpty()) {
            panel.createButton("Save",
                    new InputData(InputData.NAVIGATION, StateManager.SAVE), panel.componentHeight * 4);
            panel.createButton("Add drawings to an existing Symbol",
                    new InputData(InputData.NAVIGATION, StateManager.ADD_TO_EXISTING), panel.componentHeight * 5);
            panel.createButton("Guess my drawing",
                    new InputData(InputData.NAVIGATION, StateManager.GUESS), panel.componentHeight * 6);

        }
    }

    @Override
    public void drawCreateNew() {
        wipe();
        panel.createTitle("Name of the new drawing");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    @Override
    public void drawAddToExisting() {
        wipe();
        panel.createTitle("Name of the drawing");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    @Override
    public void teach() {
        wipe();
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTitle("Draw");
        panel.createDrawingArea();
        draw();
    }

    @Override
    public void guess() {
        guess("");
    }

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
        panel.createDrawingArea();
        draw();
    }

    @Override
    public void save() {
        wipe();
        panel.createTitle("Save name");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    @Override
    public void load() {
        wipe();
        panel.createTitle("Save name");
        panel.createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        panel.createTextField(panel.componentHeight * 2);
    }

    @Override
    public void invalidInput() {
        wipe();
        panel.createTitle("Invalid Input.");
    }

    private void wipe() {
        panel.removeAll();
        draw();
    }

    private void draw() {
        panel.revalidate();
        panel.repaint();
    }

}
