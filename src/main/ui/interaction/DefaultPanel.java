package ui.interaction;

import model.Drawing;
import model.InputData;
import ui.StateManager;

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
        JButton b = createButton(name, x, y, width);
        b.addActionListener(e -> {
            userInput = input;
            synchronized (sleeper) {
                sleeper.notify();
            }
        });
    }

    protected void createButton(String name, DrawingArea input, int x, int y, int width) {
        JButton b = createButton(name, x, y, width);
        b.addActionListener(e -> {
            Drawing d = new Drawing(input.getState());
            userInput = new InputData(InputData.DRAWING, d);
            synchronized (sleeper) {
                sleeper.notify();
            }
        });
    }

    protected void createButton(String name, JTextField input, int x, int y, int width) {
        JButton b = createButton(name, x, y, width);
        b.addActionListener(e -> {
            if (input.getText().length() > 0) {
                userInput = new InputData(InputData.NAME, input.getText());
                synchronized (sleeper) {
                    sleeper.notify();
                }
            }
        });
    }

    protected JButton createButton(String name, int x, int y, int width) {
        JButton b = new JButton(name);
        this.add(b);
        x -= width / 2;
        x = Math.max(0, x);
        x = Math.min(windowWidth - width, x);
        b.setBounds(x, y, width, componentHeight);
        b.setBorder(null);
        return b;
    }


    protected void createTextField(int y) {
        createTextField(windowWidth / 2, y);
    }

    protected void createTextField(int x, int y) {
        JTextField textField = new JTextField();
        int textWidth = 100;
        x = Math.max(0, x - textWidth / 2);
        textField.setBounds(x, y, textWidth, componentHeight);
        createButton("enter", textField, x + textWidth + 20, y, 40);
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
        DrawingArea drawingArea = new DrawingArea();
        int width = getWidth();
        int height = getHeight() - componentHeight * 2;
        int x = 0;
        if (height < width) {
            x = (width - height) / 2;
            width = height;
        }
        drawingArea.setBounds(x, componentHeight * 2, width, height);
        this.add(drawingArea);
        InputData in = new InputData(InputData.NAVIGATION, StateManager.ADD_TO_EXISTING);
        createButton("Clear", in, this.getWidth() / 2 + 320, this.componentHeight * 2 / 3, 50);
        createButton("Submit", drawingArea, this.getWidth() / 2 + 380, this.componentHeight * 2 / 3, 50);
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