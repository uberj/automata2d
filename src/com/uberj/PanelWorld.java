package com.uberj;

import java.awt.*;

abstract class PanelWorld {

    private int originX; // Defaults to 0
    private int originY; // Defaults to 0

    public int getOriginY() {
        return originY;
    }

    public int getOriginX() {
        return originX;
    }

    public abstract void paintCells(Graphics g);
    public abstract Cell getCellFromPanelPoint(Point p);
}
