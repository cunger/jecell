package jecell;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class Grid<T> {

    protected final int rows;
    protected final int columns;

    protected Map<Integer, Map<Integer, T>> cells;

    protected Neighbourhood neighbourhood;

    protected Function<CellState, T> stateUpdate;

    public Grid(Class<T> type, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cells = new HashMap<>();
        this.neighbourhood = new VonNeumannNeighbourhood(rows, columns);
    }

    public Grid<T> wrapHorizontally() {
        this.neighbourhood.wrapHorizontally = true;
        return this;
    }

    public Grid<T> wrapVertically() {
        this.neighbourhood.wrapVertically = true;
        return this;
    }

    public Grid<T> useMooreNeighbourhood() {
        boolean wrapHorizontally = neighbourhood.wrapHorizontally;
        boolean wrapVertically = neighbourhood.wrapVertically;
        neighbourhood = new MooreNeighbourhood(rows, columns);
        neighbourhood.wrapHorizontally = wrapHorizontally;
        neighbourhood.wrapVertically = wrapVertically;
        return this;
    }

    public Grid<T> useVonNeumannNeighbourhood() {
        boolean wrapHorizontally = neighbourhood.wrapHorizontally;
        boolean wrapVertically = neighbourhood.wrapVertically;
        neighbourhood = new VonNeumannNeighbourhood(rows, columns);
        neighbourhood.wrapHorizontally = wrapHorizontally;
        neighbourhood.wrapVertically = wrapVertically;
        return this;
    }

    public Grid<T> useCustomNeighbourhood(Neighbourhood customNeighbourhood) {
        boolean wrapHorizontally = neighbourhood.wrapHorizontally;
        boolean wrapVertically = neighbourhood.wrapVertically;
        neighbourhood = customNeighbourhood;
        neighbourhood.wrapHorizontally = wrapHorizontally;
        neighbourhood.wrapVertically = wrapVertically;
        return this;
    }

    public Grid<T> initialState(T state) {
        return initialState(c -> state);
    }

    public Grid<T> initialState(Function<Coordinate, T> state) {
        for (int x = 1; x <= rows; x++) {
            cells.put(x, new HashMap<>());
            for (int y = 1; y <= columns; y++) {
                cells.get(x).put(y, state.apply(new Coordinate(x, y)));
            }
        }

        return this;
    }

    public Grid<T> stateUpdate(Function<CellState, T> update) {
        stateUpdate = update;
        return this;
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return columns;
    }

    public void evolve() {
        Map<Integer, Map<Integer, T>> newCells = new HashMap<>();

        for (int x = 1; x <= rows; x++) {
            newCells.put(x, new HashMap<>());
            for (int y = 1; y <= columns; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                CellState cell = new CellState(coordinate, cells.get(x).get(y), neighbourhood);
                for (Coordinate neighbour : neighbourhood.neighbours(coordinate)) {
                    cell.addNeighbour(neighbour, cells.get(neighbour.row()).get(neighbour.column()));
                }

                newCells.get(x).put(y, stateUpdate.apply(cell));
            }
        }

        cells = newCells;
    }

    public Optional<T> cell(int row, int column) {
        if (neighbourhood.isWithinGridBoundaries(row, column)) {
            return Optional.of(cells.get(row).get(column));
        } else {
            return Optional.empty();
        }
    }

    public Set<Coordinate> neighbours(int row, int column) {
        return neighbourhood.neighbours(row, column);
    }
}
