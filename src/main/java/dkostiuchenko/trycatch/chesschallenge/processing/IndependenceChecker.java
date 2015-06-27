package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;
import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

import java.util.Map;

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
    public boolean isIndependent(Board board, Map<Piece, int[]> index) {
        for (Map.Entry<Piece, int[]> indexEntry : index.entrySet()) {
            Piece p = indexEntry.getKey();
            int[] sqaures = indexEntry.getValue();

            for (int square : sqaures) {
                final int[] fileAndRank = board.getSquareCoordinates(square);
                if (p.canAttackFrom(board, fileAndRank[0], fileAndRank[1])) {
                    return false;
                }
            }
        }
        return true;
    }
}
