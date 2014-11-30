package com.uberj;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by uberj on 11/24/14.
 */

public class WorldState {
    private World world;

    public World getWorld() {
        return world;
    }

    private int rows;

    public int getRows() {
        return rows;
    }

    private Cell [][] cells;

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    private int columns;

    public int getColumns() {
        return columns;
    }

    public WorldState(World world, int iColumns, int iRows) {
        this.world = world;
        this.columns = iColumns;
        this.rows = iRows;
        this.cells = new Cell[iRows][iColumns];
        for (int row = 0; row < iRows; row++) {
            for (int col = 0; col < iColumns; col++) {
                this.cells[row][col] = new Cell(this, new Point(col, row), 0);
            }
        }
    }

    public void printWorldState() {
        // Rows should *always* be the outer counter (usually i)
        for (int i = 0; i < this.columns; i++) {
            System.out.print("---");
        }
        System.out.print("\n");
        for (int j = 0; j < this.rows; j++) {
            for (int i = 0; i < this.columns; i++) {
                System.out.format(" %d ", this.cells[j][i].getState());
            }
            System.out.print("\n");
        }
    }

    public void setWorldStateForCell(int column, int row, int value) {
        this.cells[row][column].setState(value);
    }

    public boolean inBounds(Point point) {
        if (point.getX() < 0 || point.getX() >= this.columns) {
            return false;
        }
        if (point.getY() < 0 || point.getY() >= this.rows) {
            return false;
        }
        return true;
    }

    public Cell getCell(Point p){
        if (!inBounds(p)) {
            return null;
        }
        return this.cells[(int) p.getY()][(int) p.getX()];
    }
}

