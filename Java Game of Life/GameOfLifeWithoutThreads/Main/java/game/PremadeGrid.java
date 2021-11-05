package game;

public class PremadeGrid {

    public Cell [][] premadeGrid;


    public PremadeGrid(int numberOfGameGridRows, int numberOfGameGridColumns) {
        premadeGrid = new Cell[numberOfGameGridRows][numberOfGameGridColumns];
        initializePremadeGrids(numberOfGameGridRows, numberOfGameGridColumns);
    }

    private void initializePremadeGrids(int numberOfGameGridRows, int numberOfGameGridColumns) {
        for(int x = 0; x < numberOfGameGridRows; x++) {
            for(int y = 0; y < numberOfGameGridColumns; y++) {
                createPremadeGridCell(x, y, false);
            }
        }
    }

    private void createPremadeGridCell(int x, int y, boolean isAlive) {
        Cell cell = new Cell(x, y, isAlive);
        premadeGrid[x][y] = cell;
    }



    public void loadGosperGliderGun(){
        premadeGrid[24][16].setStatus(true);
        premadeGrid[25][16].setStatus(true);
        premadeGrid[25][17].setStatus(true);
        premadeGrid[24][17].setStatus(true);
        premadeGrid[24][26].setStatus(true);
        premadeGrid[25][26].setStatus(true);
        premadeGrid[26][26].setStatus(true);
        premadeGrid[27][27].setStatus(true);
        premadeGrid[28][28].setStatus(true);
        premadeGrid[28][29].setStatus(true);
        premadeGrid[23][27].setStatus(true);
        premadeGrid[22][28].setStatus(true);
        premadeGrid[22][29].setStatus(true);
        premadeGrid[25][30].setStatus(true);
        premadeGrid[27][31].setStatus(true);
        premadeGrid[26][32].setStatus(true);
        premadeGrid[25][32].setStatus(true);
        premadeGrid[24][32].setStatus(true);
        premadeGrid[23][31].setStatus(true);
        premadeGrid[25][33].setStatus(true);
        premadeGrid[24][36].setStatus(true);
        premadeGrid[24][37].setStatus(true);
        premadeGrid[23][37].setStatus(true);
        premadeGrid[23][36].setStatus(true);
        premadeGrid[22][36].setStatus(true);
        premadeGrid[22][37].setStatus(true);
        premadeGrid[21][38].setStatus(true);
        premadeGrid[25][38].setStatus(true);
        premadeGrid[26][40].setStatus(true);
        premadeGrid[25][40].setStatus(true);
        premadeGrid[21][40].setStatus(true);
        premadeGrid[20][40].setStatus(true);
        premadeGrid[22][50].setStatus(true);
        premadeGrid[23][50].setStatus(true);
        premadeGrid[23][51].setStatus(true);
        premadeGrid[22][51].setStatus(true);
    }


    public boolean getStatus(int i, int j) {
        return premadeGrid[i][j].getStatus();
    }
    /*public Cell[][] getPremadeGrid() {
        return premadeGrid;
    }*/

}
