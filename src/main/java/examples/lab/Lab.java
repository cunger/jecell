package examples.lab;

import jecell.Coordinate;
import jecell.Grid;

import java.util.HashMap;
import java.util.Map;

public class Lab {

    public static Grid<Section> grid() {
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
                    String id = "" + (c.column() - 1);
                    return Section.occupied(id, startSpeeds.get(c));
                } else {
                    return Section.unoccupied();
                }
            });
    }

    private static class WaterTank extends Grid<Section> {

        public WaterTank(int rows, int columns) {
            super(Section.class, rows, columns);
        }

        @Override
        public void evolve() {
            Map<Integer, Map<Integer, Section>> updatedCells = new HashMap<>();

            initRows(updatedCells);
            moveCrayfish(updatedCells);
            fillRemainingCells(updatedCells);

            cells = updatedCells;
        }

        private void initRows(Map<Integer, Map<Integer, Section>> newCells) {
            for (int x = 1; x <= rows; x++) {
                newCells.put(x, new HashMap<>());
            }
        }

        private void moveCrayfish(Map<Integer, Map<Integer, Section>> newCells) {
            for (int x = 1; x <= rows; x++) {
                for (int y = 1; y <= columns; y++) {
                    Coordinate coordinate = new Coordinate(x, y);
                    Section cell = cells.get(x).get(y);

                    if (cell.isOccupied()) {
                        int newSpeed = cell.speed();
                        int distanceAhead = distanceToNextNeighbour(coordinate);
                        if (distanceAhead <= cell.speed()) {
                            newSpeed = distanceAhead - 1;
                        } else if (cell.speed() < 5 && cell.speed() < distanceAhead - 1) {
                            newSpeed = cell.speed() + 1;
                        }
                        Coordinate newCoordinate = neighbourhood.wrap(new Coordinate(x, y + newSpeed));
                        int newX = newCoordinate.row();
                        int newY = newCoordinate.column();
                        newCells.get(newX).put(newY, Section.occupied(cell.crayfishId(), newSpeed));
                    }
                }
            }
        }

        private void fillRemainingCells(Map<Integer, Map<Integer, Section>> newCells) {
            for (int x = 1; x <= rows; x++) {
                for (int y = 1; y <= columns; y++) {
                    if (!newCells.get(x).containsKey(y)) {
                        newCells.get(x).put(y, Section.unoccupied());
                    }
                }
            }
        }

        private int distanceToNextNeighbour(Coordinate coordinate) {
            for (int i = 1; i <= columns; i++) {
                Coordinate neighbour = neighbourhood.wrap(new Coordinate(coordinate.row(), coordinate.column() + i));
                Section neighbourState = cell(neighbour.row(), neighbour.column()).get();
                if (neighbourState.isOccupied()) {
                    return i;
                }
            }

            return columns;
        }
    }
}
