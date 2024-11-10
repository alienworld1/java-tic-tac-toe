package com.projects;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        statusLabel = new Label("Player X's turn");
        GridPane gridPane = createGameGrid();
        Button resetButton = createResetButton();

        root.getChildren().addAll(statusLabel, gridPane, resetButton);

        Scene scene = new Scene(root, 300, 350);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createGameGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

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
        button.setPrefSize(80, 80);
        button.setOnAction(e -> handleButtonClick(row, col));
        return button;
    }

    private Button createResetButton() {
        Button resetButton = new Button("Reset Game");
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
        buttons[row][col].setText(gameController.getCellState(row, col).getSymbol());
        buttons[row][col].setDisable(true);
    }

    private void updateGameStatus() {
        switch (gameController.getGameState()) {
            case WIN:
                statusLabel.setText("Player " + gameController.getCurrentPlayer().getSymbol() + " wins!");
                disableAllButtons();
                break;
            case DRAW:
                statusLabel.setText("Game ended in a draw!");
                break;
            case PLAYING:
                statusLabel.setText("Player " + gameController.getCurrentPlayer().getSymbol() + "'s turn");
                break;
        }
    }

    private void resetGame() {
        gameController.resetGame();
        resetButtons();
        statusLabel.setText("Player X's turn");
    }

    private void resetButtons() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setDisable(false);
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
