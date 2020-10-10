/**
 *
 */
package unsw.automata;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Conway's Game of Life on a 10x10 grid.
 *
 * @author Robert Clifton-Everest
 *
 */
public class GameOfLife {
    private BooleanProperty[][] cells;

    public GameOfLife() {
        // TODO At the start all cells are dead
        cells = new BooleanProperty[10][10];
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                cells[a][b] = new SimpleBooleanProperty();
            }
        }

    }

    public void ensureAlive(int a, int b) {
        cells[a][b].set(true);
    }

    public void ensureDead(int a, int b) {
        cells[a][b].set(true);
    }

    public boolean isAlive(int a, int b) {
        return cells[a][b].get();
    }

    public void tick() {
        boolean[][] nextGen = new boolean[10][10];

        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                int liveNeighbours = neighboursAlive(a,b);
                if (isAlive(a,b)) {
                    if (liveNeighbours == 2 || liveNeighbours == 3)
                        nextGen[a][b] = true;
                    else
                        nextGen[a][b] = false;
                } else {
                    if (liveNeighbours == 3)
                        nextGen[a][b] = true;
                    else
                        nextGen[a][b] = false;
                }
            }
        }

        // Can't just reassign like this. Have to update properties.
//        cells = nextGen;

        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                cells[a][b].set(nextGen[a][b]);
            }
        }
    }

    public BooleanProperty cellProperty(int a, int b) {
        return cells[a][b];
    }

    private int neighboursAlive(int a, int b) {
        int alive = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;

                if (isAlive(Math.floorMod(a+i,10), Math.floorMod(b+j, 10))) {
                    alive++;
                }
            }
        }
        return alive;
    }
}
