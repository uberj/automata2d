package com.uberj;

import javax.swing.*;
import java.awt.*;

/**
 * Created by uberj on 11/22/14.
 */
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

class MyCanvas extends JComponent {
    public void paint(Graphics g) {
        g.drawRect (10, 10, 200, 200);
    }
}

public class DrawRect {
    public int width, height;
    public DrawRect(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw() {
        JFrame window = new JFrame();
        System.out.print(this.width);
        window.setSize(this.width, this.height);
        window.repaint();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 300, 300);
        window.getContentPane().add(new MyCanvas());
        window.setVisible(true);
    }
}