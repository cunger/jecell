package examples.lab;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jecell.Grid;

public class WaterTankFX  extends Application {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    private final Grid<Section> grid = Lab.grid();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Lab");

        TilePane tiles = new TilePane();
        tiles.setPrefRows(grid.rows() * 10);
        tiles.setPrefColumns(grid.columns());
        tiles.setPadding(new Insets(20, 20, 20, 20));

        for (int x = 1; x <= grid.rows(); x++) {
            for (int y = 1; y <= grid.columns(); y++) {
                StackPane cell = new StackPane();
                cell.getChildren().add(new Rectangle(WIDTH, HEIGHT));
                tiles.getChildren().add(cell);
            }
        }

        paint(tiles);

        stage.setScene(new Scene(tiles));
        stage.show();

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, event -> grid.evolve()),
            new KeyFrame(Duration.millis(2000), event -> paint(tiles))
        );
        timeline.setCycleCount(10);
        timeline.play();
    }

    private void paint(TilePane tiles) {
        for (int x = 1; x <= grid.rows(); x++) {
            for (int y = 1; y <= grid.columns(); y++) {
                Section state = grid.cell(x, y).get();

                StackPane cell = new StackPane();

                Rectangle crayfish = new Rectangle(WIDTH, HEIGHT);
                crayfish.setFill(state.isOccupied() ? Color.rgb(188, 23, 15) : Color.AQUA);
                crayfish.setStroke(Color.AQUA);
                Text label = new Text(state.isOccupied() ? "" + state.speed() : "");

                cell.getChildren().addAll(crayfish, label);
                tiles.getChildren().add(cell);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}