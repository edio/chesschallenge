package dkostiuchenko.trycatch.chesschallenge.chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds useful static methods to access board squares and groups of squares
 */
public final class BoardUtil {
    private BoardUtil() {
        // private constructor to prevent instantiation
    }

    /**
     * Count pieces which are placed on the diagonal that includes specific square and is parallel to long black
     * diagonal (from bottom-left to top-right corner)
     *
     * @return squares on diagonal
     */
    public static List<Integer> selectForwardDiagonal(Board board, int file, int rank) {
        final int diff = Math.min(file, rank);
        // traverse from bottom-left to top-right corner
        final int initialFile = file - diff;
        final int initialRank = rank - diff;
        final int initialIndex = board.square(initialFile, initialRank);

        List<Integer> squares = new ArrayList<>(Math.max(board.files(), board.ranks()));

        final int diagonalStep = board.ranks() + 1;
        final int steps = Math.min(board.files() - initialFile, board.ranks() - initialRank);
        for (int i = 0, square = initialIndex; i < steps; i++, square += diagonalStep) {
            squares.add(square);
        }

        return squares;
    }

    /**
     * Count pieces which are placed on the diagonal that includes specific square and is parallel to long white
     * diagonal
     *
     * @return number of pieces
     */
    public static List<Integer> selectBackDiagonal(Board board, int file, int rank) {
        final int diff = Math.min(board.files() - file - 1, rank);
        // traverse from bottom-right to top-left corner
        final int initialFile = file + diff;
        final int initialRank = rank - diff;
        final int initialIndex = board.square(initialFile, initialRank);

        List<Integer> squares = new ArrayList<>(Math.max(board.files(), board.ranks()));

        final int diagonalStep = board.ranks() - 1;
        final int steps = Math.min(initialFile, board.ranks() - initialRank - 1);
        for (int s = 0, square = initialIndex; s <= steps; s++, square -= diagonalStep) {
            squares.add(square);
        }

        return squares;
    }

    /**
     * Return list of squares located on specific rank on the board
     *
     * @param rank rank
     * @return list of squares
     */
    public static List<Integer> selectRank(Board board, int rank) {
        if (rank < 0 | rank >= board.ranks()) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        List<Integer> indices = new ArrayList<>(board.files());

        for (int step = 0; step < board.files(); step++) {
            indices.add(step * board.ranks() + rank);
        }
        return indices;
    }

    /**
     * Return list of squares located on specific file on the board
     *
     * @param file file
     * @return list of squares
     */
    public static List<Integer> selectFile(Board board, int file) {
        if (file < 0 | file >= board.files()) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        List<Integer> indices = new ArrayList<>(board.files());

        for (int step = 0; step < board.ranks(); step++) {
            indices.add(file * board.ranks() + step);
        }
        return indices;
    }

}
