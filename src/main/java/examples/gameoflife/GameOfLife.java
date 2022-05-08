package examples.gameoflife;

import jecell.CellState;
import jecell.Coordinate;
import jecell.Grid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GameOfLife {

    public static Grid<Boolean> loadGridFrom(String filename) {
        int row = 0;
        int column = 0;

        Map<Coordinate, Boolean> cellStates = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                GameOfLife.class.getResourceAsStream("/gameoflife/" + filename)
            ));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                row++;

                column = 0;
                for (String cell : line.trim().split("")) {
                    column++;
                    cellStates.put(new Coordinate(row, column), !cell.equals("."));
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load " + filename);
            System.out.println(e.getMessage());
        }

        return new Grid(Boolean.class, row, column)
            .useMooreNeighbourhood()
            .initialState(c -> cellStates.get(c))
            .stateUpdate(stateUpdate());
    }

    private static Function<CellState<Boolean>, Boolean> stateUpdate() {
        return s -> {
            boolean isAlive = s.currentState();
            long numberOfLivingNeighbours = s.countNeighboursThatSatisfy(b -> b == true);

            if (isAlive) {
                if (numberOfLivingNeighbours == 2 || numberOfLivingNeighbours == 3) {
                    return true; // Survival
                } else {
                    return false; // Death
                }
            } else {
                if (numberOfLivingNeighbours == 3) {
                    return true; // Birth
                } else {
                    return false; // Nothing changes
                }
            }
        };
    }
}
