package org.example;

import java.awt.*;
import java.util.*;

import static java.lang.Math.floorMod;

//should have origin coordinates, and tile coordinates are relative to that.
//should randomly create one tetromino when initialised
enum TetrominoType {
    I(TileType.I,3, new HashMap<>(Map.of(
            0, new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}},
            1, new int[][]{{2, 0}, {2, 1}, {2, 2}, {2, 3}},
            2, new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}},
            3, new int[][]{{2, 0}, {2, 1}, {2, 2}, {2, 3}}
    ))),
    L(TileType.L,4, new HashMap<>(Map.of(
            0, new int[][]{{0, 1}, {0, 2}, {1, 1}, {2, 1}},
            1, new int[][]{{0, 0}, {1, 0}, {1, 1}, {1, 2}},
            2, new int[][]{{0, 1}, {2, 0}, {1, 1}, {2, 1}},
            3, new int[][]{{2, 2}, {1, 0}, {1, 1}, {1, 2}}
    ))),
    J(TileType.J,4, new HashMap<>(Map.of(
            0, new int[][]{{0, 1}, {2, 2}, {1, 1}, {2, 1}},
            1, new int[][]{{0, 2}, {1, 0}, {1, 1}, {1, 2}},
            2, new int[][]{{0, 1}, {0, 0}, {1, 1}, {2, 1}},
            3, new int[][]{{2, 0}, {1, 0}, {1, 1}, {1, 2}}
    ))),
    S(TileType.S,4, new HashMap<>(Map.of(
            0, new int[][]{{0, 2}, {1, 2}, {1, 1}, {2, 1}},
            1, new int[][]{{1, 0}, {1, 1}, {2, 1}, {2, 2}},
            2, new int[][]{{0, 2}, {1, 2}, {1, 1}, {2, 1}},
            3, new int[][]{{1, 0}, {1, 1}, {2, 1}, {2, 2}}
    ))),
    Z(TileType.Z,4, new HashMap<>(Map.of(
            0, new int[][]{{0, 1}, {1, 1}, {1, 2}, {2, 2}},
            1, new int[][]{{1, 2}, {1, 1}, {2, 1}, {2, 0}},
            2, new int[][]{{0, 1}, {1, 1}, {1, 2}, {2, 2}},
            3, new int[][]{{1, 2}, {1, 1}, {2, 1}, {2, 0}}
    ))),
    T(TileType.T,4, new HashMap<>(Map.of(
            0, new int[][]{{0, 1}, {1, 1}, {2, 1}, {1, 2}},
            1, new int[][]{{1, 0}, {1, 1}, {1, 2}, {0, 1}},
            2, new int[][]{{0, 1}, {1, 1}, {2, 1}, {1, 0}},
            3, new int[][]{{1, 0}, {1, 1}, {1, 2}, {2, 1}}
    ))),
    O(TileType.O, 4, new HashMap<>(Map.of(
            0, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            1, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            2, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            3, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}})));

    private final TileType tileType;
    private final int initialXCoordinate;
    private final HashMap<Integer, int[][]> coordsByRotation;

    private static final TetrominoType[] VALUES = TetrominoType.values();
    private static final Random RANDOM = new Random();

    TetrominoType(TileType tileType, int initialXCoordinate, HashMap<Integer, int[][]> relativeCoordinates) {
        this.tileType = tileType;
        this.initialXCoordinate = initialXCoordinate;
        this.coordsByRotation = relativeCoordinates;
    }

    public HashMap<Integer, int[][]> getCoordsByRotation() {
        return coordsByRotation;
    }

    public int getInitialXCoordinate() {
        return initialXCoordinate;
    }

    public Color getColor() {
        return tileType.getColor();
    }

    public TileType getTileType() { return tileType; }

    public static TetrominoType getRandomTetromino() {
        return VALUES[RANDOM.nextInt(7)];
    }
}



public class Tetromino {
    private int[] origin = new int[2];

    private int rotation; //defaults to 0

    TetrominoType tetrominoType;
    public int[][] actualCoordinates = new int[4][2];
    ;

    //currently only O piece
    Tetromino() {
        tetrominoType = TetrominoType.getRandomTetromino(); //later make this random
        setOrigin(tetrominoType.getInitialXCoordinate(), 0);
    }

    public boolean hasLanded(Board board) {
        for (int i = 0; i < 4; i++) {
            if (actualCoordinates[i][1] >= 19 ||
                    board.grid[actualCoordinates[i][0]][1 + actualCoordinates[i][1]] != TileType.BLANK) {
                return true;
            }
        }
        return false; //default, to be changed
    }


    public void fall() {
        setOrigin(origin[0], origin[1]+1);
    }

    public void setOrigin(int x,int y) {
        origin[0] = x;
        origin[1] = y;
        updateActualCoordinates();
    }

    public void rotate(boolean isClockwise) {
        if (isClockwise) {
            rotation = floorMod((rotation + 1),4);
        } else {
            rotation = floorMod((rotation - 1),4);
        }
        updateActualCoordinates();
    }


    private void updateActualCoordinates() {
        //actualCoordinates[i][j] is the jth coord of the ith square in the tetromino.
        for (int i = 0; i < 4; i++) {
            actualCoordinates[i][0] = origin[0] + tetrominoType.getCoordsByRotation().get(rotation)[i][0];
            actualCoordinates[i][1] = origin[1] + tetrominoType.getCoordsByRotation().get(rotation)[i][1];
        }
    }


    //X key for clockwise rotation, Z for antiC
    public void moveLeft() {
        setOrigin(origin[0]-1, origin[1]);
    }

    public boolean canMoveLeft(Board board) {
        for (int i = 0; i < 4; i++) {
            if (actualCoordinates[i][0] <= 0 ||
                    board.grid[actualCoordinates[i][0]-1][actualCoordinates[i][1]] != TileType.BLANK) {
                return false;
            }
        }
        return true;
    }

    public void moveRight() {
        setOrigin(origin[0]+1, origin[1]);
    }

    public boolean canMoveRight(Board board) {
        for (int i = 0; i < 4; i++) {
            if (actualCoordinates[i][0] >= 9 ||
                    board.grid[actualCoordinates[i][0]+1][actualCoordinates[i][1]] != TileType.BLANK) {
                return false;
            }
        }
        return true;
    }

    public void moveDown() {
        setOrigin(origin[0], origin[1] +1);
    }

    public void rotateIncludingCheck(Board board, boolean isClockwise) {
        rotate(isClockwise);
        //if any overlap, reverse the rotation and return.
        for (int i = 0; i < 4; i++) {
            if (actualCoordinates[i][0] < 0 ||
                    actualCoordinates[i][0] > 9 ||
                    actualCoordinates[i][1] < 0 ||
                    actualCoordinates[i][1] > 19 ||
                    board.grid[actualCoordinates[i][0]][actualCoordinates[i][1]] != TileType.BLANK) {
                rotate(!isClockwise);
                return;
            }
        }
    }
}

