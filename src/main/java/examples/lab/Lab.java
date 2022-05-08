package examples.lab;

import jecell.CellState;
import jecell.Coordinate;
import jecell.Grid;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Lab {

    private final Grid<CellOccupation> grid;

    public Lab() {
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
            return null;
        };

        grid = new Grid(Double.class, 1, 20)
            .wrapHorizontally()
            .useMooreNeighbourhood()
            .initialState(state_0)
            .stateUpdate(state_t);
    }

    public Grid grid() {
        return grid;
    }

    public static void main(String[] args) {
        new Lab();
    }
}
