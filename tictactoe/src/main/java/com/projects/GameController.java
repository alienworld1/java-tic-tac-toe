package com.projects;

public class GameController {
    private final GameBoard gameBoard;
    private final WinChecker winChecker;
    private Player currentPlayer;
    private GameState gameState;
    private GameMode gameMode;
    private AIPlayer aiPlayer;

    public GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.winChecker = new WinChecker(gameBoard);
        this.currentPlayer = Player.X;
        this.gameState = GameState.PLAYING;
        this.gameMode = GameMode.HUMAN_VS_HUMAN;
    }

    public void setGameMode(GameMode mode, AIStrategy strategy) {
        this.gameMode = mode;
        if (mode == GameMode.HUMAN_VS_AI) {
            this.aiPlayer = new AIPlayer(strategy, Player.O);
        } else {
            this.aiPlayer = null;
        }
        resetGame();
    }

    public boolean makeMove(int row, int col) {
        if (gameState != GameState.PLAYING) {
            return false;
        }

        if (gameBoard.makeMove(row, col, currentPlayer)) {
            if (checkGameEnd()) {
                return true;
            }

            switchPlayer();

            // AI move if it's AI's turn
            if (gameMode == GameMode.HUMAN_VS_AI && 
                currentPlayer == aiPlayer.getAiSymbol() && 
                gameState == GameState.PLAYING) {
                    
                Move aiMove = aiPlayer.makeMove(gameBoard);
                if (aiMove != null) {
                    gameBoard.makeMove(aiMove.getRow(), aiMove.getCol(), currentPlayer);
                    if (checkGameEnd()) {
                        return true;
                    }
                    switchPlayer();
                }
            }
            return true;
        }
        return false;
    }

    private boolean checkGameEnd() {
        if (winChecker.checkWin(currentPlayer)) {
            gameState = GameState.WIN;
            return true;
        } else if (gameBoard.isFull()) {
            gameState = GameState.DRAW;
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

    public GameBoard getGameBoard() {
        return gameBoard;
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

    public boolean makeAIMove() {
        if (gameState != GameState.PLAYING || 
            gameMode != GameMode.HUMAN_VS_AI || 
            currentPlayer != aiPlayer.getAiSymbol()) {
            return false;
        }

        Move aiMove = aiPlayer.makeMove(gameBoard);
        if (aiMove != null) {
            boolean moveSuccessful = gameBoard.makeMove(aiMove.getRow(), aiMove.getCol(), currentPlayer);
            if (moveSuccessful) {
                if (checkGameEnd()) {
                    return true;
                }
                switchPlayer();
                return true;
            }
        }
        return false;
    }

    public boolean isAITurn() {
        return gameMode == GameMode.HUMAN_VS_AI && 
               currentPlayer == aiPlayer.getAiSymbol() &&
               gameState == GameState.PLAYING;
    }
}