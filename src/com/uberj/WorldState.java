package com.uberj;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by uberj on 11/24/14.
 */

public class WorldState {
    private Cell [][] cells;
    private int columns;
    private int rows;
    private World world;

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

    public World getWorld() {
        return world;
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

    public int rows() {
        return rows;
    }

    public int getRows() {
        return rows;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public int getColumns() {
        return columns;
    }

    public void setWorldStateForCell(int column, int row, int value) {
        this.cells[row][column].setState(value);
    }

    public int [][] getWorldStateIntMap() {
        int [][] encodedWorld = new int[this.rows][this.columns];
        for (int j = 0; j < this.rows; j++) {
            for (int i = 0; i < this.columns; i++) {
                encodedWorld[j][i] = this.cells[j][i].getState();
            }
        }
        return encodedWorld;
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

    public void setWorldStateFromIntMap(int [][] worldState) {
        for (int j = 0; j < this.rows; j++) {
            for (int i = 0; i < this.columns; i++) {
                this.cells[j][i].setState(worldState[j][i]);
            }
        }
    }

    public Cell getCell(Point p){
        if (!inBounds(p)) {
            return null;
        }
        return this.cells[(int) p.getY()][(int) p.getX()];
    }
}

