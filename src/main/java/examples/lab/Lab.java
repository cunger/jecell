package examples.lab;

import jecell.CellState;
import jecell.Coordinate;
import jecell.Grid;
import jecell.Neighbourhood;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Lab {

    public static Grid<CellOccupation> grid() {
        Map<Coordinate, Integer> startSpeeds = new HashMap<>();
        startSpeeds.put(new Coordinate(1, 1), 5);
        startSpeeds.put(new Coordinate(1, 3), 1);
        startSpeeds.put(new Coordinate(1, 6), 3);
        startSpeeds.put(new Coordinate(1, 10), 2);
        startSpeeds.put(new Coordinate(1, 13), 5);

        // Initial state
        Function<Coordinate, CellOccupation> state_0;
        // State update
        Function<CellState<CellOccupation>, CellOccupation> state_t;

        state_0 = c -> {
            if (startSpeeds.containsKey(c)) {
                return new CellOccupation(true, startSpeeds.get(c));
            } else {
                return new CellOccupation(false, 0);
            }
        };

        state_t = s -> {
            for (int i = 1; i <= 5; i++) {
                int neighbourSpeed = s.stateOfNeighbour(0, -i).speed();
                if (neighbourSpeed == i) {
                    return new CellOccupation(true, neighbourSpeed);
                }
            }

            if (s.currentState().isOccupied() && s.currentState().speed() == 0) {
                return new CellOccupation(true, 0);
            }

            return new CellOccupation(false, 0);
        };

        return new Grid(Double.class, 1, 20)
            .useCustomNeighbourhood(new BackLookingNeighbourhood(1, 20))
            .wrapHorizontally()
            .initialState(state_0)
            .stateUpdate(state_t);
    }

    private static class BackLookingNeighbourhood extends Neighbourhood {
        public BackLookingNeighbourhood(int rows, int columns) {
            super(rows, columns);
        }

        @Override
        public Set<Coordinate> neighbours(int row, int column) {
            Set<Coordinate> neighbours = new HashSet<>();

            for (int i = 1; i <= 5; i++) {
                neighbours.add(wrap(new Coordinate(row, column - i)));
            }

            return neighbours;
        }
    }
}
