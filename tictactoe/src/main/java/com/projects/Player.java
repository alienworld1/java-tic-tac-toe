package com.projects;

public enum Player {
    X("X"), O("O"), EMPTY("");
  
    private final String symbol;
  
    Player(String symbol) {
        this.symbol = symbol;
    }
  
    public String getSymbol() {
        return symbol;
    }
  }
