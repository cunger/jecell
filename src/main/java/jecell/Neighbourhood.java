package jecell;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Neighbourhood {

    protected int rows;
    protected int columns;

    protected boolean wrapHorizontally;
    protected boolean wrapVertically;

    public Neighbourhood(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.wrapHorizontally = false;
        this.wrapVertically = false;
    }

    public abstract Set<Coordinate> neighbours(int row, int column);

    public Set<Coordinate> neighbours(Coordinate coordinate) {
        return neighbours(coordinate.row(), coordinate.column());
    }

    protected boolean isWithinGridBoundaries(Coordinate coordinate) {
        return isWithinGridBoundaries(coordinate.row(), coordinate.column());
    }

    protected boolean isWithinGridBoundaries(int row, int column) {
        if (row < 1) return false;
        if (column < 1) return false;
        if (row > rows) return false;
        if (column > columns) return false;
        return true;
    }

    public Coordinate wrap(Coordinate coordinate) {
        return wrapHorizontally(wrapVertically(coordinate));
    }
    protected Set<Coordinate> wrap(Set<Coordinate> coordinates) {
        return coordinates.parallelStream().map(c -> wrap(c)).collect(Collectors.toSet());
    }

    /**
     * Wraps horizontally, i.e. wraps column index.
     */
    private Coordinate wrapHorizontally(Coordinate coordinate) {
        int newColumn = coordinate.column();

        if (wrapHorizontally) {
            while (newColumn < 1) newColumn = columns + coordinate.column();
            while (newColumn > columns) newColumn = coordinate.column() - columns;
        }

        return new Coordinate(coordinate.row(), newColumn);
    }

    /**
     * Wraps vertically, i.e. wraps row index.
     */
    private Coordinate wrapVertically(Coordinate coordinate) {
        int newRow = coordinate.row();

        if (wrapVertically) {
            while (newRow < 1) newRow = rows - coordinate.row();
            while (newRow > rows) newRow = coordinate.row() - rows;
        }

        return new Coordinate(newRow, coordinate.column());
    }
}
