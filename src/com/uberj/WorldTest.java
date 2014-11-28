package com.uberj;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

// TODO: assertEqual(expected, actual) <--- need to update tests to follow this convention. its swapped right now.
public class WorldTest {
    // There *has* to be a stdlib function that does this....
    private static boolean multiArrayIsEqual(int [][] world1, int [][] world2) {
        if (world1.length != world2.length) {
            fail("world1 has a different number of rows than world2");
        }
        if (world1.length == 0) {
            return true;
        }
        if (world1[0].length != world2[0].length) {
            fail("world1 has a different dimension than world2");
        }
        for (int j = 0; j < world1.length; j++) {
            for (int i = 0; i < world1[0].length; i++) {
                if (world1[j][i] != world2[j][i]) {
                    fail("world1 has a different values than world2");
                }
            }
        }
        return true;
    }

    @Test
    public void testWorldSetup() {
        IntWorld world = new IntWorld(5, 2);
        int [][] expectedState = new int [][]{
            {0,0,0,0,0},
            {0,0,0,0,0}
        };
        multiArrayIsEqual(world.getWorldState(), expectedState);

        IntWorld world2 = new IntWorld(2, 5);
        int [][] expectedState2 = new int [][]{
                {0,0},
                {0,0},
                {0,0},
                {0,0},
                {0,0},
        };
        multiArrayIsEqual(world2.getWorldState(), expectedState2);
    }

    @Test
    public void testWorldSetupSeeded() {
        int [][] identityState = new int [][]{
                {1,0,0,0,0},
                {0,1,0,0,0},
                {0,0,1,0,0},
                {0,0,0,1,0},
                {0,0,0,0,1}
        };
        IntWorld world = new IntWorld(5, 5);
        world.seedState(identityState);
        world.getCurWorldState().printWorldState();
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(world.getCurWorldState().getCells()[i][i].getState(), 1);
        }
    }
}