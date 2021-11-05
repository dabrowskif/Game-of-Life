package game;

import controllers.TimeWindowController;
import javafx.application.Platform;
import models.MainWindowModel;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComputingThread implements Runnable{

    private MainWindowModel mainWindowModel;
    private TimeWindowController timeWindowController;
    private int numberOfGameGridRows;

    private RowComputer[] rowComputers;
    private Thread[] rowThreads;
    public CyclicBarrier threadBarrier;

    public int generationsToCompute;
    private int generationsComputed;
    public int speedOfTheGame;
    public int numberOfThreadsToRun;

    long startingTime;


    public ComputingThread(MainWindowModel mainWindowModel) {
        this.mainWindowModel = mainWindowModel;
        this.numberOfGameGridRows = mainWindowModel.numberOfGameGridRows;
        this.numberOfThreadsToRun = mainWindowModel.numberOfThreadsToRun;

        generationsComputed = 0;
    }

    @Override
    public void run() {
        initializeThreads();
        //mainWindowModel.showPrimary();
        for(int i = 0; i < numberOfThreadsToRun; i++) {
            rowThreads[i].start();
        }
    }

    private void initializeThreads() {
        startingTime = System.currentTimeMillis();
        generationsToCompute = Integer.parseInt(mainWindowModel.mainWindowController.stepsToMakeTextField.getText());
        speedOfTheGame = Integer.parseInt(mainWindowModel.mainWindowController.speedOfTheGameTextField.getText());
        numberOfThreadsToRun = Integer.parseInt(mainWindowModel.mainWindowController.numberOfThreadsTextField.getText());

        rowComputers = new RowComputer[numberOfThreadsToRun];
        rowThreads = new Thread[numberOfThreadsToRun];

        threadBarrier = new CyclicBarrier(numberOfThreadsToRun, new Runnable(){
            @Override
            public void run() {
                generationsComputed++;
                mainWindowModel.updateLogicGrids();
                if(speedOfTheGame >= 200 && !mainWindowModel.mainWindowController.virtualGridCheckBox.isSelected()) {
                    mainWindowModel.updateGraphicGrid();
                }
                updateStepsMadeLabel();
                lastGenerationActions();

            }
        });

        for(int i = 0; i < numberOfThreadsToRun; i++) {
            rowComputers[i] = new RowComputer(mainWindowModel, this, i);
            rowThreads[i] = new Thread(rowComputers[i]);
        }
    }

    private void updateStepsMadeLabel() {
        Platform.runLater( () -> mainWindowModel.mainWindowController.stepsMadeLabel.setText("Steps made: " + generationsComputed));
    }

    private void lastGenerationActions() {
        if(generationsComputed == generationsToCompute) {
            if(!mainWindowModel.mainWindowController.virtualGridCheckBox.isSelected()) {
                mainWindowModel.updateGraphicGrid();
            }
            Thread.currentThread().interrupt();
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //mainWindowModel.showPrimary();
            long elapsedTime = System.currentTimeMillis() - startingTime;
            float elapsedTimeInSeconds = elapsedTime / 1000F;
            Platform.runLater( () -> {
                try {
                    timeWindowController = new TimeWindowController(elapsedTimeInSeconds);
                    timeWindowController.showStage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


}
