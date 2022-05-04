package examples.algaelab;

import jecell.CellState;
import jecell.Coordinate;
import jecell.Grid;

import java.util.function.Function;

public class AlgaeLab {

    private final Grid<Double> grid;

    public AlgaeLab() {
        // Initial state
        Function<Coordinate, Double> rho_0;
        // State update
        Function<CellState<Double>, Double> rho_t;

        rho_0 = c -> 0.2 * Math.cos(c.column()) + 0.5;
        rho_t = s -> {
            double rho_i = s.currentState();
            double rho_iplus1 = s.stateOfNeighbour(0, 1);
            return rho_i - 0.1 * ((1 - rho_iplus1) * rho_iplus1 - (1 - rho_i) * rho_i);
        };

        grid = new Grid(Double.class, 1, 360)
            .wrapHorizontally()
            .useMooreNeighbourhood()
            .initialState(rho_0)
            .stateUpdate(rho_t);
    }

    public Grid grid() {
        return grid;
    }

    public static void main(String[] args) {
        new AlgaeLab();
    }
}
