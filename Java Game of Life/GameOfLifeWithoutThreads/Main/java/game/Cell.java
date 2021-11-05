package game;

import javafx.scene.layout.Pane;

public class Cell {

    private final int x;
    private final int y;
    private boolean status;
    private int numberOfNeighbours;
    private Pane pane;


    public Cell(int x, int y, boolean status) {
        this.x = x;
        this.y = y;
        this.status = status;
        this.numberOfNeighbours = 0;
    }

    public Cell(int x, int y, boolean status, Pane pane) {
        this.x = x;
        this.y = y;
        this.status = status;
        this.numberOfNeighbours = 0;
        this.pane = pane;
    }


    public void addNeighbour() {
        numberOfNeighbours++;
    }


    public void setNumberOfNeighbours(int numberOfNeighbours) {
        this.numberOfNeighbours = numberOfNeighbours;
    }

    public int getNumberOfNeighbours() {
        return numberOfNeighbours;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean hasTwoNeighbours() {
        return getNumberOfNeighbours() == 2;
    }

    public boolean hasThreeNeighbours() {
        return getNumberOfNeighbours() == 3;
    }

    public void setImage() {
        if(this.status) {
            setAliveCellImage();
        }
        else {
            setDeadCellImage();
        }
    }

    public void setAliveCellImage() {
        this.pane.getStyleClass().clear();
        this.pane.getStyleClass().add("game-grid-cell-alive");
    }

    public void setDeadCellImage() {
        this.pane.getStyleClass().clear();
        this.pane.getStyleClass().add("game-grid-cell-dead");
    }


}
