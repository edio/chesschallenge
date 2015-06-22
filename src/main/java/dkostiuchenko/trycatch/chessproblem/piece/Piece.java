package dkostiuchenko.trycatch.chessproblem.piece;

import dkostiuchenko.trycatch.chessproblem.Board;

public interface Piece {
    /**
     * Whether piece canAttackFrom another piece on board if placed at specific file (vertical) and rank (horizontal)
     *
     * @param board the board piece is placed on
     * @param file  number of vertical piece is placed on
     * @param rank  number of horizontal piece is placed on
     * @return <tt>true</tt> if piece canAttackFrom any other piece on the board, <tt>false</tt> otherwise
     */
    boolean canAttackFrom(Board board, int file, int rank);

    /**
     * Piece code for memory efficient storage
     *
     * @return integer code > 0
     */
    int code();
}
