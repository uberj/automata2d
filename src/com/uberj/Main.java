package com.uberj;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.uberj.World;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setupGUI();
            }
        });
    }

    private static void setupGUI() {
        // Get screen info
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double frameRatio = 2.0/3.0;
        int frameWidth = (int) (screenSize.getWidth() * frameRatio);
        int frameHeight = (int) (screenSize.getHeight() * frameRatio);

        // Give our world some margins
        double worldRatio = 0.8;
        int worldWidth = (int) (frameWidth * worldRatio);
        int worldHeight = (int) (frameHeight * worldRatio);
        Dimension worldDimension = new Dimension(worldWidth, worldHeight);

        // Make our world Panel
        int cellWidthAndHeight = 8;
        final GridPanel panel = new GridPanel(cellWidthAndHeight, worldDimension);
        panel.setPreferredSize(worldDimension);
        panel.setMaximumSize(worldDimension);
        panel.setMinimumSize(worldDimension);

        // Set up a box so we can center our frame
        Box box = new Box(BoxLayout.Y_AXIS);
        box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());

        // Build our frame
        JFrame f = new JFrame("Game of life");
        f.add(box);
        f.setSize(frameWidth, frameHeight);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

class Simulator implements Runnable {
    World world;
    JPanel panel;

    public Simulator(JPanel panel, World world) {
        this.world = world;
        this.panel = panel;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(75);
                world.simulateTimeAmount(1);
                panel.repaint();
            }
        } catch (InterruptedException e) {
            // TODO
        }
    }
}
class GridPanel extends JPanel {

    private World world;

    public World getWorld() {
        return world;
    }

    public GridPanel(int cellWidthAndHeight, Dimension worldDimension) {
        world =  new World(cellWidthAndHeight, worldDimension);
        (new Thread(new Simulator(this, world))).start();

        addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                repaint();
                Cell c = world.getCellFromPanelPoint(new Point(e.getX(), e.getY()));
                if (c != null) {
                    c.setState(1);
                }
            }
        }));

        addMouseMotionListener((new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                repaint();
                Cell c = world.getCellFromPanelPoint(new Point(e.getX(), e.getY()));
                if (c != null) {
                    c.setState(1);
                }
            }
        }));
    }

    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        world.paintCells(g);
    }
}

abstract class PanelWorld {
    private int originX;
    private int originY;

    public int getOriginY() {
        return originY;
    }

    public int getOriginX() {
        return originX;
    }

    public abstract void paintCells(Graphics g);
    public abstract Cell getCellFromPanelPoint(Point p);
}


abstract class PanelCell {
    private int width;
    private int height;
    private int padding = 1;

    public abstract PanelWorld getWorld();

    public int getOriginX() {
        return getWorld().getOriginX();
    }

    public int getOriginY() {
        return getWorld().getOriginY();
    }

    public int getPadding() {
        return padding;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void paintCell(Graphics g);
}