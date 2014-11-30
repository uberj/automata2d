package com.uberj;


import java.awt.*;
import java.util.Iterator;

public class Cell {
    /*
    Right now a Cell has a state that is an int. Eventually it would be cool to have the state by something else,
    like a color. Its probably possible to make state its own type an encapsulate its data that way.

    Note: A point at Point(X, Y) is going to be at cells[y][x]
     */

    private int state;

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private Point position;

    public Point getPosition() {
        return position;
    }

    private WorldState worldState;

    public WorldState getWorldState() {
        return worldState;
    }

    private PanelCell panelCell;

    public PanelCell getPanelCell() {
        return panelCell;
    }

    public Cell(WorldState worldState, Point position, int iState){
        this.panelCell = new APanelCell();
        this.worldState = worldState;
        this.position = position;
        this.state = iState;
    }

    public PanelWorld getWorld() {
        return this.worldState.getWorld().getPanelWorld();
    }

    private class APanelCell extends PanelCell {
        @Override
        public PanelWorld getWorld() {
            return Cell.this.getWorld();
        }

        @Override
        public Point getPosition() {
            return Cell.this.getPosition();
        }

        @Override
        public void paintCell(Graphics g) {
            Point panelPoint = calculatePanelPosition(this);
            if (Cell.this.state == 0) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.RED);
            }
            g.fillRect(
                    (int) panelPoint.getX(), (int) panelPoint.getY(),
                    this.getWidth() - this.getPadding(), this.getHeight() - this.getPadding()
            );
            g.setColor(Color.BLACK);
            g.drawRect(
                    (int) panelPoint.getX(), (int) panelPoint.getY(),
                    this.getWidth() - this.getPadding(), this.getHeight() - this.getPadding()
            );

        }
    }
    /*
    public void paintCell(Graphics g) {
        Point panelPoint = calculatePanelPosition(this);
        if (this.state == 0) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(
                (int) panelPoint.getX(), (int) panelPoint.getY(),
                this.getWidth() - this.getPadding(), this.getHeight() - this.getPadding()
        );
        g.setColor(Color.BLACK);
        g.drawRect(
                (int) panelPoint.getX(), (int) panelPoint.getY(),
                this.getWidth() - this.getPadding(), this.getHeight() - this.getPadding()
        );
    }
    */

    public Neighbors getNeighbors() {
        return new Neighbors(this);
    }

    public Iterator<Point> getNeighborPoints() {
        Neighbors n = new Neighbors(this);
        return n.pointIterator();
    }
}
