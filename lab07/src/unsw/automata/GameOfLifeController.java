package unsw.automata;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * A JavaFX controller for the Conway's Game of Live Application.
 *
 * @author Robert Clifton-Everest
 *
 */
public class GameOfLifeController {
    @FXML
    private GridPane cells;
    @FXML
    private Button play;
    private GameOfLife gl;
    private boolean playing;
    private Timeline tl;
    public GameOfLifeController() {
        gl = new GameOfLife();
        tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                gl.tick();
            }

        }));

        tl.setCycleCount(Timeline.INDEFINITE);
    }
    @FXML
    public void initialize() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                CheckBox checkbox = new CheckBox();
                gl.cellProperty(x, y).bindBidirectional(checkbox.selectedProperty());
                cells.add(checkbox, x, y);
            }
        }
    }

    @FXML
    public void handleTick(ActionEvent event) {
        gl.tick();
    }

    @FXML
    public void handlePlay(ActionEvent event) {
        if (!playing) {
            playing = true;
            play.setText("Stop");
            tl.play();
        } else {
            playing = false;
            play.setText("Play");
            tl.stop();
        }
    }

}

