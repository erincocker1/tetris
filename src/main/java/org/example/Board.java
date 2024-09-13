package org.example;

public class Board {
    TileType[][] grid = new TileType[10][20];

    Board() {
        for (int i=0; i<10; i++) {
            for(int j=0; j<20; j++) {
                grid[i][j] = TileType.BLANK;
            }
        }
        grid[0][5] = TileType.O;
    }

    public void addFallenTetromino(Tetromino tetromino) {
        for (int i=0; i<4; i++) {
            grid[tetromino.actualCoordinates[i][0]][tetromino.actualCoordinates[i][1]] = TileType.O;
            //TO BE CHANGED, NEEDS TO BE TILETYPE OF TETROMINO
        }
    }

    public void checkForFullLine() {
    }
}
