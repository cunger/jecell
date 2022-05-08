package examples.gameoflife;

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

public class GameOfLifeFX extends Application {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    private Grid<Boolean> grid = GameOfLife.loadGridFrom("trafficlight.txt");
    private Map<String, Rectangle> cache = new HashMap<>();

    @Override
    public void start(Stage stage) {
        TilePane tiles = new TilePane();
        tiles.setPrefRows(grid.rows());
        tiles.setPrefColumns(grid.columns());
        tiles.setPadding(new Insets(20, 20, 20, 20));
        tiles.setStyle("-fx-background-color: #eeeeee;");

        for (int x = 1; x <= grid.rows(); x++) {
            for (int y = 1; y <= grid.columns(); y++) {
                Rectangle cell = new Rectangle(WIDTH, HEIGHT);
                tiles.getChildren().add(cell);
                cache.put(x + " " + y, cell);
            }
        }

        colorGridState();

        stage.setTitle("Game of Life");
        stage.setScene(new Scene(tiles));
        stage.show();

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, event -> grid.evolve()),
            new KeyFrame(Duration.millis(200), event -> colorGridState())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void colorGridState() {
        for (int x = 1; x <= grid.rows(); x++) {
            for (int y = 1; y <= grid.columns(); y++) {
                boolean alive = grid.cell(x, y).get();
                Rectangle cell = cache.get(x + " " + y);
                if (alive) {
                    cell.setFill(Color.rgb(224, 224, 224));
                } else {
                    cell.setFill(Color.rgb(73, 73, 73));
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
