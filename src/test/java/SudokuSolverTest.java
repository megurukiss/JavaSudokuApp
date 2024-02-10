import com.meguru.maven.mysudoku.gamelogic.SudokuSolver;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SudokuSolverTest {

    @Test
    public void onTrueSolver(){
        int[][] board=SudokuSamples.getSimplePuzzle();
        assert SudokuSolver.solvable(board);
        assert SudokuSolver.checkSolved(board);
        assert Arrays.deepEquals(board, SudokuSamples.getSimpleSolution());

        board=SudokuSamples.getMiddlePuzzle();
        assert SudokuSolver.solvable(board);
        assert SudokuSolver.checkSolved(board);
        assert Arrays.deepEquals(board, SudokuSamples.getMiddleSolution());

        board=SudokuSamples.getHardPuzzle();
        assert SudokuSolver.solvable(board);
        assert SudokuSolver.checkSolved(board);
        System.out.println(Arrays.deepToString(board));
        assert Arrays.deepEquals(board, SudokuSamples.getHardSolution());
    }

    @Test
    public void checkSolvedTest(){
        int[][] solvedGame= SudokuSamples.getSimpleSolution();
        assert SudokuSolver.checkSolved(solvedGame);
        solvedGame= SudokuSamples.getMiddleSolution();
        assert SudokuSolver.checkSolved(solvedGame);
        solvedGame= SudokuSamples.getHardSolution();
        assert SudokuSolver.checkSolved(solvedGame);
        int[][] unsolvedGame= SudokuSamples.getSimplePuzzle();
        assert !SudokuSolver.checkSolved(unsolvedGame);
    }
}
