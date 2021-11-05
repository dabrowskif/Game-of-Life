package controllers;


import game.Cell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import models.MainWindowModel;
import models.WindowLoader;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

public class TimeWindowController extends WindowLoader {

    @FXML
    public Label computingTimeLabel;
    @FXML
    private Button closeButton;

    private final Stage timeWindowStage;
    private float computingTime;

    public TimeWindowController(float computingTime) throws IOException {
        createWindow(timeWindowStage = new Stage(), "/views/timeWindow.fxml", "/css/gameGridStyle.css",
                "Conway's Game of Life", "/img/icon.png", this, false);
        this.computingTime = computingTime;
        computingTimeLabel.setText("Computing time: " + computingTime);
    }

    @FXML
    private void initialize() {
        computingTimeLabel.setText("Computing time: " + computingTime);
        closeButton.setOnAction(event -> {
            timeWindowStage.close();
        });
    }

    public void showStage() {
        timeWindowStage.show();
    }

}
