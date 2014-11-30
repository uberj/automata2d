package com.uberj;

import java.awt.*;

public class World {
    private static int MAX_TIME = 2;

    private final APanelWorld panelWorld = new APanelWorld();

    public PanelWorld getPanelWorld() {
        return panelWorld;
    }

    public WorldState getCurWorldState() {
        return curWorldState;
    }

    private WorldState curWorldState;
    private WorldState nextWorldState;
    private int cellWidth, cellHeight;

    public int getCellHeight() {
        return cellHeight;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public class SetWidthAndHeight implements CellOperation {
        int cellWidth;
        int cellHeight;

        public SetWidthAndHeight(int width, int height) {
            cellHeight = height;
            cellWidth = width;
        }

        public void op(Cell c) {
            c.setHeight(cellHeight);
            c.setWidth(cellWidth);
        }
    }

    public World(int [][] seed){
        if (seed.length == 0) {
            // TODO. Oh how I do hate exceptions in Java. Maybe there is a way for my editor to generate the boiler
            // plate code
        }
        World.this.curWorldState = new WorldState(World.this, seed[0].length, seed.length);
        World.this.nextWorldState = new WorldState(World.this, seed[0].length, seed.length);
        World.this.seedState(seed);
    }

    public void mapOverCells(CellOperation op) {
        mapOverCells(op, curWorldState);
    }

    public void mapOverCells(CellOperation op, WorldState state){
        for (int row = 0; row < World.this.getCurWorldState().getRows(); row++) {
            for (int col = 0; col < World.this.getCurWorldState().getColumns(); col++) {
                op.op(state.getCells()[row][col]);
            }
        }
    }

    public class Seeder implements CellOperation {
        int [][] seedState;

        public Seeder(int [][] seed) { seedState = seed; }

        public void op(Cell c) {
            int col = (int) c.getPosition().getX();
            int row = (int) c.getPosition().getY();
            c.setState(seedState[row][col]);
        }
    }

    public void seedState(int [][] seedState){
        mapOverCells(new Seeder(seedState));
    }

    public World(int cellWidth, int cellHeight, int initialCols, int initialRows){
        World.this.cellWidth = cellWidth;
        World.this.cellHeight = cellHeight;

        World.this.curWorldState = new WorldState(World.this, initialCols, initialRows);
        World.this.nextWorldState = new WorldState(World.this, initialCols, initialRows);

        CellOperation cop = new SetWidthAndHeight(cellWidth, cellHeight);
        mapOverCells(cop, curWorldState);
        mapOverCells(cop, nextWorldState);
    }

    public World(int cellWidthHeight, Dimension worldDimension){
        World.this.cellWidth = cellWidthHeight;
        World.this.cellHeight = cellWidthHeight;

        int initialCols = (int) worldDimension.getWidth() / cellWidthHeight;
        int initialRows = (int) worldDimension.getHeight() / cellWidthHeight;

        World.this.curWorldState = new WorldState(World.this, initialCols, initialRows);
        World.this.nextWorldState = new WorldState(World.this, initialCols, initialRows);

        CellOperation cop = new SetWidthAndHeight(cellWidth, cellHeight);
        mapOverCells(cop, curWorldState);
        mapOverCells(cop, nextWorldState);
    }

    public static void advanceWorld(WorldState cur, WorldState next) {
        // Threading could happen here
        // TODO: implement iterator on WorldState
        for (int row=0; row < cur.rows(); row++) {
            for (int column=0; column < cur.getColumns(); column++) {
                next.setWorldStateForCell(column, row, applyRule(cur.getCells()[row][column], column, row));
            }
        }
    }

    public static int applyRule(Cell c, int i, int j) {
        /*
          1 2 3
          4   5
          6 7 8
         */
        int ALIVE = 1;
        int DEAD = 0;
        int neighborsAlive = 0;
        for (Cell n: c.getNeighbors()) {
            if (n.getState() == 1) {
                neighborsAlive++;
            }
        }
        // See rules at https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
        if (c.getState() == ALIVE) {
            if (neighborsAlive < 2)  {
                // < 2 alive and alive -- dead
                return DEAD;
            }
            if (neighborsAlive == 2 || neighborsAlive == 3) {
                // 2 or 3 and alive -- alive
                return ALIVE;
            }
            return DEAD; // > 3 alive and alive -- dead
        } else { // Cell is DEAD
            if (neighborsAlive == 3) {
                // = 3 alive and dead -- alive
                return ALIVE;
            }
        }
        return DEAD;
    }

    public void simulateTimeAmount(int time) {
        for (int i = 0; i < time; i++) {
            advanceWorld(World.this.curWorldState, World.this.nextWorldState);
            swapWorldState();
        }
    }

    public void simulate() {
        simulateTimeAmount(MAX_TIME);
    }

    public void swapWorldState() {
        // TODO: this.{cur,next}WorldState are a references, right?
        WorldState tmpWorldStateRef = World.this.curWorldState;
        World.this.curWorldState = World.this.nextWorldState;
        World.this.nextWorldState = tmpWorldStateRef;
    }

    public Cell getCell(Point p)  {
        // A helper function to quickly look up a cell in the currentWorldState
        return World.this.curWorldState.getCell(p);
    }

    private class APanelWorld extends PanelWorld {
        public void paintCells(Graphics g) {
            for (int row = 0; row < curWorldState.rows(); row++) {
                for (int column = 0; column < curWorldState.getColumns(); column++) {
                    curWorldState.getCells()[row][column].paintCell(g);
                }
            }
        }

        public Cell getCellFromPanelPoint(Point p)  {
            // Find the cell corresponding to the pixle coordinate p
            return Cell.calculateCellPosition(p, curWorldState);
        }
    }
}
