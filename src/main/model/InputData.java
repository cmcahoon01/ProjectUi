package model;

public class InputData {
    public static final int NAVIGATION = 1;
    public static final int NAME = 2;
    public static final int DRAWING = 3;
    public int type;
    public int navigation;
    public String name;
    public Drawing drawing;

    // combines three possible user input types into one object
    public InputData(int type, Object o) {
        this.type = type;
        if (type == NAVIGATION) {
            navigation = (int) o;
        } else if (type == NAME) {
            name = (String) o;
        } else if (type == DRAWING) {
            drawing = (Drawing) o;
        }
    }
}
