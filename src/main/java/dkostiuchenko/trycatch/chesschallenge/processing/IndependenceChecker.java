package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;
import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

/**
 * Asserts that permutation is independent
 */
public class IndependenceChecker implements Board.BoardVisitor {

    /**
     * Checks whether pieces on the board are independent, i.e. there is no piece which may attack or be attacked
     *
     * @param board board instance with pieces
     * @return {@code true} if there is no piece under attack on board, {@code false} otherwise
     */
    public boolean isIndependent(Board board) {
        boolean pieceThreated = board.visit(this, Piece.ROOK, Piece.QUEEN, Piece.KING, Piece.BISHOP, Piece.KNIGHT);
        return !pieceThreated;
    }

    @Override
    public boolean onPiece(Board b, Piece p, int file, int rank) {
        if (p.canAttackFrom(b, file, rank)) {
            return true;
        }
        return false;
    }
}
