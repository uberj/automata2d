package com.uberj;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.Iterator;

import static org.junit.Assert.*;

public class CellTest {
    @Test
    public void testNeighborIterator() {
        Point point;
        Point [] expectedPoints = {
                new Point(2, 2),
                new Point(1, 2),
                new Point(0, 2),
                new Point(2, 1),
                new Point(0, 1),
                new Point(2, 0),
                new Point(1, 0),
                new Point(0, 0),
        };
        World world = new World(1, 1, 3, 3);
        // Get the cell right in the middle
        Iterator<Point> neighbors = world.getCurWorldState().getCells()[1][1].getNeighborPoints();
        for(int i = 0; i < expectedPoints.length; i++) {
            Assert.assertTrue(neighbors.hasNext());
            point = (Point) neighbors.next();
            Assert.assertEquals(expectedPoints[i], point);
        }
        Assert.assertFalse(neighbors.hasNext());
    }

    @Test
    public void testNeighborIteratorLeftUpperBoundary() {
        Point point;
        Point [] expectedPoints = {
                new Point(2, 1),
                new Point(1, 1),
                new Point(1, 0),
        };
        World world = new World(1, 1, 3, 3);
        // Get the cell right in the middle
        Iterator<Point> neighbors = world.getCurWorldState().getCells()[0][2].getNeighborPoints();
        for(int i = 0; i < expectedPoints.length; i++) {
            Assert.assertTrue(neighbors.hasNext());
            point = (Point) neighbors.next();
            Assert.assertEquals(expectedPoints[i], point);
        }
        Assert.assertFalse(neighbors.hasNext());
    }

    @Test
    public void testNeighborIteratorLeftLowerBoundary() {
        Point point;
        Point [] expectedPoints = {
                new Point(1, 9),
                new Point(1, 8),
                new Point(0, 8),
        };
        World world = new World(1, 1, 7, 10);
        // Get the cell right in the middle
        Iterator<Point> neighbors = world.getCurWorldState().getCells()[9][0].getNeighborPoints();
        for(int i = 0; i < expectedPoints.length; i++) {
            Assert.assertTrue(neighbors.hasNext());
            point = (Point) neighbors.next();
            Assert.assertEquals(expectedPoints[i], point);
        }
        Assert.assertFalse(neighbors.hasNext());
    }

    @Test
    public void testNeighborIteratorCenterish() {
        Point point;
        Point [] expectedPoints = {
                new Point(5, 10),
                new Point(4, 10),
                new Point(3, 10),
                new Point(5, 9),
                new Point(3, 9),
                new Point(5, 8),
                new Point(4, 8),
                new Point(3, 8),
        };
        World world = new World(1, 1, 19, 14);
        // Get the cell right in the middle
        Iterator<Point> neighbors = world.getCurWorldState().getCells()[9][4].getNeighborPoints();
        for(int i = 0; i < expectedPoints.length; i++) {
            Assert.assertTrue(neighbors.hasNext());
            point = (Point) neighbors.next();
            Assert.assertEquals(expectedPoints[i], point);
        }
        Assert.assertFalse(neighbors.hasNext());
    }

    @Test
    public void testNeighbors() {
        Point point;
        Point [] expectedPoints = {
                new Point(2, 2),
                new Point(1, 2),
                new Point(0, 2),
                new Point(2, 1),
                new Point(0, 1),
                new Point(2, 0),
                new Point(1, 0),
                new Point(0, 0),
        };
        World world = new World(1, 1, 3, 3);
        // Get the cell right in the middle
        Cell mainCell = world.getCurWorldState().getCells()[1][1];
        for(Cell c: mainCell.getNeighbors()) {
            System.out.println(c);
        }
    }
}
