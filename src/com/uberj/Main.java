package com.uberj;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import com.uberj.World;

public class Main {

    public static void main(String[] args) {
        World world = new World(5, 2);
        world.simulate();
        /*
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        */
    }

    private static void createAndShowGUI() {
        System.out.println( "Created GUI on EDT? "+ SwingUtilities.isEventDispatchThread() );
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel());
        f.setSize(500, 500);
        f.setVisible(true);
    }
}

class MyPanel extends JPanel {

    RedSquare redSquare = new RedSquare();

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(), e.getY());
            }
        }));

        addMouseMotionListener((new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveSquare(e.getX(), e.getY());
            }
        }));
    }

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

    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawString("Hi, this is a custom panel", 10, 20);
        redSquare.paintSquare(g);
    }
}

class RedSquare {

    private int xPos = 50;
    private int yPos = 50;
    private int width = 20;
    private int height = 20;

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
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

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void paintSquare(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(xPos, yPos, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos, yPos, width, height);
    }
}