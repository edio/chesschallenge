package dkostiuchenko.trycatch.chesschallenge.chess;

import org.junit.Test;

import static dkostiuchenko.trycatch.chesschallenge.BoardTruth.assertWithBoard;
import static dkostiuchenko.trycatch.chesschallenge.chess.BoardUtil.selectBackDiagonal;
import static dkostiuchenko.trycatch.chesschallenge.chess.BoardUtil.selectFile;
import static dkostiuchenko.trycatch.chesschallenge.chess.BoardUtil.selectForwardDiagonal;
import static dkostiuchenko.trycatch.chesschallenge.chess.BoardUtil.selectRank;

public class BoardUtilTest {

    @Test
    public void selectForwardDiagonal_portrait() {
        Board b = new Board(4, 6);

        assertWithBoard(b).that(selectForwardDiagonal(b, 2, 2)).containsExactly(0, 7, 14, 21);
        assertWithBoard(b).that(selectForwardDiagonal(b, 0, 2)).containsExactly(2, 9, 16, 23);
        assertWithBoard(b).that(selectForwardDiagonal(b, 2, 0)).containsExactly(12, 19);
        assertWithBoard(b).that(selectForwardDiagonal(b, 0, 5)).containsExactly(5);
        assertWithBoard(b).that(selectForwardDiagonal(b, 3, 0)).containsExactly(18);
    }

    @Test
    public void selectForwardDiagonal_landscape() {
        Board b = new Board(6, 4);

        assertWithBoard(b).that(selectForwardDiagonal(b, 3, 3)).containsExactly(0, 5, 10, 15);
        assertWithBoard(b).that(selectForwardDiagonal(b, 3, 1)).containsExactly(8, 13, 18, 23);
        assertWithBoard(b).that(selectForwardDiagonal(b, 0, 2)).containsExactly(2, 7);
        assertWithBoard(b).that(selectForwardDiagonal(b, 0, 3)).containsExactly(3);
        assertWithBoard(b).that(selectForwardDiagonal(b, 5, 0)).containsExactly(20);
    }

    @Test
    public void selectBackDiagonal_portrait() {
        Board b = new Board(4, 6);

        assertWithBoard(b).that(selectBackDiagonal(b, 2, 1)).containsExactly(18, 13, 8, 3);
        assertWithBoard(b).that(selectBackDiagonal(b, 3, 2)).containsExactly(20, 15, 10, 5);
        assertWithBoard(b).that(selectBackDiagonal(b, 2, 0)).containsExactly(12, 7, 2);
        assertWithBoard(b).that(selectBackDiagonal(b, 0, 0)).containsExactly(0);
        assertWithBoard(b).that(selectBackDiagonal(b, 3, 5)).containsExactly(23);
    }

    @Test
    public void selectBackDiagonal_landscape() {
        Board b = new Board(6, 4);

        assertWithBoard(b).that(selectBackDiagonal(b, 4, 1)).containsExactly(20, 17, 14, 11);
        assertWithBoard(b).that(selectBackDiagonal(b, 5, 2)).containsExactly(22, 19);
        assertWithBoard(b).that(selectBackDiagonal(b, 1, 1)).containsExactly(8, 5, 2);
        assertWithBoard(b).that(selectBackDiagonal(b, 0, 0)).containsExactly(0);
        assertWithBoard(b).that(selectBackDiagonal(b, 5, 3)).containsExactly(23);
    }

    @Test
    public void selectFile_portrait() {
        Board b = new Board(4, 6);

        assertWithBoard(b).that(selectFile(b, 1)).containsExactly(6, 7, 8, 9, 10, 11);
    }

    @Test
    public void selectFile_landscape() {
        Board b = new Board(6, 4);

        assertWithBoard(b).that(selectFile(b, 1)).containsExactly(4, 5, 6, 7);
    }

    @Test
    public void selectRank_portrait() {
        Board b = new Board(4, 6);

        assertWithBoard(b).that(selectRank(b, 1)).containsExactly(1, 7, 13, 19);
    }

    @Test
    public void selectRank_landscape() {
        Board b = new Board(6, 4);

        assertWithBoard(b).that(selectRank(b, 1)).containsExactly(1, 5, 9, 13, 17, 21);
    }
}