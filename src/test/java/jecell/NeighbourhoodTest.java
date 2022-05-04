package jecell;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NeighbourhoodTest {

    @Test
    @DisplayName("Moore neighbours in the middle of a non-wrapping grid")
    void mooreNeighboursInTheMiddleOfNonWrappingGrid() {
        Grid<Integer> grid = new Grid(Integer.class, 10, 10).useMooreNeighbourhood();
        Set<Coordinate> neighbours = grid.neighbours(4, 5);

        assertThat(neighbours).isEqualTo(Sets.set(
            new Coordinate(3, 4),
            new Coordinate(3, 5),
            new Coordinate(3, 6),
            new Coordinate(4, 4),
            new Coordinate(4, 6),
            new Coordinate(5, 4),
            new Coordinate(5, 5),
            new Coordinate(5, 6)
        ));
    }

    @Test
    @DisplayName("Moore neighbours at the edges of a non-wrapping grid")
    void mooreNeighboursAtEdgesOfNonWrappingGrid() {
        Grid<Integer> grid = new Grid(Integer.class, 10, 10).useMooreNeighbourhood();
    }

    @Test
    @DisplayName("Moore neighbours at the edges of a wrapping grid")
    void mooreNeighboursAtEdgesOfWrappingGrid() {
        Grid<Integer> grid = new Grid(Integer.class, 1, 10)
            .wrapHorizontally()
            .useMooreNeighbourhood();

        Set<Coordinate> neighbours = grid.neighbours(1, 10);
        assertThat(neighbours).isEqualTo(Sets.set(
            new Coordinate(1, 9),
            new Coordinate(1, 1) // = wrapped (1, 11)
        ));
    }

    @Test
    @DisplayName("Von Neumann neighbours in the middle of a non-wrapping grid")
    void vonNeumannNeighboursInTheMiddleOfNonWrappingGrid() {
        Grid<Integer> grid = new Grid(Integer.class, 10, 10).useVonNeumannNeighbourhood();
        Set<Coordinate> neighbours = grid.neighbours(4, 5);

        assertThat(neighbours).isEqualTo(Sets.set(
            new Coordinate(3, 5),
            new Coordinate(4, 4),
            new Coordinate(4, 6),
            new Coordinate(5, 5)
        ));
    }

    @Test
    @DisplayName("Von Neumann neighbours at the edges of a non-wrapping grid")
    void vonNeumannNeighboursAtEdgesOfNonWrappingGrid() {
        Grid<Integer> grid = new Grid(Integer.class, 10, 10).useVonNeumannNeighbourhood();
        Set<Coordinate> neighbours;

        // Left edge
        neighbours = grid.neighbours(3, 1);
        assertThat(neighbours).isEqualTo(Sets.set(
            new Coordinate(2, 1),
            new Coordinate(3, 2),
            new Coordinate(4, 1)
        ));

        // Bottom edge
        neighbours = grid.neighbours(1, 3);
        assertThat(neighbours).isEqualTo(Sets.set(
            new Coordinate(1, 2),
            new Coordinate(1, 4),
            new Coordinate(2, 3)
        ));
    }

    @Test
    @DisplayName("Von Neumann neighbours in the corners of a non-wrapping grid")
    void vonNeumannNeighboursInCornersOfNonWrappingGrid() {
        Grid<Integer> grid = new Grid(Integer.class, 10, 10).useVonNeumannNeighbourhood();
    }

    @Test
    @DisplayName("Von Neumann neighbours at the edges of a wrapping grid")
    void vonNeumannNeighboursAtEdgesOfWrappingGrid() {
        Grid<Integer> grid = new Grid(Integer.class, 10, 10)
            .wrapHorizontally()
            .wrapVertically()
            .useVonNeumannNeighbourhood();
        Set<Coordinate> neighbours;

        // Left edge
        neighbours = grid.neighbours(3, 1);
        assertThat(neighbours).isEqualTo(Sets.set(
            new Coordinate(2, 1),
            new Coordinate(3, 2),
            new Coordinate(4, 1),
            new Coordinate(3, 10) // = wrapped (3, 0)
        ));

        // Bottom edge
        neighbours = grid.neighbours(1, 3);
        assertThat(neighbours).isEqualTo(Sets.set(
            new Coordinate(1, 2),
            new Coordinate(1, 4),
            new Coordinate(2, 3),
            new Coordinate(10, 3) // = wrapped (0, 3)
        ));
    }

    @Test
    @DisplayName("Von Neumann neighbours in the corners of a wrapping grid")
    void vonNeumannNeighboursInCornersOfWrappingGrid() {
        Grid<Integer> grid = new Grid(Integer.class, 10, 10).useVonNeumannNeighbourhood();
    }
}
