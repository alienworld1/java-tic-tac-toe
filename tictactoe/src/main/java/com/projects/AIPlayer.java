package com.projects;

public class AIPlayer {
    private final AIStrategy strategy;
    private final Player aiSymbol;

    public AIPlayer(AIStrategy strategy, Player aiSymbol) {
        this.strategy = strategy;
        this.aiSymbol = aiSymbol;
    }

    public Move makeMove(GameBoard board) {
        return strategy.getBestMove(board, aiSymbol);
    }

    public Player getAiSymbol() {
        return aiSymbol;
    }
}
