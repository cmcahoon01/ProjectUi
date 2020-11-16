package ui.interaction;

import model.InputData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DefaultPanel extends JPanel {
    GraphicInterface parent;
    private int windowWidth;
    private int windowHeight;
    private InputData userInput;
    private final Object sleeper;

    public final int componentHeight = 25;
    public final int componentWidth = 250;

    public DefaultPanel(GraphicInterface parent) {
        this.parent = parent;
        sleeper = new Object();
        windowWidth = parent.getWidth();
        windowHeight = parent.getHeight();
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = this.getSize();
        windowWidth = d.width + 16;
        windowHeight = d.height + 39;
    }

    // Creates a button and adds it to the panel
    protected void createButton(String name, InputData input, int y) {
        createButton(name, input, windowWidth / 2, y);
    }

    // Creates a button and adds it to the panel
    protected void createButton(String name, InputData input, int x, int y) {
        createButton(name, input, x, y, componentWidth);
    }


    protected void createButton(String name, InputData input, int x, int y, int width) {

        JButton b = new JButton(name);
        b.addActionListener(e -> {
            userInput = input;
            synchronized (sleeper) {
                sleeper.notify();
            }
        });
        this.add(b);
        x -= width / 2;
        x = Math.max(0, x);
        x = Math.min(windowWidth - width, x);
        b.setBounds(x, y, width, componentHeight);
        b.setBorder(null);
    }

    protected void createTextField(int y) {
        createTextField(windowWidth / 2, y);
    }

    protected void createTextField(int x, int y) {
        JTextField textField = new JTextField();
        int textWidth = 100;
        x = Math.max(0, x - textWidth / 2);
        textField.setBounds(x, y, textWidth, componentHeight);
        createButton("enter", new InputData(InputData.NAME, textField.getText()), x + textWidth + 20, y, 40);
        this.add(textField);
        parent.setVisible(false);
        parent.setVisible(true);
    }

    protected void createTitle(String name) {
        Font bigFont = new Font("serif", Font.BOLD, 30);
        JLabel title = new JLabel(name, SwingConstants.CENTER);
        title.setFont(bigFont);
        int width = windowWidth;
        title.setBounds(0, 0, width, componentHeight * 2);
        this.add(title);
    }

    protected void createLabel(String name, int y) {
        createLabel(name, windowWidth / 2, y);
    }

    protected void createLabel(String name, int x, int y) {
        JLabel text = new JLabel(name, SwingConstants.CENTER);
        int width = windowWidth;
        text.setBounds(x - width / 2, y, width, componentHeight);
        this.add(text);
    }

    protected void createDrawingArea() {
        JPanel drawingArea = new DrawingArea();
        int width = getWidth();
        int height = getHeight() - componentHeight * 2;
        int x = 0;
        if (height < width) {
            x = (width - height) / 2;
            width = height;
        }
        drawingArea.setBounds(x, componentHeight * 2, width, height);
        this.add(drawingArea);
    }


    public InputData getInput() {
        try {
            synchronized (sleeper) {
                sleeper.wait();
            }
        } catch (InterruptedException e) {
            // pass
        }
        return userInput;
    }


}