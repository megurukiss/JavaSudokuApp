package com.meguru.maven.mysudoku.gamelogic;

class Solution{
    private final int n = 3;
    private final int N = n * n;

    int [][] rows = new int[N][N + 1];
    int [][] columns = new int[N][N + 1];
    int [][] boxes = new int[N][N + 1];

    int[][] board;

    boolean sudokuSolved = false;

    public boolean couldPlace(int d, int row, int col) {
    /*
    Check if one could place a number d in (row, col) cell
    */
        int idx = (row / n ) * n + col / n;
        return rows[row][d] + columns[col][d] + boxes[idx][d] == 0;
    }

    public void placeNumber(int d, int row, int col) {
    /*
    Place a number d in (row, col) cell
    */
        int idx = (row / n ) * n + col / n;

        rows[row][d]++;
        columns[col][d]++;
        boxes[idx][d]++;
        board[row][col] = d;
    }

    public void removeNumber(int d, int row, int col) {
    /*
    Remove a number that didn't lead to a solution
    */
        int idx = (row / n ) * n + col / n;
        rows[row][d]--;
        columns[col][d]--;
        boxes[idx][d]--;
        board[row][col] = 0;
    }

    public void placeNextNumbers(int row, int col) {
    /*
    Call backtrack function in recursion
    to continue to place numbers
    till the moment we have a solution
    */
        //If we're in the last cell
        // that means we have the solution
        if ((col == N - 1) && (row == N - 1)) {
            sudokuSolved = true;
        }
        // if not yet
        else {
            //If we're at the end of the row
            // go to the next row
            if (col == N - 1) backtrack(row + 1, 0);
                // go to the next column
            else backtrack(row, col + 1);
        }
    }

    public boolean backtrack(int row, int col) {
    /*
    Backtracking
    */
        //If the cell is empty
        if (board[row][col] == 0) {
            // iterate over all numbers from 1 to 9
            for (int d = 1; d < 10; d++) {
                if (couldPlace(d, row, col)) {
                    placeNumber(d, row, col);
                    placeNextNumbers(row, col);
                    // If sudoku is solved, there is no need to backtrack
                    // since the single unique solution is promised
                    if(sudokuSolved){
                        return true;
                    }
                    else {
                        removeNumber(d, row, col);
                    };
                }
            }
            return false;
        }
        else placeNextNumbers(row, col);
        return sudokuSolved;
    }

    public boolean solveSudoku(int[][] board) {
        this.board = board;

        // init rows, columns, and boxes
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int num = board[i][j];
                if (num != 0) {
                    placeNumber(num, i, j);
                }
            }
        }
        return backtrack(0, 0);
    }
}
