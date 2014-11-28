package com.uberj;

import java.awt.*;

public class World {
    private static int MAX_TIME = 2;

    public WorldState getCurWorldState() {
        return curWorldState;
    }

    private WorldState curWorldState;
    private WorldState nextWorldState;

    public World(int initialCols, int initialRows){
        this.curWorldState = new WorldState(initialCols, initialRows);
        this.nextWorldState = new WorldState(initialCols, initialRows);
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
        c.getWorldState().printWorldState();
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
        // A helper funciton to quickly look up a cell in the currentWorldState
        return this.curWorldState.getCell(p);
    }
}
