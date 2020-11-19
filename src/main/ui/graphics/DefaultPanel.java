package ui.graphics;

import model.Drawing;
import model.InputData;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class DefaultPanel extends JPanel {
    GraphicInterface parent;
    private int windowWidth;
    private int windowHeight;
    private InputData userInput;
    private final Object sleeper;

    public final int componentHeight = 30;
    public final int buttonGap = 5;

    public final int componentWidth = 250;

    // Creates a new JPanel
    public DefaultPanel(GraphicInterface parent) {
        this.parent = parent;
        sleeper = new Object();
        windowWidth = parent.getWidth();
        windowHeight = parent.getHeight();
        setLayout(null);
    }

    // Uptades the window Size
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

    // Creates a button and adds it to the panel
    protected void createButton(String name, InputData input, int x, int y, int width) {
        JButton b = createButton(name, x, y, width);
        b.addActionListener(e -> {
            userInput = input;
            synchronized (sleeper) {
                sleeper.notify();
            }
        });
    }

    // Creates a button and adds it to the panel
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

    // Creates a button and adds it to the panel
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

    // Creates a button and adds it to the panel
    protected JButton createButton(String name, int x, int y, int width) {
        JButton b = new JButton(name);
        this.add(b);
        x -= width / 2;
        x = Math.max(0, x);
        x = Math.min(windowWidth - width, x);
        b.setBounds(x, y, width, componentHeight - buttonGap);
        b.setBorder(null);
        return b;
    }

    // Creates a text panel and adds it to the panel
    protected void createTextField(int y) {
        createTextField(windowWidth / 2, y);
    }

    // Creates a text panel and adds it to the panel
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
        createTitle(name, 0.4);
    }

    // Adds a title to the panel
    protected void createTitle(String name, double windowPercentage) {
        Font bigFont = new Font("serif", Font.BOLD, 30);
        String htmlStyle = "<html><div style = 'text-align: center;'>" + name + "</div></html>";
        JLabel title = new JLabel(htmlStyle, SwingConstants.CENTER);
        title.setFont(bigFont);
        int width = (int) (windowWidth * windowPercentage);
        int y = -componentHeight * 2 / 3;
        if (name.length() > 40) { //40 is an estimate
            y = -componentHeight / 4;
        }
        title.setBounds((windowWidth - width) / 2, y, width, componentHeight * 3);
        this.add(title);
    }

    // Adds text to the panel
    protected void createLabel(String name, int x, int y) {
        JLabel text = new JLabel(name, SwingConstants.CENTER);
        int width = componentWidth;
        text.setBounds(x - width / 2, y, width, componentHeight);
        this.add(text);
    }

    // Creates a new Drawing Area for the panel
    protected void createDrawingArea(int page) {
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
        InputData in = new InputData(InputData.NAVIGATION, page);
        createButton("Clear", in, x + width + 100, getHeight() / 2 - componentHeight / 2, 100);
        createButton("Add drawing", drawingArea, x + width + 100, getHeight() / 2 + componentHeight / 2, 100);
    }

    // Listens for button presses
    public InputData getInput() {
        try {
            synchronized (sleeper) {
                sleeper.wait();
            }
        } catch (InterruptedException e) {
            // pass
        }
        Runnable runnable = this::playNote;
        Thread t = new Thread(runnable);
        t.start();
        return userInput;
    }

    // plays a click sound
    private void playNote() {
        try {
            File f = new File("src/main/ui/graphics/sounds/mouseClick.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double gain = 0.25;
            float d = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(d);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }


    // Shows the list of all existing drawings
    public void showDrawings() {
        double percentDown = 0.05;
        ListArea listArea = new ListArea();
        int width = (int) (getWidth() * 0.21);
        int height = (int) (getHeight() * (1 - percentDown));
        int x = getWidth() - width;
        listArea.setBounds(x, (int) (getHeight() * percentDown), width, height);
        this.add(listArea);
        listArea.draw();
    }

    // Creates a dropdown
    protected void createDropDown(String name, ArrayList<String> components, int y) {
        String[] comps = new String[components.size()];
        for (int i = 0; i < components.size(); i++) {
            comps[i] = components.get(i);
        }
        JComboBox b = new JComboBox<>(comps);
        b.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String selectedItem = (String) cb.getSelectedItem();
            userInput = new InputData(InputData.NAME, selectedItem);
            synchronized (sleeper) {
                sleeper.notify();
            }
        });
        this.add(b);
        b.setBounds(windowWidth / 2, y, componentWidth / 2, componentHeight - buttonGap);
        JLabel text = new JLabel(name, SwingConstants.CENTER);
        text.setBounds(windowWidth / 2 - componentWidth / 2, y, componentWidth / 2, componentHeight);
        this.add(text);
    }
}