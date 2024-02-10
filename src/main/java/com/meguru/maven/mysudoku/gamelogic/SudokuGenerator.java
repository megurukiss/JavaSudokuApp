package com.meguru.maven.mysudoku.gamelogic;

import com.meguru.maven.mysudoku.userinterface.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    public static int[][] generateGame(){
        return unSolveGame(getUnsolvedGame());
    }
    public static int[][] getUnsolvedGame(){
        Random random = new Random(System.currentTimeMillis());
        int[][] board = new int[9][9];

        for(int value=1; value<=9; value++){
            int allocations = 0;
            int interrupt = 0;
            List<Coordinate> allocTracker = new ArrayList<>();
            int attempts = 0;

            while(allocations < 9){
                if (interrupt > 200){
                    allocTracker.forEach(coord -> {
                        board[coord.getX()][coord.getY()] = 0;
                    });
                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;
                    if (attempts > 500){
                        clearBoard(board);
                        attempts = 0;
                        value = 1;
                    }
                }
                int x = random.nextInt(9);
                int y = random.nextInt(9);
                if (board[x][y] == 0){
                    board[x][y] = value ;
                    if (SudokuSolver.checkValid(board)){
                        allocTracker.add(new Coordinate(x, y));
                        allocations++;
                    } else {
                        board[x][y] = 0;
                        interrupt++;
                    }
                }
            }
        }
        return board;
    }

    public static int[][] unSolveGame(int[][] board) {
        Random random = new Random(System.currentTimeMillis());
        boolean solvable = false;
        int[][] boardCopy=copyBoard(board);
        int[][] result=boardCopy;
        while (!solvable) {
            boardCopy = copyBoard(board);
            int count=0;
            while(count<40){
                int x = random.nextInt(9);
                int y = random.nextInt(9);
                if (boardCopy[x][y] != 0){
                    boardCopy[x][y] = 0;
                    count++;
                }
            }
            result= copyBoard(boardCopy);
            solvable = SudokuSolver.solvable(boardCopy);
        }
        return result;
    }


    private static void clearBoard(int[][] board){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                board[i][j] = 0;
            }
        }
    }

    public static int[][] copyBoard(int[][] board){
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

}
