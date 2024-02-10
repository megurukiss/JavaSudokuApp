package com.meguru.maven.mysudoku.gamelogic;

import java.io.Serializable;

public class SudokuGame implements Serializable {
    private final GameState gameState;
    private final int[][] grid;

    public static final int GRID_BOUNDARY = 9;

    public SudokuGame(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.grid = gridState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int[][] getGridState() {
        return grid;
    }

    public int[][] getCopyOfGridState() {
        int[][] deepCopy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            deepCopy[i] = grid[i].clone();
        }
        return deepCopy;
    }

    public static boolean isGridFull(int[][] grid) {
        for (int i = 0; i < GRID_BOUNDARY; i++) {
            for (int j = 0; j < GRID_BOUNDARY; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
