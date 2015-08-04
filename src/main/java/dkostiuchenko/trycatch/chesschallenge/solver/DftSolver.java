package dkostiuchenko.trycatch.chesschallenge.solver;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;
import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Solver that interprets pieces positions as nodes of a graph. Solver performs depth-first traversal over the graph.
 * <p/>
 * Placing pieces ony by one on the board, solver advances over nodes trying to find such nodes, that all pieces are
 * placed and no piece is attacked. As soon as solver finds a node, where at least one piece is attacked or no space
 * is left for new pieces, it returns back to parent node and proceeds with different path.
 * Thus, unsuccessful placements are discarded on very early stage without even being generated.
 */
public class DftSolver {

    private final SolutionCollector collector;
    private final Board board;
    private final Piece pieces[];
    private final Set<Object> backtrack = new HashSet<>();

    /**
     * Create new solver for a problem with specific
     *
     * @param board     board to solve for
     * @param pieces    pieces to solve for
     * @param collector collector to collect independent placements
     */
    public DftSolver(Board board, Piece[] pieces, SolutionCollector collector) {
        this.collector = collector;
        // create defencive copies
        this.board = Board.copyOf(board);
        this.pieces = Arrays.copyOf(pieces, pieces.length);
        Arrays.sort(this.pieces);
    }

    /**
     * Solve independence problem for a specific board and specific set of pieces.
     * <p/>
     * Board can be empty or contain some pieces, which will be considered, but won't be moved during solution.
     */
    public void solve() {
        traverse(0);
    }

    private void traverse(int pieceIndex) {
        for (int p = pieceIndex; p < pieces.length; p++) {
            int proceedWithPiece = placePiece(pieceIndex);
            if (proceedWithPiece < pieceIndex) {
                return;
            }
        }
        collector.collect(board);
    }

    private int placePiece(int pieceIndex) {
        for (Integer square : getAvailableSquares()) {
            Piece placedPiece = pieces[pieceIndex];
            board.set(placedPiece, square);
            if (!pieceCanAttack(placedPiece, square) && backtrack.add(board.getHashableKey())) {
                // proceed to next piece, if placed piece can't attack and we haven't considered that placement before
                traverse(pieceIndex + 1);
            }
            board.set(Piece.NONE, square);
        }
        return pieceIndex - 2;
    }

    private boolean pieceCanAttack(Piece piece, int square) {
        Collection<Integer> attacks = piece.coveredSquares(board, board.file(square), board.rank(square));

        for (int occupiedSquare : board.getOccupiedSquares()) {
            if (occupiedSquare != square && attacks.contains(occupiedSquare)) {
                return true;
            }
        }
        return false;
    }

    private Set<Integer> getAvailableSquares() {
        // at first we determine occupied squares and then available squares. This reduces GC load and is a bit faster
        Set<Integer> occupied = new HashSet<>();
        for (Integer square : board.getOccupiedSquares()) {
            occupied.addAll(board.get(square).coveredSquares(board, board.file(square), board.rank(square)));
        }

        Set<Integer> squares = new HashSet<>();
        for (int i = 0; i < board.length(); i++) {
            if (!occupied.contains(i)) {
                squares.add(i);
            }
        }
        return squares;
    }

}
