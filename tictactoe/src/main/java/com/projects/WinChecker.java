package com.projects;

public class WinChecker {
    private final GameBoard gameBoard;
    private static final int BOARD_SIZE = 3;
  
    public WinChecker(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
  
    public boolean checkWin(Player player) {
        return checkRows(player) || checkColumns(player) || checkDiagonals(player);
    }
  
    private boolean checkRows(Player player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (gameBoard.getCell(i, 0) == player &&
                gameBoard.getCell(i, 1) == player &&
                gameBoard.getCell(i, 2) == player) {
                return true;
            }
        }
        return false;
    }
  
    private boolean checkColumns(Player player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (gameBoard.getCell(0, i) == player &&
                gameBoard.getCell(1, i) == player &&
                gameBoard.getCell(2, i) == player) {
                return true;
            }
        }
        return false;
    }
  
    private boolean checkDiagonals(Player player) {
        return (gameBoard.getCell(0, 0) == player &&
                gameBoard.getCell(1, 1) == player &&
                gameBoard.getCell(2, 2) == player) ||
               (gameBoard.getCell(0, 2) == player &&
                gameBoard.getCell(1, 1) == player &&
                gameBoard.getCell(2, 0) == player);
    }
  }
