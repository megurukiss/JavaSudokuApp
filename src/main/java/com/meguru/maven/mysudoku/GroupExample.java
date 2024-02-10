package com.meguru.maven.mysudoku;

import com.meguru.maven.mysudoku.gamelogic.GameState;
import com.meguru.maven.mysudoku.gamelogic.SudokuGame;
import com.meguru.maven.mysudoku.gamelogic.SudokuGenerator;
import javafx.application.Application;
import javafx.stage.Stage;
import com.meguru.maven.mysudoku.userinterface.UserInterface;

public class GroupExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        UserInterface ui = new UserInterface(primaryStage);
        SudokuGame game = new SudokuGame(GameState.NEW, SudokuGenerator.generateGame());
        ui.updateBoard(game);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

