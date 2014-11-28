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

    public Point getPosition() {
        return position;
    }

    private Point position;
    private WorldState worldState;

    public Cell(WorldState worldState, Point position, int iState){
        this.worldState = worldState;
        this.position = position;
        this.state = iState;
    }

    public Neighbors getNeighbors() {
        return new Neighbors(this);
    }

    public Iterator<Point> getNeighborPoints() {
        Neighbors n = new Neighbors(this);
        return n.pointIterator();
    }
    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public WorldState getWorldState() { return worldState; }
}
