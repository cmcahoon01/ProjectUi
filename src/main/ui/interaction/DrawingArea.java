package ui.interaction;

import ui.personal.GuiTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DrawingArea extends JPanel {
    private static final int rows = 20;
    private static final int columns = 20;

    private final int buffer = 20 / Math.min(rows, columns);
    private final ArrayList<ArrayList<Double>> state;

    private String lastPressed;
    private boolean erasing;

    public DrawingArea() {
        super();
        state = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            state.add(new ArrayList<>());
            for (int j = 0; j < columns; j++) {
                state.get(i).add(0.);
            }
        }
        startListener();
        lastPressed = "none";
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension d = this.getSize();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, d.width, d.height);
        int cellWidth = d.width / columns;
        int cellHeight = d.height / rows;
        int size = Math.min(cellHeight, cellWidth);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int val = (int) (255 - state.get(i).get(j) * 255);
                val = (Math.max(0, Math.min(210, val)));
                g.setColor(new Color(val, val, val));
                g.fillRect(j * size + buffer / 2, i * size + buffer / 2,
                        size - buffer, size - buffer);
            }
        }
    }

    private void startListener() {
        DrawingMouseListener dml = new DrawingMouseListener();
        addMouseListener(dml);
        addMouseMotionListener(dml);
    }


    private void click(MouseEvent e, boolean pressed) {
        Point point = e.getPoint();
        int[] cellIndices = getCell(point);
        if (!lastPressed.equals(cellIndices[0] + " " + cellIndices[1])) {
            lastPressed = cellIndices[0] + " " + cellIndices[1];
            if (pressed) {
                erasing = state.get(cellIndices[1]).get(cellIndices[0]) == 1;
            }
            if (!erasing) {
                state.get(cellIndices[1]).set(cellIndices[0], 1.);
            } else {
                state.get(cellIndices[1]).set(cellIndices[0], 0.);
            }
            revalidate();
            repaint();
        }
    }

    private int[] getCell(Point p) {
        int x = Math.min(p.x * columns / getWidth(), columns - 1);
        int y = Math.min(p.y * rows / getHeight(), rows - 1);
        return new int[]{x, y};
    }

    public ArrayList<ArrayList<Double>> getState() {
        return state;
    }

    private class DrawingMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            click(e, true);
        }

        public void mouseDragged(MouseEvent e) {
            click(e, false);
        }
    }
}