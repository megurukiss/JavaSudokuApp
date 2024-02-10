package com.meguru.maven.mysudoku.userinterface;

import javafx.scene.control.TextField;

public class SudokuTextField extends TextField{
    private int x;
    private int y;

    public SudokuTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    @Override
    public void replaceText(int i, int i1, String string) {
//        if (!string.matches("[0-9]")) {
//            super.replaceText(i, i1, string);
//        }
    }

    @Override
    public void replaceSelection(String string) {
//        if (!string.matches("[0-9]")) {
//            super.replaceSelection(string);
//        }
    }
}
