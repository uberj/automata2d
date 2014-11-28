package com.uberj;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * I think I'm doing this wrong. It should figure out a way to use the standard Set implementation
 */
public class Neighbors implements Iterable<Cell> {
    private Cell cell;
    private ArrayList<Cell> cells;

    private class NeighborIterator implements Iterator<Point> {
        /*
          Emit points that are neighbors our the central point. This only implements the 2D case

         */
        private int neighborToVisit = 7;
        private int pointX;
        private int pointY;
        private Point curPoint;

        public NeighborIterator() {
            pointX = (int) Neighbors.this.cell.getPosition().getX();
            pointY = (int) Neighbors.this.cell.getPosition().getY();
            curPoint = new Point();
        }

        @Override
        public boolean hasNext() {
            Point testPoint = new Point();
            int nextNeighbor = neighborToVisit;
            while (nextNeighbor >= 0) {
                testPoint.setLocation(pointX, pointY);
                setNeighborPosition(testPoint, nextNeighbor);
                if (inBounds(testPoint)) {
                    return true;
                }
                nextNeighbor--;
            }
            return false;
        }

        private boolean inBounds(Point point) {
            if (point.getX() < 0 || point.getX() >= Neighbors.this.cell.getWorldState().getColumns()) {
                return false;
            }
            if (point.getY() < 0 || point.getY() >= Neighbors.this.cell.getWorldState().getRows()) {
                return false;
            }
            return true;
        }

        private void setNeighborPosition(Point point, int neighborIndex) {
            switch (neighborIndex) {
                    /*
                        I'm pretty sure there is a grey code here.
                        Probably would more efficient to just count in binary.
                        (0,0)  x ------>
                        y       0 1 2
                        |       3   4
                        |       5 6 7
                        v
                     */
                case 0:
                    point.setLocation(pointX - 1, pointY - 1); // (-1 -1)
                    break;
                case 1:
                    point.setLocation(pointX, pointY - 1);      // (0, -1)
                    break;
                case 2:
                    point.setLocation(pointX + 1, pointY - 1);  // (+1, -1)
                    break;
                case 3:
                    point.setLocation(pointX - 1, pointY);     // (-1, 0)
                    break;
                case 4:
                    point.setLocation(pointX + 1, pointY);      // (+1, 0)
                    break;
                case 5:
                    point.setLocation(pointX - 1, pointY + 1); // (-1, +1)
                    break;
                case 6:
                    point.setLocation(pointX, pointY + 1);      // (0, +1)
                    break;
                case 7:
                    point.setLocation(pointX + 1, pointY + 1);  // (+1, +1)
                    break;
            }
        }

        public Point next() {
            /* Take a point and move it to where the next neighbor should be */
            do {
                setNeighborPosition(curPoint, neighborToVisit);
                neighborToVisit--;
            } while(!inBounds(curPoint) && neighborToVisit >= 0);
            return curItem();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("not supported yet");
        }

        public Point curItem() {
            return (Point) curPoint.clone(); // Return a copy because we will reuse the internal Point
        }
    }

    @Override
    public Iterator<Cell> iterator() {
        return this.cells.iterator();
    }

    public Iterator<Point> pointIterator() {
        return new NeighborIterator();
    }

    public Neighbors(Cell cell) {
        /*
          In this constructor we must setup the neighbors
         */
        this.cell = cell;
        this.cells = new ArrayList<Cell>(7);
        Point p;
        Iterator<Point> neighbors = new NeighborIterator();
        while(neighbors.hasNext()) {
            p = neighbors.next();
            this.cells.add(cell.getWorldState().getCell(p));
        }
    }
}
