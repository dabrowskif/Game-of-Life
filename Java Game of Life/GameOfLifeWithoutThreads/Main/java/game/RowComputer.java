package game;

import javafx.application.Platform;
import models.MainWindowModel;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class RowComputer implements Runnable{

    private final MainWindowModel mainWindowModel;
    private final ComputingThread computingThread;

    private Cell [][] primaryLogicGrid;
    private Cell [][] secondaryLogicGrid;
    private Cell[][] graphicGrid;
    private final int numberOfGameGridRows;
    private final int numberOfGameGridColumns;
    private int rowToCompute;
    private CyclicBarrier threadBarrier;
    public int generationsToCompute;
    public int generationsComputed;
    private int firstRow;
    private int lastRow;
    private int speedOfTheGame;

    public RowComputer(MainWindowModel mainWindowModel, ComputingThread computingThread, int threadAlfa) {
        this.mainWindowModel = mainWindowModel;
        this.computingThread = computingThread;
        //this.rowToCompute = rowToCompute;

        this.numberOfGameGridRows = mainWindowModel.numberOfGameGridRows;
        this.numberOfGameGridColumns = mainWindowModel.numberOfGameGridColumns;
        this.graphicGrid = mainWindowModel.graphicGrid;
        this.primaryLogicGrid = mainWindowModel.primaryLogicGrid;
        this.secondaryLogicGrid = mainWindowModel.secondaryLogicGrid;

        this.threadBarrier = mainWindowModel.computingThread.threadBarrier;
        this.generationsToCompute = computingThread.generationsToCompute;

        generationsComputed = 0;
        this.speedOfTheGame = Integer.parseInt(mainWindowModel.mainWindowController.speedOfTheGameTextField.getText());
        cutBoardForThreads(threadAlfa);
    }

    private void cutBoardForThreads(int threadLambda) {
        firstRow = threadLambda * (numberOfGameGridRows / mainWindowModel.computingThread.numberOfThreadsToRun);
        lastRow = firstRow + (numberOfGameGridRows / mainWindowModel.computingThread.numberOfThreadsToRun);
        if(threadLambda == computingThread.numberOfThreadsToRun - 1 && lastRow < numberOfGameGridRows) {
            lastRow = numberOfGameGridRows;
        }
    }

    @Override
    public void run() {
        while(generationsComputed < generationsToCompute) {
            for(int i = firstRow; i < lastRow; i++) {
                rowToCompute = i;
                computeRowForNextGeneration();
            }
            try {
                threadBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(speedOfTheGame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            generationsComputed++;
        }
    }

    private void computeRowForNextGeneration() {
        countNeighboursOfAllCells();
        updateAllCellsIsAlive();
    }

    private void countNeighboursOfAllCells() {
        for(int y = 0; y < numberOfGameGridColumns; y++) {
            countNumberOfSpecificCellNeighbours(y);
        }
    }

    private void countNumberOfSpecificCellNeighbours(int y) {
        if(!isCellInBorder(y)) {
            for (int i = rowToCompute - 1; i <= rowToCompute + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (primaryLogicGrid[i][j].getStatus() && !(i == rowToCompute && j == y)) {
                        secondaryLogicGrid[rowToCompute][y].addNeighbour();
                    }
                }
            }
        }
    }

    private boolean isCellInBorder(int y) {
        return rowToCompute == 0 || rowToCompute == numberOfGameGridRows - 1 || y == 0 || y == numberOfGameGridColumns - 1;
    }


    private void updateAllCellsIsAlive() {
        for(int y = 0; y < numberOfGameGridColumns; y++) {
            setCellIsAlive(y);
        }
    }

    public void setCellIsAlive(int y) {
        Cell cell = secondaryLogicGrid[rowToCompute][y];
        if( !(cell.getStatus() &&
                (cell.hasTwoNeighbours() || cell.hasThreeNeighbours()))) {
            cell.setStatus(false);
            mainWindowModel.graphicGrid[rowToCompute][y].setStatus(false);
        }
        if(!cell.getStatus() && cell.hasThreeNeighbours()) {
            cell.setStatus(true);
            mainWindowModel.graphicGrid[rowToCompute][y].setStatus(true);
        }
        cell.setNumberOfNeighbours(0);
    }

}
