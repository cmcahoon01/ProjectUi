package model;

import ui.ConsoleInterface;
import ui.UserInterface;

public class StateManager {
    private static final boolean GUI = false;

    private static final int MENU = 1;
    private static final int CREATE_NEW = 2;
    private static final int ADD_TO_EXISTING = 3;
    private static final int TEACH = 4;
    private static final int GUESS = 5;

    private static UserInterface ui;

    // creates a new state manager
    public StateManager() {
        if (GUI) {
            ui = new ConsoleInterface();
        }
        createNew();
    }

    // EFFECTS: displays the menu, then moves to the next state
    public void menu() {
        ui.drawMenu();
        Object in = ui.getInput();
        if (in instanceof Drawing) {
            throw new RuntimeException("drawing not excepted");
        }
        int selected = (int) in;
        if (selected == CREATE_NEW) {
            createNew();
        } else if (selected == ADD_TO_EXISTING) {
            addToExisting();
        } else if (selected == GUESS) {
            guess();
        } else {
            throw new RuntimeException("invalid menu selection");
        }
    }

    // EFFECTS: displays the create a new symbol page, then moves to the next state
    private void createNew() {
        ui.drawCreateNew();
        Object in = ui.getInput();
        if (in instanceof Drawing) {
            throw new RuntimeException("drawing not excepted");
        }
        int selected = (int) in;
        if (selected == MENU) {
            menu();
        } else if (selected == TEACH) {
            teach();
        } else {
            throw new RuntimeException("invalid createNew selection");
        }
    }

    // EFFECTS: displays the add drawings to an existing symbol page, then moves to the next state
    // MODIFIES: model
    private void addToExisting() {
        ui.drawAddToExisting();
        Object in = ui.getInput();
        if (in instanceof Drawing) {
            throw new RuntimeException("drawing not excepted");
        }
        int selected = (int) in;
        if (selected == MENU) {
            menu();
        } else if (selected == TEACH) {
            teach();
        } else {
            throw new RuntimeException("invalid addToExisting selection");
        }
    }

    // EFFECTS: displays the teach the model page, then moves to the next state
    // MODIFIES: model
    private void teach() {
        ui.teach();
        Object in = ui.getInput();
        if (in instanceof Drawing) {
            // model.teach((Drawing) in)
            teach();
        } else {
            int selected = (int) in;
            if (selected == MENU) {
                menu();
            } else if (selected == TEACH) {
                teach();
            } else {
                throw new RuntimeException("invalid teach selection");
            }
        }

    }

    // EFFECTS: displays the model guessing page, then moves to the next state
    private void guess() {
        ui.guess();
        Object in = ui.getInput();
        if (in instanceof Drawing) {
            // model.guess((Drawing) in)
            guess();
        } else {
            int selected = (int) in;
            if (selected == MENU) {
                menu();
            } else if (selected == GUESS) {
                guess();
            } else {
                throw new RuntimeException("invalid guess selection");
            }
        }
    }
}
