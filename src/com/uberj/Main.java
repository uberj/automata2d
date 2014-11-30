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
        JFrame f = new JFrame("Game of life");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight() - 10;
        final GridPanel panel = new GridPanel(screenWidth, screenHeight);
        f.add(panel);
        f.setSize(screenWidth, screenHeight);
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

    public GridPanel(int screenWidth, int screenHeight) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        world =  new World(20, screenWidth, screenHeight);
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