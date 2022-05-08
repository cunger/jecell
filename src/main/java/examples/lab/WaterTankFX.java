package examples.lab;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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

    private static final int WIDTH = 10;
    private static final int HEIGHT = 100;

    private final Grid<Double> grid = new Lab().grid();
    private Map<String, Rectangle> cache = new HashMap<>();

    @Override
    public void start(Stage stage) {
        TilePane tiles = new TilePane();
        tiles.setPrefRows(grid.rows());
        tiles.setPrefColumns(grid.columns());
        tiles.setStyle("-fx-background-color: aqua;");

        for (int x = 1; x <= grid.rows(); x++) {
            for (int y = 1; y <= grid.columns(); y++) {
                Rectangle cell = new Rectangle(WIDTH, HEIGHT);
                tiles.getChildren().add(cell);
                cache.put(x + " " + y, cell);
            }
        }

        colorGridState();

        stage.setTitle("Grünalgen-Lab");
        stage.setScene(new Scene(tiles));
        stage.show();

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, event -> grid.evolve()),
            new KeyFrame(Duration.millis(400), event -> colorGridState()),
            new KeyFrame(Duration.ZERO, event -> System.out.println("tick"))
        );
        timeline.setCycleCount(60);
        timeline.play();
    }

    private void colorGridState() {
        for (int x = 1; x <= grid.rows(); x++) {
            for (int y = 1; y <= grid.columns(); y++) {
                double algae = grid.cell(x, y).get();
                if (Double.isNaN(algae)) return;
                // TODO Opacity would need normalization to [0,10.
                double opacity = Math.min(1, Math.max(0, algae));
                Rectangle cell = cache.get(x + " " + y);
                cell.setFill(Color.rgb(81, 252, 2, opacity));
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
