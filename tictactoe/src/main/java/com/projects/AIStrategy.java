package com.projects;

public interface AIStrategy {
    Move getBestMove(GameBoard board, Player aiPlayer);
}
