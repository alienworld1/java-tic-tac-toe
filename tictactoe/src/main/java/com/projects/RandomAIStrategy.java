package com.projects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAIStrategy implements AIStrategy {
    private final Random random = new Random();

    @Override
    public Move getBestMove(GameBoard board, Player aiPlayer) {
        List<Move> availableMoves = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isValidMove(i, j)) {
                    availableMoves.add(new Move(i, j));
                }
            }
        }
        
        if (!availableMoves.isEmpty()) {
            return availableMoves.get(random.nextInt(availableMoves.size()));
        }
        return null;
    }
}