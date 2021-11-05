package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.WindowLoader;
import sun.applet.Main;

import java.io.IOException;

public class GridSettingsWindowController extends WindowLoader {

    @FXML
    public TextField widthTextField;
    @FXML
    public TextField heightTextField;
    @FXML
    private Button closeButton;

    private final Stage gridSettingsStage;
    private MainWindowController mainWindowController;

    public GridSettingsWindowController(MainWindowController mainWindowController) throws IOException {
        createWindow(gridSettingsStage = new Stage(), "/views/gridSettingsWindow.fxml", "/css/gameGridStyle.css",
                "Conway's Game of Life", "/img/icon.png", this, false);
        this.mainWindowController = mainWindowController;
    }

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> {
            mainWindowController.numberOfGameGridRows = Integer.parseInt(heightTextField.getText());
            mainWindowController.numberOfGameGridColumns = Integer.parseInt(widthTextField.getText());
            mainWindowController.mainWindowModel.updateSizes();
            mainWindowController.createGraphicGrid();
            gridSettingsStage.close();
        });
    }

    public void showStage() {
        gridSettingsStage.show();
    }

}
