package models;

import controllers.MainWindowController;
import game.Cell;
import game.ComputingThread;
import game.PremadeGrid;
import game.RowComputer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MainWindowModel {

    public MainWindowController mainWindowController;
    public PremadeGrid premadeGrid;
    public Cell[][] graphicGrid;
    public Cell[][] primaryLogicGrid;
    public Cell[][] secondaryLogicGrid;
    public int numberOfGameGridRows;
    public int numberOfGameGridColumns;

    public ComputingThread computingThread;
    public Thread gameThread;
    public int numberOfThreadsToRun;


    public MainWindowModel(MainWindowController mainWindowController){
        this.mainWindowController = mainWindowController;
        this.numberOfGameGridRows = mainWindowController.visualGameGrid.getRowConstraints().size();
        this.numberOfGameGridColumns = mainWindowController.visualGameGrid.getColumnConstraints().size();


        numberOfThreadsToRun = Integer.parseInt(mainWindowController.numberOfThreadsTextField.getText());
        premadeGrid = new PremadeGrid(numberOfGameGridRows, numberOfGameGridColumns);
        graphicGrid = new Cell[numberOfGameGridRows][numberOfGameGridColumns];
        primaryLogicGrid = new Cell[numberOfGameGridRows][numberOfGameGridColumns];
        secondaryLogicGrid = new Cell[numberOfGameGridRows][numberOfGameGridColumns];

        computingThread = new ComputingThread(this);
        gameThread = new Thread(computingThread);

    }

    public void runGameThread() throws BrokenBarrierException, InterruptedException {
        computingThread = new ComputingThread(this);
        gameThread = new Thread(computingThread);
        initializePrimaryLogicBoard();
        gameThread.start();
    }

    private void initializePrimaryLogicBoard() {
        for(int i = 0; i < numberOfGameGridRows; i++) {
            for(int j = 0; j < numberOfGameGridColumns; j++) {
                primaryLogicGrid[i][j].setStatus(graphicGrid[i][j].getStatus());
                secondaryLogicGrid[i][j].setStatus(graphicGrid[i][j].getStatus());
            }
        }
    }

    public void loadPremadeBoard(int whichBoardToLoad) {
        switch(whichBoardToLoad) {
            case 1:
                //System.out.println(numberOfGameGridColumns + ":" + numberOfGameGridRows);
                premadeGrid.loadGosperGliderGun();
                    for (int i = 0; i < numberOfGameGridRows; i++) {
                        for (int j = 0; j < numberOfGameGridColumns; j++) {
                            if (graphicGrid[i][j].getStatus() != premadeGrid.getStatus(i, j)) {
                                if(numberOfGameGridColumns == 100 && numberOfGameGridRows == 60) {
                                    mainWindowController.convertGraphicCell(i, j);
                                }
                                else {
                                    graphicGrid[i][i].setStatus(premadeGrid.getStatus(i, j));
                                }
                            }
                        }
                    }

                break;
            default:
                break;
        }
    }


    public void updateLogicGrids() {
        for(int i = 0; i < numberOfGameGridRows; i++) {
            for (int j = 0; j < numberOfGameGridColumns; j++) {
                primaryLogicGrid[i][j].setStatus(secondaryLogicGrid[i][j].getStatus());
            }
        }
    }

    public void showPrimary() {
        System.out.println("Primary:");
        for(int i = 0; i < numberOfGameGridRows; i++) {
            for (int j = 0; j < numberOfGameGridColumns; j++) {
                if(primaryLogicGrid[i][j].getStatus()) {
                    System.out.print("1 ");
                }
                else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    public void updateGraphicGrid() {
            for(int i = 0; i < numberOfGameGridRows; i++) {
                for (int j = 0; j < numberOfGameGridColumns; j++) {
                    mainWindowController.setGraphicCell(i, j);
                }
            }
    }

    public void updateSizes() {
        numberOfGameGridRows = mainWindowController.numberOfGameGridRows;
        numberOfGameGridColumns = mainWindowController.numberOfGameGridColumns;
        premadeGrid = new PremadeGrid(numberOfGameGridRows, numberOfGameGridColumns);
        graphicGrid = new Cell[numberOfGameGridRows][numberOfGameGridColumns];
        primaryLogicGrid = new Cell[numberOfGameGridRows][numberOfGameGridColumns];
        secondaryLogicGrid = new Cell[numberOfGameGridRows][numberOfGameGridColumns];
    }
}
