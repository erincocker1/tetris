package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TetrisGame extends JPanel implements KeyListener {

    final int boardWidth;
    final int boardHeight;

    final int T = 30;

    final int SPEED = 500;

    Board board;
    Tetromino tetromino;


    //constructor for TetrisGame
    TetrisGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        tetromino = new Tetromino();
        board = new Board();
        repaint();

        new Thread(() -> {
            try {
                Thread.sleep(SPEED);
            } catch (InterruptedException e) {}

            while (true) {
                try {
                    if (!tetromino.hasLanded(board)) {
                        tetromino.fall();
                    }
                    Thread.sleep(SPEED/20); //to allow tucks+spins :)
                    if (tetromino.hasLanded(board)) {
                        board.addFallenTetromino(tetromino);
                        if (board.isGameOver()) { break; }
                        board.checkForFullLine();
                        tetromino = new Tetromino();
                    }
                    repaint();
                    Thread.sleep(SPEED);
                } catch ( InterruptedException e ) {}
            }
        }).start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        drawBoard(g);
        drawFallingTetromino(g);
    }

    private void drawFallingTetromino(Graphics g) {
        int[][] coords = tetromino.actualCoordinates;
        g.setColor(tetromino.tetrominoType.getColor());
        for (int i = 0; i < 4; i++) {
            g.fillRect(coords[i][0] * T, coords[i][1] * T, T, T);
        }
    }

    private void drawBoard(Graphics g) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                g.setColor(board.grid[i][j].getColor());
                g.fillRect(i * T, j * T, T, T);
            }
        }
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (tetromino.canMoveLeft(board)) {
                tetromino.moveLeft();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (tetromino.canMoveRight(board)) {
                tetromino.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (! tetromino.hasLanded(board)) {
                tetromino.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_Z) {
            tetromino.rotateIncludingCheck(board, false);
        } else if (e.getKeyCode() == KeyEvent.VK_X) {
            tetromino.rotateIncludingCheck(board, true);
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
