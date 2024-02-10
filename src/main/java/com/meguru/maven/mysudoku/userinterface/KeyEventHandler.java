package com.meguru.maven.mysudoku.userinterface;

import com.meguru.maven.mysudoku.gamelogic.GameState;
import com.meguru.maven.mysudoku.gamelogic.SudokuGame;
import com.meguru.maven.mysudoku.gamelogic.SudokuGenerator;
import com.meguru.maven.mysudoku.gamelogic.SudokuSolver;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import com.meguru.maven.mysudoku.userinterface.InterfaceContract;

public class KeyEventHandler implements EventHandler<KeyEvent>, InterfaceContract.EventListener{
    private InterfaceContract.View view;

    public KeyEventHandler(InterfaceContract.View view) {
        super();
        this.view = view;
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getText().matches("[0-9]")) {
                int value = Integer.parseInt(event.getText());
                SudokuTextField source = (SudokuTextField) event.getSource();
                onSudokuInput(source.getX(), source.getY(), value);
            } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                SudokuTextField source = (SudokuTextField) event.getSource();
                onSudokuInput(source.getX(), source.getY(), 0);
            }
        }
        event.consume();
    }

    @Override
    public void onSudokuInput(int x, int y, int input) {
        view.updateSquare(x, y, input);
        int[][] board = view.getBoard();
        if (SudokuGame.isGridFull(board)){
            if(SudokuSolver.checkSolved(board)){
                view.displayMessage("Congratulations! You solved the puzzle!");
            } else {
                view.displayError("Incorrect solution!");
            }
        }
    }

    @Override
    public void onDialogClick() {
        SudokuGame game = new SudokuGame(GameState.NEW, SudokuGenerator.generateGame());
        view.updateBoard(game);
    }
}
