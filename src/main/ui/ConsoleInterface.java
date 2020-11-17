package ui;

import model.Drawing;
import model.InputData;
import ui.StateManager;
import ui.UserInterface;

import java.util.Scanner;

public class ConsoleInterface implements UserInterface {
    private Scanner input;

    public ConsoleInterface() {
        input = new Scanner(System.in);
    }

    // gets an input from the user based on the given page
    public InputData getInput() {
        boolean keepGoing = true;
        String command = input.next();
        if (command.equals("menu")) {
            return new InputData(InputData.NAVIGATION, StateManager.MENU);
        } else if (command.equals("add")) {
            return new InputData(InputData.NAVIGATION, StateManager.CREATE_NEW);
        } else if (command.equals("draw")) {
            return new InputData(InputData.NAVIGATION, StateManager.ADD_TO_EXISTING);
        } else if (command.equals("guess")) {
            return new InputData(InputData.NAVIGATION, StateManager.GUESS);
        } else if (StateManager.getCurrentState() == StateManager.ADD_TO_EXISTING
                || StateManager.getCurrentState() == StateManager.CREATE_NEW) {
            return new InputData(InputData.NAME, command);
        } else {
            // TODO: look at why this exists
            return new InputData(InputData.DRAWING, new Drawing(command));
        }
    }

    // displays the menu to select what the user wants to do
    public void drawMenu() {
        System.out.println("\n Main Menu: \n"
                + "\t to add a new symbol, type \"add\" \n"
                + "\t to add drawings to an existing symbol, type \"draw\" \n"
                + "\t to add a make the model guess your drawings, type \"guess\" \n");
    }

    // displays the page where users can add new symbols to the guesser
    public void drawCreateNew() {
        System.out.println("\n Create a new symbol: \n"
                + "\t to go back, type \"menu\" \n"
                + "\t type in the name of the new symbol here:\n");
    }

    // displays the page where users select which symbol they want to add drawings to
    public void drawAddToExisting() {
        System.out.println("\n Select drawing to add to: \n"
                + "\t to go back, type \"menu\" \n"
                + "\t type in the name of the symbol you want to add to here:\n");
    }

    // displays the page where users add new drawings to an existing symbol
    public void teach() {
        System.out.println("\n Add drawings to an existing symbol: \n"
                + "\t to go back, type \"menu\" \n"
                + "\t type in a sequence of characters that represent your drawing here:\n");
    }

    // displays the page where users draw symbols and the model guesses what they are
    public void guess() {
        System.out.println("\n Draw a symbol that the program will try and guess: \n"
                + "\t to go back, type \"menu\" \n"
                + "\t type in a sequence of characters that represent your drawing here:\n");
    }

    @Override
    public void guess(String guess) {
        System.out.println("\n I guess... " + guess);
        guess();
    }

    // displays generic invalid input message
    @Override
    public void invalidInput() {
        System.out.println("invalid input");
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }
}
