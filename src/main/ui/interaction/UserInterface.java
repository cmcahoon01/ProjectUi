package ui.interaction;

import model.InputData;

public interface UserInterface {
    // gets an input from the user based on the given page
    public InputData getInput();

    // displays the menu to select what the user wants to do
    public void drawMenu();

    // displays the page where users can add new symbols to the guesser
    public void drawCreateNew();

    // displays the page where users select which symbol they want to add drawings to
    public void drawAddToExisting();

    // displays the page where users add new drawings to an existing symbol
    public void teach();

    // displays the page where users draw symbols and the model guesses what they are
    public void guess();

    public void guess(String guess);

    // displays generic invalid input message
    public void invalidInput();

    // prompts the user where they want to save to
    public void save();

    // prompts the user where they want to load from
    public void load();
}
