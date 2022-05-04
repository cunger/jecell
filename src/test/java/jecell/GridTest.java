package jecell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GridTest {

    @Test
    @DisplayName("Access to grid cells is 1-based")
    void accessToGridCells() {
        Grid<Integer> grid = new Grid(Integer.class, 1, 1).initialState(2);

        assertThat(grid.cell(1, 1)).isPresent();
        assertThat(grid.cell(0, 0)).isNotPresent();
    }

    @Test
    @DisplayName("Cells within a grid are initialized")
    void cellsWithinNonWrappingGrid() {
        Grid<Integer> grid = new Grid(Integer.class, 1, 10).initialState(2);

        for (int col = 1; col <= 10; col++) {
            assertThat(grid.cell(1, col)).isPresent();
            assertThat(grid.cell(1, 1)).get().isEqualTo(2);
        }
    }

    @Test
    @DisplayName("Cells outside a grid do not exist")
    void cellsOutsideNonWrappingGrid() {
        Grid<Integer> grid = new Grid(Integer.class, 1, 10);

        assertThat(grid.cell(0, 0)).isNotPresent();
        assertThat(grid.cell(2, 2)).isNotPresent();
        assertThat(grid.cell(2, 1)).isNotPresent();
        assertThat(grid.cell(2, 2)).isNotPresent();
        assertThat(grid.cell(1, 11)).isNotPresent();
    }
}
