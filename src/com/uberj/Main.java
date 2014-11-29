package com.uberj;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.uberj.World;

public class Main {

    public static void main(String[] args) {
        //World world = new World(5, 2);
        //world.simulate();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println( "Created GUI on EDT? "+ SwingUtilities.isEventDispatchThread() );
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final GridPanel panel = new GridPanel(500, 500);
        f.add(panel);
        f.setSize(500, 500);
        f.setVisible(true);
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                System.out.println("You clicked the button");
                panel.getWorld().simulateTimeAmount(1);
                panel.repaint();
            }
        });
        panel.add(nextButton, BorderLayout.AFTER_LINE_ENDS);
    }
}

class GridPanel extends JPanel {

    private World world;

    public World getWorld() {
        return world;
    }

    public GridPanel(int width, int height) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        world =  new World(new int [][]{
            {1,0,0,0,1,0,1,0,1,0},
            {1,1,0,1,0,0,1,1,1,0},
            {0,0,1,0,0,0,1,0,0,1},
            {0,1,0,1,0,0,0,1,1,0},
            {0,0,0,0,1,0,1,1,1,1}
        }, 50, 50);

        addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                repaint();
                Cell c = world.getCellFromPanelPoint(new Point(e.getX(), e.getY()));
                c.setState(1);
                System.out.format("Clicked on cell %s, %s\n", c.getPosition().getX(), c.getPosition().getY());
            }
        }));


        /*
        addMouseMotionListener((new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveSquare(e.getX(), e.getY());
            }
        }));
        */
    }

    /*
    private void moveSquare(int x, int y) {
        int OFFSET = 1;
        final int CURR_X = redSquare.getxPos();
        final int CURR_Y = redSquare.getyPos();
        final int CURR_W = redSquare.getWidth();
        final int CURR_H = redSquare.getHeight();
        if ((CURR_X != x) || (CURR_Y != y)) {
            repaint(CURR_X, CURR_Y, CURR_W + OFFSET, CURR_H + OFFSET);
            repaint();
            redSquare.setxPos(x);
            redSquare.setyPos(y);
            repaint(redSquare.getxPos(), redSquare.getyPos(),
                    redSquare.getWidth() + OFFSET, redSquare.getHeight() + OFFSET);
        }
    }
    */

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
    private int originY = 80;

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
    private int width = 10;
    private int height = 10;
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