package com.meguru.maven.mysudoku.userinterface;

import com.meguru.maven.mysudoku.gamelogic.SudokuGame;

public interface InterfaceContract {

    public interface EventListener {
        void onSudokuInput(int x, int y, int input);
        void onDialogClick();
    }
    public interface View {
//        void setListener(EventListener listener);
        void updateSquare(int x, int y, int input);
        void updateBoard(SudokuGame game);
        int[][] getBoard();
        void displayMessage(String message);
        void displayError(String message);
    }
}
