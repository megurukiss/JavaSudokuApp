package com.meguru.maven.mysudoku.gamelogic;

import com.meguru.maven.mysudoku.gamelogic.Solution;

import java.util.Arrays;

public class SudokuSolver {

    public static boolean solvable(int[][] board){
        Solution solution = new Solution();
        if(checkValid(board)) {
            return solution.solveSudoku(board);
        }
        return false;
    }

    public static int[][] solve(int[][] board){
        Solution solution = new Solution();
        solution.solveSudoku(board);
        return board;
    }

    public static boolean checkSolved(int[][] board){
        int[][] rows = new int[9][10];
        int[][] columns = new int[9][10];
        int[][] boxes = new int[9][10];
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                int num = board[i][j];
                if (num == 0){
                    return false;
                }
                int k = (i / 3) * 3 + j / 3;
                if (rows[i][num] == 1 || columns[j][num] == 1 || boxes[k][num] == 1){
                    return false;
                }
                rows[i][num] = 1;
                columns[j][num] = 1;
                boxes[k][num] = 1;
            }
        }
        for(int i = 0; i < 9; i++){
//            System.out.println(Arrays.toString(rows[i]));
            if (Arrays.stream(rows[i]).sum() != 9 || Arrays.stream(columns[i]).sum() != 9 || Arrays.stream(boxes[i]).sum() != 9){
                return false;
            }
        }
        return true;
    }

    public static boolean checkValid(int[][] board){
        int[][] rows = new int[9][10];
        int[][] columns = new int[9][10];
        int[][] boxes = new int[9][10];
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                int num = board[i][j];
                if (num != 0){
                    int k = (i / 3) * 3 + j / 3;
                    if (rows[i][num] == 1 || columns[j][num] == 1 || boxes[k][num] == 1){
                        return false;
                    }
                    rows[i][num] = 1;
                    columns[j][num] = 1;
                    boxes[k][num] = 1;
                }
            }
        }
        return true;
    }

}
