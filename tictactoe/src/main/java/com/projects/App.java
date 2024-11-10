package com.projects;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private final GameBoard gameBoard;
    private final GameController gameController;
    private final GameUI gameUI;

    public App() {
        this.gameBoard = new GameBoard();
        this.gameController = new GameController(gameBoard);
        this.gameUI = new GameUI(gameController);
    }

    @Override
    public void start(Stage primaryStage) {
        gameUI.initialize(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}