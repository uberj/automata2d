package com.uberj;

import java.awt.*;

/**
 * Created by uberj on 11/30/14.
 */

abstract class PanelCell {
    private int width;
    private int height;
    private int padding = 1;

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

    public abstract PanelWorld getWorld();

    public abstract Point getPosition();

    public static Point calculatePanelPosition(PanelCell c) {
        /*
        Take a panel cell and figure out where it should have its origin on a Panel
         */
        int xPos = c.getOriginX() + c.getWidth() * (int) c.getPosition().getX();
        int yPos = c.getOriginY() + c.getHeight() * (int) c.getPosition().getY();
        return new Point(xPos, yPos); // Maybe we should pass in a reference to a Point? Might make for better memory consumption.
    }

    public static Cell calculateCellPosition(Point p, WorldState worldState) {
        /*
        Take a pixel coordinate p and get the Cell that has been drawn there.
         */
        int xPos = (int) (p.getX() - worldState.getWorld().getPanelWorld().getOriginX()) / worldState.getWorld().getCellWidth();
        int yPos = (int) (p.getY() - worldState.getWorld().getPanelWorld().getOriginY()) / worldState.getWorld().getCellHeight();
        return worldState.getCell(new Point(xPos, yPos));
    }
}
