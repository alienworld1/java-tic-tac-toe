package com.projects;

public class MinimaxAIStrategy implements AIStrategy {
    private static final int BOARD_SIZE = 3;

    @Override
    public Move getBestMove(GameBoard board, Player aiPlayer) {
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        
        // Find the best move
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.isValidMove(i, j)) {
                    // Try the move
                    board.makeMove(i, j, aiPlayer);
                    int score = minimax(board, 0, false, aiPlayer);
                    // Undo the move
                    board.makeMove(i, j, Player.EMPTY);
                    
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Move(i, j);
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(GameBoard board, int depth, boolean isMaximizing, Player aiPlayer) {
        Player humanPlayer = (aiPlayer == Player.X) ? Player.O : Player.X;
        
        // Check terminal states
        if (checkWin(board, aiPlayer)) return 10 - depth;
        if (checkWin(board, humanPlayer)) return depth - 10;
        if (isBoardFull(board)) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board.isValidMove(i, j)) {
                        board.makeMove(i, j, aiPlayer);
                        int score = minimax(board, depth + 1, false, aiPlayer);
                        board.makeMove(i, j, Player.EMPTY);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board.isValidMove(i, j)) {
                        board.makeMove(i, j, humanPlayer);
                        int score = minimax(board, depth + 1, true, aiPlayer);
                        board.makeMove(i, j, Player.EMPTY);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private boolean checkWin(GameBoard board, Player player) {
        // Check rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board.getCell(i, 0) == player && 
                board.getCell(i, 1) == player && 
                board.getCell(i, 2) == player) {
                return true;
            }
        }
        
        // Check columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board.getCell(0, i) == player && 
                board.getCell(1, i) == player && 
                board.getCell(2, i) == player) {
                return true;
            }
        }
        
        // Check diagonals
        if (board.getCell(0, 0) == player && 
            board.getCell(1, 1) == player && 
            board.getCell(2, 2) == player) {
            return true;
        }
        
        if (board.getCell(0, 2) == player && 
            board.getCell(1, 1) == player && 
            board.getCell(2, 0) == player) {
            return true;
        }
        
        return false;
    }

    private boolean isBoardFull(GameBoard board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getCell(i, j) == Player.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}