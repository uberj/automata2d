package com.uberj;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class RuleTest {
    // Copy pasta
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
    public void testLonelyDeath() {
        int [][] identityState = new int [][]{
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,1,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        IntWorld world = new IntWorld(5, 5);
        world.seedState(identityState);
        world.simulateTimeAmount(1);
        Assert.assertEquals(0, world.getCell(new Point(3, 3)).getState());
        world.simulateTimeAmount(1);
        Assert.assertEquals(0, world.getCell(new Point(3, 3)).getState());
    }

    @Test
    public void testMiddle2Alive() {
        int [][] identityState = new int [][]{
                {0,0,0,0,0},
                {0,1,0,1,0},
                {0,0,1,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        IntWorld world = new IntWorld(5, 5);
        world.seedState(identityState);
        world.simulateTimeAmount(1);
        Assert.assertEquals(1, world.getCell(new Point(2, 2)).getState());
        Assert.assertEquals(0, world.getCell(new Point(1, 1)).getState());
        Assert.assertEquals(0, world.getCell(new Point(3, 1)).getState());

        System.out.println("Break now!");
        world.simulateTimeAmount(1);
        Assert.assertEquals(0, world.getCell(new Point(2, 2)).getState());
    }
}
