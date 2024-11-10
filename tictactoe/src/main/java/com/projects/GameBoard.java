package com.projects;

public class GameBoard {
    private final Player[][] board;
    private static final int BOARD_SIZE = 3;
  
    public GameBoard() {
        board = new Player[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }
  
    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Player.EMPTY;
            }
        }
    }
  
    public boolean makeMove(int row, int col, Player player) {
        if (isValidMove(row, col)) {
            board[row][col] = player;
            return true;
        }
        return false;
    }
  
    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && 
               col >= 0 && col < BOARD_SIZE && 
               board[row][col] == Player.EMPTY;
    }
  
    public Player getCell(int row, int col) {
        return board[row][col];
    }
  
    public void reset() {
        initializeBoard();
    }
  
    public boolean isFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == Player.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
  }