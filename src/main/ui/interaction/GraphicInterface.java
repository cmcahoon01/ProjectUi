package ui.interaction;

import model.InputData;
import ui.StateManager;

import javax.swing.*;
import java.awt.*;

public class GraphicInterface extends JFrame implements UserInterface {
    private int windowWidth = 1400;
    private int windowHeight = 900;
    private Display panel;
    private InputData userInput;
    private final Object sleeper;

    //Effects: constructs a new GraphicInterface
    public GraphicInterface() {
        super();
        this.setTitle("GuiTest");
        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new JLabel("GraphicalInterface!"));
        this.setVisible(true);
        panel = new Display();
        panel.setLayout(null);
        this.setContentPane(panel);
        sleeper = new Object();

    }

    private class Display extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension d = this.getSize();
            windowWidth = d.width + 16;
            windowHeight = d.height + 39;
        }
    }

    private void createButton(String name, InputData input, int x, int y) {

        JButton b = new JButton(name);
        b.addActionListener(e -> {
            userInput = input;
            synchronized (sleeper) {
                sleeper.notify();
            }
        });
        panel.add(b);
        int width = name.length() * 7 + 5;
        b.setBounds(x, y, width, 25);
        b.setBorder(null);
    }

    // Waits for user input then returns it
    @Override
    public InputData getInput() {
        System.out.println("waiting");
        try {
            synchronized (sleeper) {
                sleeper.wait();
            }
        } catch (InterruptedException e) {
            // pass
        }
        System.out.println("returning" + userInput.type);
        return userInput;
    }

    @Override
    public void drawMenu() {
        wipe();
        createButton("Create a new Symbol", new InputData(InputData.NAVIGATION,
                StateManager.CREATE_NEW), 0, 0);
        createButton("Add drawings to an existing Symbol",
                new InputData(InputData.NAVIGATION, StateManager.ADD_TO_EXISTING), 0, 30);
        createButton("Guess my drawing",
                new InputData(InputData.NAVIGATION, StateManager.GUESS), 0, 60);
        createButton("Save",
                new InputData(InputData.NAVIGATION, StateManager.SAVE), 0, 90);
        createButton("Load",
                new InputData(InputData.NAVIGATION, StateManager.LOAD), 0, 120);
        draw();
    }

    @Override
    public void drawCreateNew() {
        wipe();
        createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        draw();
    }

    @Override
    public void drawAddToExisting() {
        wipe();
        createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        draw();
    }

    @Override
    public void teach() {
        wipe();
        createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        draw();

    }

    @Override
    public void guess() {
        wipe();
        createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        draw();
    }

    @Override
    public void guess(String guess) {
        wipe();
        createButton("Return to menu", new InputData(InputData.NAVIGATION,
                StateManager.MENU), 0, 0);
        draw();
    }

    @Override
    public void save() {
    }

    @Override
    public void load() {
    }

    @Override
    public void invalidInput() {
    }

    private void wipe() {
        panel.removeAll();
    }

    private void draw() {
        panel.revalidate();
        panel.repaint();
    }
}
