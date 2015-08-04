package dkostiuchenko.trycatch.chesschallenge.chess;

import java.util.HashSet;
import java.util.Set;

/**
 * All pieces are stateless and can be implemented as singletons. Enum is a perfect way to do that.
 */
public enum Piece {
    NONE("_"),
    // Pieces are declared in desc order of their average attacking force.
    // So when sorted, ones attacking larger part of the field come first
    // This may be considered as micro optimisation
    QUEEN("Q") {
        @Override
        public Set<Integer> coveredSquares(Board board, int file, int rank) {
            Set<Integer> squares = new HashSet<>();
            squares.addAll(BoardUtil.selectFile(board, file));
            squares.addAll(BoardUtil.selectRank(board, rank));
            squares.addAll(BoardUtil.selectForwardDiagonal(board, file, rank));
            squares.addAll(BoardUtil.selectBackDiagonal(board, file, rank));
            return squares;
        }
    },
    ROOK("R") {
        @Override
        public Set<Integer> coveredSquares(Board board, int file, int rank) {
            Set<Integer> squares = new HashSet<>();
            squares.addAll(BoardUtil.selectFile(board, file));
            squares.addAll(BoardUtil.selectRank(board, rank));
            return squares;
        }
    },
    BISHOP("B") {
        @Override
        public Set<Integer> coveredSquares(Board board, int file, int rank) {
            Set<Integer> squares = new HashSet<>();
            squares.addAll(BoardUtil.selectForwardDiagonal(board, file, rank));
            squares.addAll(BoardUtil.selectBackDiagonal(board, file, rank));
            return squares;
        }
    },
    KING("K") {
        @Override
        public Set<Integer> coveredSquares(Board board, int file, int rank) {
            Set<Integer> squares = new HashSet<>();
            squares.add(board.square(file, rank));

            if (board.isValidSquare(file - 1, rank + 1)) squares.add(board.square(file - 1, rank + 1));
            if (board.isValidSquare(file, rank + 1)) squares.add(board.square(file, rank + 1));
            if (board.isValidSquare(file + 1, rank + 1)) squares.add(board.square(file + 1, rank + 1));
            if (board.isValidSquare(file - 1, rank)) squares.add(board.square(file - 1, rank));
            if (board.isValidSquare(file + 1, rank)) squares.add(board.square(file + 1, rank));
            if (board.isValidSquare(file - 1, rank - 1)) squares.add(board.square(file - 1, rank - 1));
            if (board.isValidSquare(file, rank - 1)) squares.add(board.square(file, rank - 1));
            if (board.isValidSquare(file + 1, rank - 1)) squares.add(board.square(file + 1, rank - 1));

            return squares;
        }
    },
    KNIGHT("N") {
        @Override
        public Set<Integer> coveredSquares(Board board, int file, int rank) {
            Set<Integer> squares = new HashSet<>();
            squares.add(board.square(file, rank));

            if (board.isValidSquare(file - 2, rank + 1)) squares.add(board.square(file - 2, rank + 1));
            if (board.isValidSquare(file - 1, rank + 2)) squares.add(board.square(file - 1, rank + 2));
            if (board.isValidSquare(file + 1, rank + 2)) squares.add(board.square(file + 1, rank + 2));
            if (board.isValidSquare(file + 2, rank + 1)) squares.add(board.square(file + 2, rank + 1));
            if (board.isValidSquare(file + 2, rank - 1)) squares.add(board.square(file + 2, rank - 1));
            if (board.isValidSquare(file + 1, rank - 2)) squares.add(board.square(file + 1, rank - 2));
            if (board.isValidSquare(file - 1, rank - 2)) squares.add(board.square(file - 1, rank - 2));
            if (board.isValidSquare(file - 2, rank - 1)) squares.add(board.square(file - 2, rank - 1));

            return squares;
        }
    };

    private String symbol;

    Piece(String symbol) {
        this.symbol = symbol;
    }


    /**
     * Return collection of the squares by this piece if placed on the specific square of the board plus square occupied
     * by piece itself
     */
    public Set<Integer> coveredSquares(Board board, int file, int rank) {
        // to be overridden by instances
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return symbol;
    }

    public byte byteValue() {
        return (byte) ordinal();
    }

    public static Piece fromByte(byte byteValue) {
        return Piece.values()[byteValue];
    }

}
