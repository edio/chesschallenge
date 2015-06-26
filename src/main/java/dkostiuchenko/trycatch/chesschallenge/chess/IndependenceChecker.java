package dkostiuchenko.trycatch.chesschallenge.chess;

/**
 * Asserts that permutation is independent
 */
public class IndependenceChecker {
    /**
     * Checks whether pieces on the board are independent, i.e. there is no piece which may attack or be attacked
     *
     * @param board board instance with pieces
     * @return <tt>true</tt> if there is no piece under attack on board, <tt>false</tt> otherwise
     */
    public boolean isIndependent(Board board) {
        for (int file = 0; file < board.files(); file++) {
            for (int rank = 0; rank < board.ranks(); rank++) {
                final Piece piece = board.get(file, rank);
                if (piece != Piece.NONE && piece.canAttackFrom(board, file, rank)) {
                    return false;
                }
            }
        }
        return true;
    }
}
