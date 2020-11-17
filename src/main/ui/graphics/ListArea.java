package ui.graphics;

import model.LearnedSymbol;
import model.ml.Estimator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ListArea extends JPanel {

    private static final int titleSize = 30;
    private static final int listSize = 20;

    /**
     * Creates a new <code>ListArea</code> with a double buffer
     * and a flow layout.
     */
    public ListArea() {
        setLayout(null);
    }

    // adds all parts of the list
    public void draw() {
        createTitle();
        createBorders();
        createHeaders();
        createList();
        setVisible(true);
    }

    // draws the lines between text
    private void createBorders() {
        JSeparator separator1 = new JSeparator(JSeparator.VERTICAL);
        int height = Estimator.getLearnedSymbols().size() * listSize + 2 * titleSize;
        separator1.setBounds(getWidth() / 2, (int) (titleSize * 1.5), 5, height);
        add(separator1);
        JSeparator separator2 = new JSeparator(JSeparator.HORIZONTAL);
        separator2.setBounds((int) (getWidth() * 0.2), (int) (titleSize * 2.5), (int) (getWidth() * 0.6), 5);
        add(separator2);
    }

    // Titles the list
    private void createTitle() {
        Font bigFont = new Font("serif", Font.BOLD, titleSize - 5);
        JLabel title = new JLabel("Existing Drawings", SwingConstants.CENTER);
        title.setFont(bigFont);
        title.setBounds(0, 0, getWidth(), titleSize);
        this.add(title);
        title.setBackground(Color.RED);
    }

    // Adds the headers
    private void createHeaders() {
        Font font = new Font("serif", Font.BOLD, listSize - 5);

        JLabel name = new JLabel("Drawing Name", SwingConstants.RIGHT);
        name.setFont(font);
        name.setBounds(0, (int) (1.5 * titleSize), getWidth() / 2 - 10, listSize);
        this.add(name);

        JLabel count = new JLabel("# of Drawings", SwingConstants.LEFT);
        count.setFont(font);
        count.setBounds(getWidth() / 2 + 10, (int) (1.5 * titleSize), getWidth() / 2 - 10, listSize);
        this.add(count);
    }

    // Adds each drawing in the Estimator
    private void createList() {
        ArrayList<LearnedSymbol> symbols = Estimator.getLearnedSymbols();
        Font font = new Font("serif", Font.PLAIN, listSize - 5);
        for (int i = 0; i < symbols.size(); i++) {
            LearnedSymbol ls = symbols.get(i);
            JLabel name = new JLabel(ls.getName(), SwingConstants.RIGHT);
            name.setFont(font);
            name.setBounds(0, i * listSize + 3 * titleSize, getWidth() / 2 - 10, listSize);
            this.add(name);

            JLabel count = new JLabel("" + ls.getSize(), SwingConstants.LEFT);
            count.setFont(font);
            count.setBounds(getWidth() / 2 + 10, i * listSize + 3 * titleSize, getWidth() / 2 - 10, listSize);
            this.add(count);
        }
    }
}
