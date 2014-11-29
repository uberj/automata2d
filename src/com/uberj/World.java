package com.uberj;

import java.awt.*;

public class World extends PanelWorld {
    private static int MAX_TIME = 2;

    public WorldState getCurWorldState() {
        return curWorldState;
    }

    public void paintCells(Graphics g) {
        for (int row=0; row < curWorldState.rows(); row++) {
            for (int column=0; column < curWorldState.getColumns(); column++) {
                curWorldState.getCells()[row][column].paintCell(g);
            }
        }
    };

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

    public World(int [][] seed, int cellWidth, int cellHeight){
        this(seed);
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        CellOperation cop = new SetWidthAndHeight(cellWidth, cellHeight);
        mapOverCells(cop, curWorldState);
        mapOverCells(cop, nextWorldState);
    }

    public World(int [][] seed){
        if (seed.length == 0) {
            // TODO. Oh how I do hate exceptions in Java. Maybe there is a way for my editor to generate the boiler
            // plate code
        }
        this.curWorldState = new WorldState(this, seed[0].length, seed.length);
        this.nextWorldState = new WorldState(this, seed[0].length, seed.length);
        this.seedState(seed);
    }

    private interface CellOperation {
        public void op(Cell c);
    }

    public void mapOverCells(CellOperation op) {
        mapOverCells(op, curWorldState);
    }

    public void mapOverCells(CellOperation op, WorldState state){
        for (int row = 0; row < this.getCurWorldState().getRows(); row++) {
            for (int col = 0; col < this.getCurWorldState().getColumns(); col++) {
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

    public World(int initialCols, int initialRows){
        this.curWorldState = new WorldState(this, initialCols, initialRows);
        this.nextWorldState = new WorldState(this, initialCols, initialRows);
    }

    public static void advanceWorld(WorldState cur, WorldState next) {
        // Threading could happen here
        // TODO: implement iterator on WorldState
        //cur.printWorldState();
        for (int row=0; row < cur.rows(); row++) {
            for (int column=0; column < cur.getColumns(); column++) {
                next.setWorldStateForCell(column, row, applyRule(cur.getCells()[row][column], column, row));
            }
        }
        //next.printWorldState();
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
            System.out.format("Running time %d\n", i);
            advanceWorld(this.curWorldState, this.nextWorldState);
            swapWorldState();
        }
    }

    public void simulate() {
        simulateTimeAmount(MAX_TIME);
    }

    public void swapWorldState() {
        // TODO: this.{cur,next}WorldState are a references, right?
        WorldState tmpWorldStateRef = this.curWorldState;
        this.curWorldState = this.nextWorldState;
        this.nextWorldState = tmpWorldStateRef;
    }

    public Cell getCell(Point p)  {
        // A helper function to quickly look up a cell in the currentWorldState
        return this.curWorldState.getCell(p);
    }

    public Cell getCellFromPanelPoint(Point p)  {
        // Find the cell corresponding to the pixle coordinate p
        return Cell.calculateCellPosition(p, curWorldState);
    }
}
