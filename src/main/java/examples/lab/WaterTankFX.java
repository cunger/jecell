package examples.lab;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import jecell.Grid;

import java.util.HashMap;
import java.util.Map;

public class WaterTankFX  extends Application {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    private final Grid<CellOccupation> grid = Lab.grid();
    private Map<String, Rectangle> cache = new HashMap<>();

    @Override
    public void start(Stage stage) {
        TilePane tiles = new TilePane();
        tiles.setPrefRows(grid.rows());
        tiles.setPrefColumns(grid.columns());
        tiles.setPadding(new Insets(20, 20, 20, 20));

        for (int x = 1; x <= grid.rows(); x++) {
            for (int y = 1; y <= grid.columns(); y++) {
                Rectangle cell = new Rectangle(WIDTH, HEIGHT);
                tiles.getChildren().add(cell);
                cache.put(x + " " + y, cell);
            }
        }

        colorGridState();

        stage.setTitle("Lab");
        stage.setScene(new Scene(tiles));
        stage.show();

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, event -> grid.evolve()),
            new KeyFrame(Duration.millis(500), event -> colorGridState())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void colorGridState() {
        for (int x = 1; x <= grid.rows(); x++) {
            for (int y = 1; y <= grid.columns(); y++) {
                CellOccupation state = grid.cell(x, y).get();
                Rectangle cell = cache.get(x + " " + y);
                if (state.isOccupied()) {
                    cell.setFill(Color.rgb(188, 23, 15, 1 - ((double) state.speed() / 10.0)));
                } else {
                    cell.setFill(Color.AQUA);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
