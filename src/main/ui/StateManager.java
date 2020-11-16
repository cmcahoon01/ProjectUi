package ui;

import model.ml.Estimator;
import model.InputData;
import ui.interaction.GraphicInterface;
import ui.interaction.UserInterface;

public class StateManager {
    public static final int MENU = 1;
    public static final int CREATE_NEW = 2;
    public static final int ADD_TO_EXISTING = 3;
    public static final int TEACH = 4;
    public static final int GUESS = 5;
    public static final int SAVE = 6;
    public static final int LOAD = 7;

    private static UserInterface ui;
    private static int currentState;

    // creates a new state manager
    public StateManager() {
        this(new GraphicInterface());
    }

    public StateManager(UserInterface ui) {
        StateManager.ui = ui;
        Estimator.addSymbol("bob");
        menu();
    }

    // EFFECTS: displays the menu, then moves to the next state
    private static void menu() {
        currentState = MENU;
        ui.drawMenu();
        InputData in = ui.getInput();
        if (in.type != InputData.NAVIGATION) {
            invalidInput();
        }

        switch (in.navigation) {
            case CREATE_NEW:
                createNew();
                break;
            case ADD_TO_EXISTING:
                addToExisting();
                break;
            case GUESS:
                guess();
                break;
            case SAVE:
                save();
                break;
            case LOAD:
                load();
                break;
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
                invalidInput();
            }
        } else if (in.type == InputData.NAME) {
            Estimator.addSymbol(in.name);
            teach(in.name);
        } else if (in.type == InputData.DRAWING) {
            invalidInput();
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
                invalidInput();
            }
        } else if (in.type == InputData.NAME) {
            if (Estimator.contains(in.name)) {
                teach(in.name);
            } else {
                invalidInput();
                addToExisting();
            }
        } else if (in.type == InputData.DRAWING) {
            invalidInput();
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
                invalidInput();
            }
        } else {
            invalidInput();
        }

    }

    // EFFECTS: displays the model guessing page, then moves to the next state
    private static void guess() {
        currentState = GUESS;
        ui.guess();
        InputData in = ui.getInput();
        if (in.type == InputData.DRAWING) {
            String guess = Estimator.guess(in.drawing);
            System.out.println("good");
            ui.guess(guess);
            in = ui.getInput();
            if (in.type == InputData.NAVIGATION) {
                if (in.navigation == GUESS) {
                    guess();
                } else if (in.navigation == MENU) {
                    menu();
                }
            }
        } else if (in.type == InputData.NAVIGATION) {
            int selected = in.navigation;
            if (selected == MENU) {
                menu();
            }
        } else {
            invalidInput();
        }
    }

    // EFFECTS: Asks the user where to save, and saves the model there
    // MODIFIES: file structure
    private static void save() {
        currentState = SAVE;
        ui.save();
        InputData in = ui.getInput();
        if (in.type == InputData.NAME) {
            System.out.println(in.name);
            if (Estimator.save(in.name)) {
                menu();
            } else {
                invalidInput();
            }
        } else if (in.type == InputData.NAVIGATION && in.navigation == MENU) {
            menu();
        } else {
            invalidInput();
        }
    }

    // EFFECTS: Asks the user where to load from, and uses that estimator
    // MODIFIES: Estimator
    private static void load() {
        currentState = LOAD;
        ui.load();
        InputData in = ui.getInput();
        if (in.type == InputData.NAME) {
            if (Estimator.load(in.name)) {
                menu();
            } else {
                invalidInput();
            }
        } else if (in.type == InputData.NAVIGATION && in.navigation == MENU) {
            menu();
        } else {
            invalidInput();
        }
    }

    public static int getCurrentState() {
        return currentState;
    }

    private static void invalidInput() {
        ui.invalidInput();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        navigate();
    }

    private static void navigate() {
        switch (currentState) {
            case MENU:
                menu();
                break;
            case CREATE_NEW:
                createNew();
                break;
            case ADD_TO_EXISTING:
                addToExisting();
                break;
            case GUESS:
                guess();
                break;
            case SAVE:
                save();
                break;
            case LOAD:
                load();
                break;
        }
    }

}
