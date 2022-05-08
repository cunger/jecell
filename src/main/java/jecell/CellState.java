package jecell;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class CellState<T> {

    private final Coordinate coordinate;
    private final T currentState;
    private Map<Coordinate, T> neighbourStates;
    private Neighbourhood neighbourhood;

    public CellState(Coordinate coordinate, T currentState, Neighbourhood neighbourhood) {
        this.coordinate = coordinate;
        this.currentState = currentState;
        this.neighbourStates = new HashMap<>();
        this.neighbourhood = neighbourhood;
    }

    public void addNeighbour(Coordinate coordinate, T state) {
        neighbourStates.put(coordinate, state);
    }

    public T currentState() {
        return this.currentState;
    }

    public T stateOfNeighbour(int rowOffset, int columnOffset) {
        Coordinate neighbourCoordinate = neighbourhood.wrap(new Coordinate(
            coordinate.row() + rowOffset,
            coordinate.column() + columnOffset
        ));

        if (neighbourStates.containsKey(neighbourCoordinate)) {
            return neighbourStates.get(neighbourCoordinate);
        } else {
            throw new IllegalArgumentException(
                "The cell at " + coordinate + " has no neighbour at " + neighbourCoordinate + "."
            );
        }
    }

    public long countNeighboursThatSatisfy(Predicate<T> condition) {
        return neighbourStates.values().stream().filter(s -> condition.test(s)).count();
    }
}
