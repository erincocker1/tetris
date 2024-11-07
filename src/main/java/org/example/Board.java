package org.example;

public class Board {
    TileType[][] grid = new TileType[10][20];

    Board() {
        for (int i=0; i<10; i++) {
            for(int j=0; j<20; j++) {
                grid[i][j] = TileType.BLANK;
            }
        }
    }

    public void addFallenTetromino(Tetromino tetromino) {
        for (int i=0; i<4; i++) {
            grid[tetromino.actualCoordinates[i][0]][tetromino.actualCoordinates[i][1]] = tetromino.tetrominoType.getTileType();
        }
    }

    public int checkForFullLine(int totalLines) {
        int fullLineCount = 0;
        boolean currentLineFull;
        for (int j=19; j>=0; j--) {
            currentLineFull = true;
            for (int i=0; i<10; i++) {
                if (grid[i][j] == TileType.BLANK) {
                    currentLineFull = false;
                    break;
                }
            }

            if (currentLineFull) {
                fullLineCount += 1;
            } else if (fullLineCount > 0) { //move current row down {fullLineCount} rows
                for (int i=0; i<10; i++) {
                    grid[i][j+fullLineCount] = grid[i][j];
                }
            }
        }
        return totalLines + fullLineCount;
    }

    public boolean isGameOver() {
        for (int i = 0; i < 10; i++) {
            if (grid[i][0] != TileType.BLANK) {
                return true;
            }
        }
        return false;
    }
}
