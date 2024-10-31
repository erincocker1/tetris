package org.example;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.max;

//should have origin coordinates, and tile coordinates are relative to that.
//should randomly create one tetromino when initialised
enum TetrominoType {
    //I(Color.white,3),
    //L(Color.red,4),
    //J(Color.blue,4),
    //S(Color.magenta,4),
    //Z(Color.yellow,4),
    //T(Color.pink,4),
    O(Color.cyan, 4, new HashMap<>(Map.of(
            0, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            1, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            2, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            3, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}})));

    private final Color color;
    private final int initialXCoordinate;
    private final HashMap<Integer, int[][]> coordsByRotation;

    TetrominoType(Color color, int initialXCoordinate, HashMap<Integer, int[][]> relativeCoordinates) {
        this.color = color;
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
        return color;
    }
}

public class Tetromino {
    int[] origin;

    int rotation;

    TetrominoType tetrominoType;
    public int[][] actualCoordinates = new int[4][2];
    ;

    //currently only O piece
    Tetromino() {
        Random random = new Random();
        tetrominoType = TetrominoType.O; //later make this random
        origin = new int[]{tetrominoType.getInitialXCoordinate(), 0};
        rotation = 0;
        calculateActualCoordinates();
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
        origin[1] += 1;
        calculateActualCoordinates();
    }

    //actualCoordinates[i][j] is the jth coord of the ith square in the tetromino.
    public void calculateActualCoordinates() {
        for (int i = 0; i < 4; i++) {
            actualCoordinates[i][0] = origin[0] + tetrominoType.getCoordsByRotation().get(rotation)[i][0];
            actualCoordinates[i][1] = origin[1] + tetrominoType.getCoordsByRotation().get(rotation)[i][1];
        }
    }

    //X key for clockwise rotation, Z for antiC
    public void moveLeft() {
            origin[0]--;
            calculateActualCoordinates();
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
        origin[0]++; //need to check when at the edge
        calculateActualCoordinates();
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
}

