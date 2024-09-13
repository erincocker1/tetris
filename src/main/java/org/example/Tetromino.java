package org.example;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//should have origin coordinates, and tile coordinates are relative to that.
//should randomly create one tetromino when initialised
enum TetrominoType {
    //I(Color.white,4),
    //L(Color.red,3),
    //J(Color.blue,3),
    //S(Color.magenta,3),
    //Z(Color.yellow,3),
    //T(Color.pink,3),
    O(Color.cyan, 2, new HashMap<>(Map.of(
            0, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            1, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            2, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            3, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}})));

    private final Color color;
    private final int drawAreaSize;
    private final HashMap<Integer, int[][]> coordsByRotation;

    TetrominoType(Color color, int drawAreaSize, HashMap<Integer, int[][]> coordsByRotation) {
        this.color = color;
        this.drawAreaSize = drawAreaSize;
        this.coordsByRotation = coordsByRotation;
    }

    public HashMap<Integer, int[][]> getCoordsByRotation() {
        return coordsByRotation;
    }

    public int getDrawAreaSize() {
        return drawAreaSize;
    }

    public Color getColor() {
        return color;
    }
}

public class Tetromino {
    int[] origin;

    int rotation;

    TetrominoType tetrominoType;
    public int[][] actualCoordinates = new int[4][2];;

    //currently only O piece
    Tetromino() {
        Random random = new Random();
        tetrominoType = TetrominoType.O; //later make this random
        origin = new int[]{random.nextInt(11 - tetrominoType.getDrawAreaSize()), 0};
        rotation = 0;
    }

    public boolean hasLanded() {
        for (int i = 0; i < 4; i++) {
            if (actualCoordinates[i][1] >= 19) {
                return true;
            }
        }
        return false; //default, to be changed
    }



    public void fall() {
        origin[1] += 1;
    }

    //actualCoordinates[i][j] is the jth coord of the ith square in the tetromino.
    public void calculateActualCoordinates() {
        for (int i = 0; i < 4; i++) {
            actualCoordinates[i][0] = origin[0] + tetrominoType.getCoordsByRotation().get(rotation)[i][0];
            actualCoordinates[i][1] = origin[1] + tetrominoType.getCoordsByRotation().get(rotation)[i][1];
        }
    }

    //X key for clockwise rotation, Z for antiC
}

