package dkostiuchenko.trycatch.chessproblem;

public interface Piece {
    /**
     * Whether piece attacks another piece on board if placed at specific file (vertical) and rank (horizontal)
     *
     * @param board the board piece is placed on
     * @param file  number of vertical piece is placed on
     * @param rank  number of horizontal piece is placed on
     * @return <tt>true</tt> if piece attacks any other piece on the board, <tt>false</tt> otherwise
     */
    boolean attacks(Board board, int file, int rank);
}
