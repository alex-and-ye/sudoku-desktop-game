package sudoku.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import sudoku.model.exceptions.FillingBoardSudokuException;
import sudoku.model.models.SudokuBoard;
import sudoku.model.solver.BacktrackingSudokuSolver;

class DifficultyEnumTest {
    @ParameterizedTest
    @CsvSource({
        "EASY,30",
        "MEDIUM,50",
        "HARD,70"
    })
    void shouldClearExpectedNumberOfFieldsForEachDifficulty(
            DifficultyEnum difficulty,
            int expectedClearedFields
    ) throws FillingBoardSudokuException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        assertEquals(0, countEmptyFields(board));

        difficulty.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(board);

        assertEquals(expectedClearedFields, countEmptyFields(board));
        assertEquals(SudokuBoard.BOARD_SIZE * SudokuBoard.BOARD_SIZE - expectedClearedFields, countFilledFields(board));
    }

    private int countEmptyFields(SudokuBoard board) {
        int emptyFields = 0;
        for (int row = 0; row < SudokuBoard.BOARD_SIZE; row++) {
            for (int column = 0; column < SudokuBoard.BOARD_SIZE; column++) {
                if (board.getField(column, row).getValue() == 0) {
                    emptyFields++;
                }
            }
        }
        return emptyFields;
    }

    private int countFilledFields(SudokuBoard board) {
        return SudokuBoard.BOARD_SIZE * SudokuBoard.BOARD_SIZE - countEmptyFields(board);
    }
}
