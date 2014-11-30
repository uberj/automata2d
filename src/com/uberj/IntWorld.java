package com.uberj;

/**
 * Created by uberj on 11/24/14.
 */
public class IntWorld extends World {
    /*
      A convenient test class for treating the WorldState like it were a 2d array of integers.
     */
    public IntWorld(int initialCols, int initialRows){
        super(1, 1, initialCols, initialRows);
    }

    private class GetIntState implements CellOperation {
        int [][] state;

        public GetIntState(int [][] state) {
            this.state = state;
        }

        public void op(Cell c) {
            int col = (int) c.getPosition().getX();
            int row = (int) c.getPosition().getY();
            this.state[row][col] = c.getState();
        }
    }

    public int [][] getWorldState() {
        WorldState curState = this.getCurWorldState();
        int [][] encodedWorld = new int[curState.getRows()][curState.getColumns()];
        mapOverCells(new GetIntState(encodedWorld));
        return encodedWorld;
    }

    public boolean equals(IntWorld world) {
        // Lets assume the world state is a rectangular
        if (world.getWorldState().length != this.getWorldState().length) {
            return false;
        }
        if (world.getWorldState().length == 0) {
            // Both are empty thus are equal
            return true;
        }
        if (world.getWorldState()[0].length != this.getWorldState()[0].length) {
            return false;
        }
        // We have proved we are dealing with worlds of equal dimensions, now compare contents
        for (int j = 0; j < this.getWorldState().length; j++) {
            for (int i = 0; i < this.getWorldState()[0].length; i++) {
                if (world.getWorldState()[j][i] != this.getWorldState()[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }
}
