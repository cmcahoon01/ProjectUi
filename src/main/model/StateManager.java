package model;

import ui.ConsoleInterface;
import ui.UserInterface;

public class StateManager {
    public static final int MENU = 1;
    public static final int CREATE_NEW = 2;
    public static final int ADD_TO_EXISTING = 3;
    public static final int TEACH = 4;
    public static final int GUESS = 5;

    private static UserInterface ui;
    private static int currentState;

    // creates a new state manager
    public StateManager() {
        this(new ConsoleInterface());
    }

    public StateManager(UserInterface ui) {
        StateManager.ui = ui;
        menu();
    }

    // EFFECTS: displays the menu, then moves to the next state
    private static void menu() {
        currentState = MENU;
        ui.drawMenu();
        InputData in = ui.getInput();
        if (in.type != InputData.NAVIGATION) {
            ui.invalidInput();
        }
        int selected = in.navigation;
        if (selected == CREATE_NEW) {
            createNew();
        } else if (selected == ADD_TO_EXISTING) {
            addToExisting();
        } else if (selected == GUESS) {
            guess();
        }
    }

    // EFFECTS: displays the create a new symbol page, then moves to the next state
    private static void createNew() {
        currentState = CREATE_NEW;
        ui.drawCreateNew();
        InputData in = ui.getInput();
        if (in.type == InputData.NAVIGATION) {
            int selected = in.navigation;
            if (selected == MENU) {
                menu();
            } else {
                ui.invalidInput();
            }
        } else if (in.type == InputData.NAME) {
            Estimator.addSymbol(in.name);
            teach(in.name);
        } else if (in.type == InputData.DRAWING) {
            ui.invalidInput();
        }

    }

    // EFFECTS: displays the add drawings to an existing symbol page, then moves to the next state
    // MODIFIES: model
    private static void addToExisting() {
        currentState = ADD_TO_EXISTING;
        ui.drawAddToExisting();
        InputData in = ui.getInput();
        if (in.type == InputData.NAVIGATION) {
            int selected = in.navigation;
            if (selected == MENU) {
                menu();
            } else {
                ui.invalidInput();
            }
        } else if (in.type == InputData.NAME) {
            if (Estimator.contains(in.name)) {
                teach(in.name);
            } else {
                ui.invalidInput();
                addToExisting();
            }
        } else if (in.type == InputData.DRAWING) {
            ui.invalidInput();
        }
    }

    // EFFECTS: displays the teach the model page, then moves to the next state
    // MODIFIES: model
    private static void teach(String name) {
        currentState = TEACH;
        ui.teach();
        InputData in = ui.getInput();
        if (in.type == InputData.DRAWING) {
            Estimator.addDrawing(name, in.drawing);
            teach(name);
        } else if (in.type == InputData.NAVIGATION) {
            int selected = in.navigation;
            if (selected == MENU) {
                menu();
            } else {
                ui.invalidInput();
            }
        } else {
            ui.invalidInput();
        }

    }

    // EFFECTS: displays the model guessing page, then moves to the next state
    private static void guess() {
        currentState = GUESS;
        ui.guess();
        InputData in = ui.getInput();
        if (in.type == InputData.DRAWING) {
            String guess = Estimator.guess(in.drawing);
            ui.guess(guess);
            guess();
        } else if (in.type == InputData.NAVIGATION) {
            int selected = in.navigation;
            if (selected == MENU) {
                menu();
            } else {
                ui.invalidInput();
            }
        } else {
            ui.invalidInput();
        }
    }

    public static int getCurrentState() {
        return currentState;
    }


}
