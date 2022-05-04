package examples.gameoflife;

import jecell.Grid;

public class GameOfLife {

    private final Grid<Boolean> grid;

    public GameOfLife() {
        grid = new Grid(Double.class, 100, 100)
            .useVonNeumannNeighbourhood();

        // TODO
    }

    public Grid grid() {
        return grid;
    }

    public static void main(String[] args) {
        new GameOfLife();
    }
}
