package dkostiuchenko.trycatch.chesschallenge.chess;

import dkostiuchenko.trycatch.chesschallenge.permutation.Permutation;

import java.util.Arrays;
import java.util.Objects;

/**
 * Board.
 * <p/>
 * Indexed from bottom-left corner, as real chess-board.
 */
public class Board {

    private final int files;
    private final int ranks;
    private Permutation permutation;

    /**
     * Create new empty board
     */
    public Board(final int files, final int ranks) {
        this.files = files;
        this.ranks = ranks;

        Piece[] squares = new Piece[files * ranks];
        Arrays.fill(squares, Piece.NONE);
        this.permutation = new Permutation(squares);
    }

    /**
     * Constructor for {@link BoardFactory}
     */
    Board(final int files, final int ranks, final Permutation permutation) {
        if (permutation.length() != files * ranks) {
            throw new IllegalArgumentException("Permutation length is not equal to files*ranks");
        }
        this.files = files;
        this.ranks = ranks;
        this.permutation = permutation;
    }

    /**
     * Visit certain pieces types on this board. Method only works for not {@link Piece#NONE} pieces. {@link
     * Piece#NONE} won't be visited even if passed.
     *
     * @param visitor visitor instance
     * @param pieces  desired types of pieces
     * @return {@code true} as soon as {@code visitor} returns {@code true}, {@code false} otherwise
     */
    public boolean visit(BoardVisitor visitor, Piece... pieces) {
        for (Piece piece : pieces) {
            int[] indices = permutation.indices(piece);
            if (indices == null) {
                continue;
            }
            for (int index : indices) {
                int[] fr = getSquareCoordinates(index);
                boolean isEnough = visitor.onPiece(this, piece, fr[0], fr[1]);
                if (isEnough) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Validate that square is on the board. Throws IllegalArgumentException if it's not
     *
     * @param file file of the square
     * @param rank rank of the square
     * @throws IllegalArgumentException if square is invalid (i.e., not on the board)
     */
    public void validateSquare(int file, int rank) throws IllegalArgumentException {
        if (!isValidSquare(file, rank)) {
            throw new IllegalArgumentException("Square " + file + "x" + rank + " is not on board " + files + "x" +
                    ranks);
        }
    }

    /**
     * Get coordinates of the square by its name
     * <pre>
     *     3| 7| 11| 15|
     *     2| 6| 10| 14|
     *     1| 5|  9| 13|
     *     0| 4|  8| 12|
     * </pre>
     *
     * @param square square number
     * @return 2-elements array [file, rank]
     * @throws IllegalArgumentException if square is invalid (i.e., not on the board)
     */
    public int[] getSquareCoordinates(int square) throws IllegalArgumentException {
        int file = square / ranks;
        int rank = square % ranks;
        return new int[]{file, rank};
    }

    /**
     * Checks, whether square is on board
     *
     * @param file file of the square
     * @param rank rank of the square
     * @return {@code true} if square is on board, {@code false} otherwise
     */
    public boolean isValidSquare(int file, int rank) {
        return file >= 0 & file < files & rank >= 0 & rank < ranks;
    }

    /**
     * Get piece located on specified file and rank
     *
     * @param file file of the square
     * @param rank rank of the square
     * @return Piece instance
     * @throws IllegalArgumentException if {@code file} or {@code rank} is out of board bounds
     */
    public Piece get(int file, int rank) throws IllegalArgumentException {
        final int index = getIndex(file, rank);
        return permutation.get(index);
    }

    /**
     * Get piece located on specified square. Squares are numbered from bottom-left corner
     * <pre>
     *     3| 7| 11| 15|
     *     2| 6| 10| 14|
     *     1| 5|  9| 13|
     *     0| 4|  8| 12|
     * </pre>
     *
     * @param square square number
     * @return piece instance
     * @throws IllegalArgumentException if {@code square} value is illegal
     */
    public Piece get(int square) throws IllegalArgumentException {
        if (square < 0 | square >= permutation.length()) {
            throw new IllegalArgumentException("Square number is illegal");
        }
        return permutation.get(square);
    }

    /**
     * Checks, whether board has some piece on specified square
     *
     * @param file file of the square
     * @param rank rank of the square
     * @return {@code true} if there's some piece on specific square, {@code false} otherwise
     * @throws IllegalArgumentException
     */
    public boolean hasPiece(int file, int rank) throws IllegalArgumentException {
        final int index = getIndex(file, rank);
        return permutation.get(index) != Piece.NONE;
    }

    /**
     * Set piece to the board. Setting {@code null} is equivalent to removing piece from specific square
     *
     * @param piece piece to set
     * @param file  number of vertical
     * @param rank  number of horizontal
     */
    public void set(Piece piece, int file, int rank) {
        if (piece == null) {
            throw new NullPointerException();
        }
        this.permutation.set(piece, getIndex(file, rank));
    }

    /**
     * Count number of pieces on specified file
     *
     * @param file file
     * @return number of pieces
     */
    public int countFile(int file) {
        if (file < 0 | file >= files) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        int count = 0;

        final int initialIndex = file * ranks;
        final int finalIndex = initialIndex + ranks;
        for (int index = initialIndex; index < finalIndex; index++) {
            if (permutation.get(index) != Piece.NONE) {
                count++;
            }
        }
        return count;
    }

    /**
     * Count number of pieces on specified rank
     *
     * @param rank rank
     * @return number of pieces
     */
    public int countRank(int rank) {
        if (rank < 0 | rank >= ranks) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        int count = 0;

        final int finalIndex = files * (ranks - 1) + rank;
        final int increment = ranks;
        for (int index = rank; index <= finalIndex; index += increment) {
            if (permutation.get(index) != Piece.NONE) {
                count++;
            }
        }
        return count;
    }

    /**
     * Count pieces which are placed on the diagonal that includes specific square and is parallel to long black
     * diagonal
     *
     * @return number of pieces
     */
    public int countForwardDiagonal(int file, int rank) {
        final int initialIndex = getIndex(file, rank);

        int count = 0;

        if (permutation.get(initialIndex) != Piece.NONE) {
            count++;
        }

        int stepSize = ranks + 1;

        int stepsUp = Math.min(files - file - 1, ranks - rank - 1);
        for (int s = 0, index = initialIndex + stepSize; s < stepsUp; s++, index += stepSize) {
            if (permutation.get(index) != Piece.NONE) {
                count++;
            }
        }

        int stepsDown = Math.min(file, rank);
        for (int s = 0, index = initialIndex - stepSize; s < stepsDown; s++, index -= stepSize) {
            if (permutation.get(index) != Piece.NONE) {
                count++;
            }
        }
        return count;
    }

    /**
     * Count pieces which are placed on the diagonal that includes specific square and is parallel to long white
     * diagonal
     *
     * @return number of pieces
     */
    public int countBackDiagonal(int file, int rank) {
        final int initialIndex = getIndex(file, rank);

        int count = 0;

        if (permutation.get(initialIndex) != Piece.NONE) {
            count++;
        }

        int stepSize = ranks - 1;

        int stepsUp = Math.min(files - file - 1, rank);
        for (int s = 0, index = initialIndex + stepSize; s < stepsUp; s++, index += stepSize) {
            if (permutation.get(index) != Piece.NONE) {
                count++;
            }
        }

        int stepsDown = Math.min(file, ranks - rank - 1);
        for (int s = 0, index = initialIndex - stepSize; s < stepsDown; s++, index -= stepSize) {
            if (permutation.get(index) != Piece.NONE) {
                count++;
            }
        }
        return count;
    }

    /**
     * Size across horizontal dimension
     *
     * @return total number of files
     */
    public int files() {
        return files;
    }

    /**
     * Size across vertical dimension
     *
     * @return total number of ranks
     */
    public int ranks() {
        return ranks;
    }

    // visible for testing
    int getIndex(int file, int rank) {
        this.validateSquare(file, rank);
        return file * (ranks) + rank;
    }

    Permutation getPermutation() {
        return this.permutation;
    }

    void setPermutation(Permutation permutation) {
        if (permutation.length() != this.files * this.ranks) {
            throw new IllegalArgumentException("Permutation length is not equal to files*ranks");
        }
        this.permutation = permutation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(permutation.length());
        for (int r = ranks - 1; r >= 0; r--) {
            for (int f = 0; f < files; f++) {
                final int index = getIndex(f, r);
                sb.append(permutation.get(index)).append('|');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(files, board.files) &&
                Objects.equals(ranks, board.ranks) &&
                permutation.equals(board.permutation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(files, ranks) * 31 + permutation.hashCode();
    }

    /**
     * Copies another board
     *
     * @return always new {@link Board} instance
     */
    public static Board copyOf(Board toCopy) {
        return new Board(toCopy.files, toCopy.ranks, Permutation.copyOf(toCopy.permutation));
    }

    /**
     * Interfaces to be implemented by classes intended to visit certain pieces on a {@link Board} without scanning
     * through all board
     */
    public interface BoardVisitor {
        /**
         * Is called on this visitor of it's passed to {@link Board#visit(BoardVisitor, Piece...)} for every passed
         * {@link Piece}.
         * <p/>
         * As soon as this method returns {@code true}, {@link Board#visit(BoardVisitor, Piece...)} returns {@code
         * true} at once. No more pieces will be visited.
         * If method never returns {@code true}, all pieces will be visited, and {@link Board#visit(BoardVisitor,
         * Piece...)}
         * will return false
         *
         * @param board board instance this visitor is visiting
         * @param piece piece visitor is currently visiting
         * @param file  file of the visited piece
         * @param rank  rank of the visited piece
         * @return {@code true} if visiting should stop and  {@link Board#visit(BoardVisitor, Piece...)} return,
         * {@code false} otherwise
         */
        boolean onPiece(Board board, Piece piece, int file, int rank);
    }
}
