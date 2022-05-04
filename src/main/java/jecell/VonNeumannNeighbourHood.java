package jecell;

import java.util.HashSet;
import java.util.Set;

public class VonNeumannNeighbourHood extends Neighbourhood {

    public VonNeumannNeighbourHood(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public Set<Coordinate> neighbours(int row, int column) {
        Set<Coordinate> neighbours = new HashSet<>();

        neighbours.add(new Coordinate(row, column - 1));
        neighbours.add(new Coordinate(row, column + 1));
        neighbours.add(new Coordinate(row - 1, column));
        neighbours.add(new Coordinate(row + 1, column));

        neighbours = wrap(neighbours);
        neighbours.removeIf(c -> !isWithinGridBoundaries(c));

        return neighbours;
    }
}
