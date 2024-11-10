package com.projects;

public class GameController {
    private final GameBoard gameBoard;
    private final WinChecker winChecker;
    private Player currentPlayer;
    private GameState gameState;
  
    public GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.winChecker = new WinChecker(gameBoard);
        this.currentPlayer = Player.X;
        this.gameState = GameState.PLAYING;
    }
  
    public boolean makeMove(int row, int col) {
        if (gameState != GameState.PLAYING) {
            return false;
        }
  
        if (gameBoard.makeMove(row, col, currentPlayer)) {
            if (winChecker.checkWin(currentPlayer)) {
                gameState = GameState.WIN;
            } else if (gameBoard.isFull()) {
                gameState = GameState.DRAW;
            } else {
                switchPlayer();
            }
            return true;
        }
        return false;
    }
  
    private void switchPlayer() {
        currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
    }
  
    public void resetGame() {
        gameBoard.reset();
        currentPlayer = Player.X;
        gameState = GameState.PLAYING;
    }
  
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
  
    public GameState getGameState() {
        return gameState;
    }
  
    public Player getCellState(int row, int col) {
        return gameBoard.getCell(row, col);
    }
  }