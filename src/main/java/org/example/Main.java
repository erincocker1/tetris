package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int boardWidth = 300; //tiles will be 30x30
        int boardHeight = 600;

        JFrame frame = new JFrame("tetris");

        frame.setMinimumSize(new Dimension(boardWidth,boardHeight));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImages(getImageArrayList());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TetrisGame tetrisGame = new TetrisGame(boardWidth, boardHeight);
        frame.add(tetrisGame);

        frame.pack();
        frame.setVisible(true);
    }

    private static ArrayList<Image> getImageArrayList() {
        ArrayList<Image> imageList = new ArrayList<>();
        imageList.add(new ImageIcon("src/main/resources/cactus32.jpg").getImage());
        imageList.add(new ImageIcon("src/main/resources/cactus64.jpg").getImage());
        imageList.add(new ImageIcon("src/main/resources/cactus128.jpg").getImage());
        imageList.add(new ImageIcon("src/main/resources/cactus256.jpg").getImage());
        return imageList;
    }
}