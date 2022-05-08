package examples.lab;

import jecell.Coordinate;
import jecell.Grid;

import java.util.HashMap;
import java.util.Map;

public class Lab {

    public static Grid<CellOccupation> grid() {
        Map<Coordinate, Integer> startSpeeds = new HashMap<>();
        startSpeeds.put(new Coordinate(1, 1), 5);
        startSpeeds.put(new Coordinate(1, 3), 1);
        startSpeeds.put(new Coordinate(1, 6), 3);
        startSpeeds.put(new Coordinate(1, 10), 2);
        startSpeeds.put(new Coordinate(1, 13), 5);

        return new WaterTank(1, 20)
            .useVonNeumannNeighbourhood() // doesn't matter which neighbourhood
            .wrapHorizontally()
            .initialState(c -> {
                if (startSpeeds.containsKey(c)) {
                    return new CellOccupation(true, startSpeeds.get(c));
                } else {
                    return new CellOccupation(false, 0);
                }
            });
    }

    private static class WaterTank extends Grid<CellOccupation> {

        public WaterTank(int rows, int columns) {
            super(CellOccupation.class, rows, columns);
        }

        @Override
        public void evolve() {
            Map<Integer, Map<Integer, CellOccupation>> newCells = new HashMap<>();

            for (int x = 1; x <= rows; x++) {
                newCells.put(x, new HashMap<>());
            }

            for (int x = 1; x <= rows; x++) {
                for (int y = 1; y <= columns; y++) {
                    Coordinate coordinate = new Coordinate(x, y);
                    CellOccupation cell = cells.get(x).get(y);

                    if (cell.isOccupied()) {
                        Coordinate newCoordinate = neighbourhood.wrap(new Coordinate(x, y + cell.speed()));
                        int newSpeed;
                        int distanceAhead = distanceToNextNeighbour(coordinate);
                        if (distanceAhead < cell.speed()) {
                            newSpeed = distanceAhead;
                        } else {
                            newSpeed = cell.speed();
                            if (cell.speed() < 5) {
                                newSpeed += 1;
                            }
                        }

                        int newX = newCoordinate.row();
                        int newY = newCoordinate.column();
                        newCells.get(newX).put(newY, new CellOccupation(true, newSpeed));
                    }
                }
            }

            // Fill rest of cells with unoccupied state
            for (int x = 1; x <= rows; x++) {
                for (int y = 1; y <= columns; y++) {
                    if (!newCells.get(x).containsKey(y)) {
                        newCells.get(x).put(y, new CellOccupation(false, 0));
                    }
                }
            }

            cells = newCells;
        }

        private int distanceToNextNeighbour(Coordinate coordinate) {
            for (int i = 1; i <= columns; i++) {
                Coordinate neighbour = neighbourhood.wrap(new Coordinate(coordinate.row(), coordinate.column() + i));
                CellOccupation neighbourState = cell(neighbour.row(), neighbour.column()).get();
                if (neighbourState.isOccupied()) {
                    return i;
                }
            }

            return columns;
        }
    }
}
