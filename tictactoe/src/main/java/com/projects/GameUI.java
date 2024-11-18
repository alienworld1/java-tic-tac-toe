package com.projects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameUI {
    private final GameController gameController;
    private final Button[][] buttons;
    private Label statusLabel;
    private static final int BOARD_SIZE = 3;

    public GameUI(GameController gameController) {
        this.gameController = gameController;
        this.buttons = new Button[BOARD_SIZE][BOARD_SIZE];
    }

    public void initialize(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("game-container");

        statusLabel = new Label("Player X's turn");
        statusLabel.getStyleClass().add("status-label");

        GridPane gridPane = createGameGrid();
        Button resetButton = createResetButton();

        root.getChildren().addAll(statusLabel, gridPane, resetButton);

        Scene scene = new Scene(root, 400, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createGameGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Button button = createGameButton(i, j);
                buttons[i][j] = button;
                gridPane.add(button, j, i);
            }
        }

        return gridPane;
    }

    private Button createGameButton(int row, int col) {
        Button button = new Button();
        button.getStyleClass().addAll("game-button");
        button.setPrefSize(100, 100);
        button.setOnAction(e -> handleButtonClick(row, col));
        return button;
    }

    private Button createResetButton() {
        Button resetButton = new Button("Reset Game");
        resetButton.getStyleClass().add("reset-button");
        resetButton.setOnAction(e -> resetGame());
        return resetButton;
    }

    private void handleButtonClick(int row, int col) {
        if (gameController.makeMove(row, col)) {
            updateButton(row, col);
            updateGameStatus();
        }
    }

    private void updateButton(int row, int col) {
        Button button = buttons[row][col];
        Player currentPlayer = gameController.getCellState(row, col);
        
        button.setText(currentPlayer.getSymbol());
        button.getStyleClass().add(currentPlayer == Player.X ? "game-button-x" : "game-button-o");
        button.setDisable(true);
        
        // Add button click animation
        button.setScaleX(0.7);
        button.setScaleY(0.7);
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(200), 
                e -> {
                    button.setScaleX(1);
                    button.setScaleY(1);
                }
            )
        );
        timeline.play();
    }

    private void updateGameStatus() {
        switch (gameController.getGameState()) {
            case WIN:
                statusLabel.setText("Player " + gameController.getCurrentPlayer().getSymbol() + " wins!");
                animateWinningButtons();
                break;
            case DRAW:
                statusLabel.setText("Game ended in a draw!");
                break;
            case PLAYING:
                statusLabel.setText("Player " + gameController.getCurrentPlayer().getSymbol() + "'s turn");
                break;
        }
    }

    private void animateWinningButtons() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isWinningCell(i, j)) {
                    buttons[i][j].getStyleClass().add("win-button");
                }
            }
        }
    }

    private boolean isWinningCell(int row, int col) {
        Player player = gameController.getCurrentPlayer();
        return (checkRowWin(row, player) || 
                checkColumnWin(col, player) || 
                checkDiagonalWin(player));
    }

    private boolean checkRowWin(int row, Player player) {
        return gameController.getCellState(row, 0) == player &&
               gameController.getCellState(row, 1) == player &&
               gameController.getCellState(row, 2) == player;
    }

    private boolean checkColumnWin(int col, Player player) {
        return gameController.getCellState(0, col) == player &&
               gameController.getCellState(1, col) == player &&
               gameController.getCellState(2, col) == player;
    }

    private boolean checkDiagonalWin(Player player) {
        return (gameController.getCellState(0, 0) == player &&
                gameController.getCellState(1, 1) == player &&
                gameController.getCellState(2, 2) == player) ||
               (gameController.getCellState(0, 2) == player &&
                gameController.getCellState(1, 1) == player &&
                gameController.getCellState(2, 0) == player);
    }

    private void resetGame() {
        gameController.resetGame();
        resetButtons();
        statusLabel.setText("Player X's turn");
    }

    private void resetButtons() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Button button = buttons[i][j];
                button.setText("");
                button.setDisable(false);
                button.getStyleClass().removeAll("game-button-x", "game-button-o", "win-button");
            }
        }
    }

    private void disableAllButtons() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j].setDisable(true);
            }
        }
    }
}