package dkostiuchenko.trycatch.chesschallenge.solver;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;
import dkostiuchenko.trycatch.chesschallenge.chess.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static dkostiuchenko.trycatch.chesschallenge.chess.Piece.KING;
import static dkostiuchenko.trycatch.chesschallenge.chess.Piece.KNIGHT;
import static dkostiuchenko.trycatch.chesschallenge.chess.Piece.ROOK;

public class DftSolverTest {

    private Collector collector;

    @Before
    public void setup() {
        collector = new Collector();
    }

    @Test
    public void resultFromTask_3x3() {
        new DftSolver(
                new Board(3, 3),
                new Piece[]{KING, KING, ROOK},
                collector)
                .solve();

        LinkedList<Board> expectedSolutions = new LinkedList<>();

        expectedSolutions.add(new Board(3, 3));
        expectedSolutions.getLast().set(KING, 0, 2);
        expectedSolutions.getLast().set(KING, 2, 2);
        expectedSolutions.getLast().set(ROOK, 1, 0);

        expectedSolutions.add(new Board(3, 3));
        expectedSolutions.getLast().set(KING, 0, 0);
        expectedSolutions.getLast().set(KING, 0, 2);
        expectedSolutions.getLast().set(ROOK, 2, 1);

        expectedSolutions.add(new Board(3, 3));
        expectedSolutions.getLast().set(KING, 2, 2);
        expectedSolutions.getLast().set(KING, 2, 0);
        expectedSolutions.getLast().set(ROOK, 0, 1);

        expectedSolutions.add(new Board(3, 3));
        expectedSolutions.getLast().set(KING, 0, 0);
        expectedSolutions.getLast().set(KING, 2, 0);
        expectedSolutions.getLast().set(ROOK, 1, 2);

        assertThat(collector.solutions).containsExactlyElementsIn(expectedSolutions);
    }

    @Test
    public void resultFromTask_4x4() {
        new DftSolver(
                new Board(4, 4),
                new Piece[]{KNIGHT, KNIGHT, KNIGHT, KNIGHT, ROOK, ROOK},
                collector)
                .solve();

        LinkedList<Board> expectedSolutions = new LinkedList<>();

        expectedSolutions.add(new Board(4, 4));
        expectedSolutions.getLast().set(ROOK, 0, 0);
        expectedSolutions.getLast().set(ROOK, 2, 2);
        expectedSolutions.getLast().set(KNIGHT, 1, 1);
        expectedSolutions.getLast().set(KNIGHT, 3, 1);
        expectedSolutions.getLast().set(KNIGHT, 1, 3);
        expectedSolutions.getLast().set(KNIGHT, 3, 3);

        expectedSolutions.add(new Board(4, 4));
        expectedSolutions.getLast().set(ROOK, 0, 2);
        expectedSolutions.getLast().set(ROOK, 2, 0);
        expectedSolutions.getLast().set(KNIGHT, 1, 1);
        expectedSolutions.getLast().set(KNIGHT, 3, 1);
        expectedSolutions.getLast().set(KNIGHT, 1, 3);
        expectedSolutions.getLast().set(KNIGHT, 3, 3);

        expectedSolutions.add(new Board(4, 4));
        expectedSolutions.getLast().set(ROOK, 0, 3);
        expectedSolutions.getLast().set(ROOK, 2, 1);
        expectedSolutions.getLast().set(KNIGHT, 1, 0);
        expectedSolutions.getLast().set(KNIGHT, 3, 0);
        expectedSolutions.getLast().set(KNIGHT, 1, 2);
        expectedSolutions.getLast().set(KNIGHT, 3, 2);

        expectedSolutions.add(new Board(4, 4));
        expectedSolutions.getLast().set(ROOK, 0, 1);
        expectedSolutions.getLast().set(ROOK, 2, 3);
        expectedSolutions.getLast().set(KNIGHT, 1, 0);
        expectedSolutions.getLast().set(KNIGHT, 3, 0);
        expectedSolutions.getLast().set(KNIGHT, 1, 2);
        expectedSolutions.getLast().set(KNIGHT, 3, 2);

        expectedSolutions.add(new Board(4, 4));
        expectedSolutions.getLast().set(ROOK, 1, 3);
        expectedSolutions.getLast().set(ROOK, 3, 1);
        expectedSolutions.getLast().set(KNIGHT, 0, 0);
        expectedSolutions.getLast().set(KNIGHT, 2, 0);
        expectedSolutions.getLast().set(KNIGHT, 0, 2);
        expectedSolutions.getLast().set(KNIGHT, 2, 2);

        expectedSolutions.add(new Board(4, 4));
        expectedSolutions.getLast().set(ROOK, 1, 1);
        expectedSolutions.getLast().set(ROOK, 3, 3);
        expectedSolutions.getLast().set(KNIGHT, 0, 0);
        expectedSolutions.getLast().set(KNIGHT, 2, 0);
        expectedSolutions.getLast().set(KNIGHT, 0, 2);
        expectedSolutions.getLast().set(KNIGHT, 2, 2);

        expectedSolutions.add(new Board(4, 4));
        expectedSolutions.getLast().set(ROOK, 1, 0);
        expectedSolutions.getLast().set(ROOK, 3, 2);
        expectedSolutions.getLast().set(KNIGHT, 0, 1);
        expectedSolutions.getLast().set(KNIGHT, 2, 1);
        expectedSolutions.getLast().set(KNIGHT, 0, 3);
        expectedSolutions.getLast().set(KNIGHT, 2, 3);

        expectedSolutions.add(new Board(4, 4));
        expectedSolutions.getLast().set(ROOK, 1, 2);
        expectedSolutions.getLast().set(ROOK, 3, 0);
        expectedSolutions.getLast().set(KNIGHT, 0, 1);
        expectedSolutions.getLast().set(KNIGHT, 2, 1);
        expectedSolutions.getLast().set(KNIGHT, 0, 3);
        expectedSolutions.getLast().set(KNIGHT, 2, 3);

        assertThat(collector.solutions).containsExactlyElementsIn(expectedSolutions);
    }

    class Collector implements SolutionCollector {
        List<Board> solutions = new LinkedList<>();

        @Override
        public void collect(Board board) {
            solutions.add(Board.copyOf(board));
        }
    }
}
