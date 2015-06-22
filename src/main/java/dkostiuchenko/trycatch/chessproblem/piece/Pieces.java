package dkostiuchenko.trycatch.chessproblem.piece;

import dkostiuchenko.trycatch.chessproblem.Board;

/**
 * All pieces are stateless and can be implemented as singletons. Enum is a perfect way to do that.
 */
public enum Pieces implements Piece {
    BISHOP("B") {
        @Override
        public boolean canAttackFrom(Board board, int file, int rank) {
            // attacks whenever there are pieces on any of diagonals
            return board.countForwardDiagonal(file, rank) > 1 || board.countBackDiagonal(file, rank) > 1;
        }
    },
    ROOK("R") {
        @Override
        public boolean canAttackFrom(Board board, int file, int rank) {
            // attacks whenever there are pieces on the file or rank
            return board.countFile(file) > 1 || board.countRank(rank) > 1;
        }
    },
    QUEEN("Q") {
        @Override
        public boolean canAttackFrom(Board board, int file, int rank) {
            // attacks if ROOK or BISHOP can attack from the same square
            return ROOK.canAttackFrom(board, file, rank) || BISHOP.canAttackFrom(board, file, rank);
        }
    },
    KING("K") {
        @Override
        public boolean canAttackFrom(Board board, int file, int rank) {
            board.validateSquare(file, rank);

            boolean northWest = board.isValidSquare(file - 1, rank + 1) && board.hasPiece(file - 1, rank + 1);
            boolean north = board.isValidSquare(file, rank + 1) && board.hasPiece(file, rank + 1);
            boolean northEast = board.isValidSquare(file + 1, rank + 1) && board.hasPiece(file + 1, rank + 1);
            boolean west = board.isValidSquare(file - 1, rank) && board.hasPiece(file - 1, rank);
            boolean east = board.isValidSquare(file + 1, rank) && board.hasPiece(file + 1, rank);
            boolean southWest = board.isValidSquare(file - 1, rank - 1) && board.hasPiece(file - 1, rank - 1);
            boolean south = board.isValidSquare(file, rank - 1) && board.hasPiece(file, rank - 1);
            boolean southEast = board.isValidSquare(file + 1, rank - 1) && board.hasPiece(file + 1, rank - 1);

            return northWest | north | northEast | west | east | southWest | south | southEast;
        }
    },
    KNIGHT("N") {
        @Override
        public boolean canAttackFrom(Board board, int file, int rank) {
            board.validateSquare(file, rank);

            boolean westNorth = board.isValidSquare(file - 2, rank + 1) && board.hasPiece(file - 2, rank + 1);
            boolean northWest = board.isValidSquare(file - 1, rank + 2) && board.hasPiece(file - 1, rank + 2);
            boolean northEast = board.isValidSquare(file + 1, rank + 2) && board.hasPiece(file + 1, rank + 2);
            boolean eastNorth = board.isValidSquare(file + 2, rank + 1) && board.hasPiece(file + 2, rank + 1);
            boolean eastSouth = board.isValidSquare(file + 2, rank - 1) && board.hasPiece(file + 2, rank - 1);
            boolean southEast = board.isValidSquare(file + 1, rank - 2) && board.hasPiece(file + 1, rank - 2);
            boolean southWest = board.isValidSquare(file - 1, rank - 2) && board.hasPiece(file - 1, rank - 2);
            boolean westSouth = board.isValidSquare(file - 2, rank - 1) && board.hasPiece(file - 2, rank - 1);

            return westNorth | northWest | northEast | eastNorth | eastSouth | southEast | southWest | westSouth;
        }
    };

    private String symbol;

    Pieces(String symbol) {
        this.symbol = symbol;
    }

    public int code() {
        return ordinal() + 1;
    }

    public static Piece ofCode(int code) {
        if (code < 1 | code > values().length) {
            throw new IllegalArgumentException("Unknown piece code " + code);
        }
        return values()[code - 1];
    }

    @Override
    public String toString() {
        return symbol;
    }
}
