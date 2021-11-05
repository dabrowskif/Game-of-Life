package controllers;


import game.Cell;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import models.MainWindowModel;
import models.WindowLoader;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

public class MainWindowController extends WindowLoader {

    @FXML
    public GridPane visualGameGrid;
    @FXML
    private Button startButton;
    @FXML
    private MenuItem gospersGliderGunMenuItem;
    @FXML
    private MenuItem setVirtualGridMenuItem;
    @FXML
    public TextField stepsToMakeTextField;
    @FXML
    public TextField numberOfThreadsTextField;
    @FXML
    public Label stepsMadeLabel;
    @FXML
    public TextField speedOfTheGameTextField;
    @FXML
    public CheckBox virtualGridCheckBox;

    public MainWindowModel mainWindowModel;
    private GridSettingsWindowController gridSettingsWindowController;
    private final Stage mainWindowStage;
    public int numberOfGameGridRows;
    public int numberOfGameGridColumns;

    public MainWindowController() throws IOException {
        createWindow(mainWindowStage = new Stage(), "/views/mainWindow.fxml", "/css/gameGridStyle.css",
                "Conway's Game of Life", "/img/icon.png", this, false);
    }

    @FXML
    private void initialize() {
        mainWindowModel = new MainWindowModel(this);
        numberOfGameGridRows = visualGameGrid.getRowConstraints().size();
        numberOfGameGridColumns = visualGameGrid.getColumnConstraints().size();

        createGraphicGrid();

        gospersGliderGunMenuItem.setOnAction(event -> {
            mainWindowModel.loadPremadeBoard(1);
        });
        startButton.setOnAction(event -> {
            try {
                if(!mainWindowModel.gameThread.isAlive())
                    mainWindowModel.runGameThread();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        setVirtualGridMenuItem.setOnAction(event -> {
            try {
                gridSettingsWindowController = new GridSettingsWindowController(this);
                gridSettingsWindowController.showStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void createGraphicGrid() {
        for(int i = 0; i < numberOfGameGridRows; i++) {
            for(int j = 0; j < numberOfGameGridColumns; j++) {
                Pane pane = createPane(i, j);
                if(numberOfGameGridColumns == 100 && numberOfGameGridRows == 60) {
                    visualGameGrid.add(pane, j, i);
                }
                mainWindowModel.graphicGrid[i][j] = new Cell(i, j, false, pane);
                mainWindowModel.primaryLogicGrid[i][j] = new Cell(i, j, false, pane);
                mainWindowModel.secondaryLogicGrid[i][j] = new Cell(i, j, false, pane);
            }
        }
    }


    private Pane createPane(int x, int y) {
        Pane pane = new Pane();
        if(isCellInBorder(x, y)) {
            pane.getStyleClass().add("game-grid-cell-border");
        }
        else {
            pane.getStyleClass().add("game-grid-cell-dead");
            createPaneMouseEventHandler(pane, x, y);
        }
        return pane;
    }

    private boolean isCellInBorder(int x, int y) {
        return x == 0 || x == numberOfGameGridRows - 1 || y == 0 || y == numberOfGameGridColumns - 1;
    }

    private void createPaneMouseEventHandler(Pane pane, int x, int y){
        pane.setOnMousePressed(e -> {
            convertGraphicCell(x, y);
        });
    }

    public void convertGraphicCell(int x, int y) {
        Cell cell = mainWindowModel.graphicGrid[x][y];
        cell.setStatus(!cell.getStatus());
        cell.setImage();
    }

    public void setGraphicCell(int x, int y) {
        Cell cell = mainWindowModel.graphicGrid[x][y];
        if(cell.getStatus()) {
            cell.setAliveCellImage();
        }
        else {
            cell.setDeadCellImage();
        }
    }

    public void showStage() {
        mainWindowStage.show();
    }

}
