package dkostiuchenko.trycatch.chesschallenge.chess;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Chess board. Is characterized by size (number files and ranks). Holds pieces.
 * Squares can be accessed by file and rank (through helper conversion methods) or by index
 * <pre>
 *  rank
 *   3  |  3|  7| 11| 15|
 *   2  |  2|  6| 10| 14|
 *   1  |  1|  5|  9| 13|
 *   0  |  0|  4|  8| 12|
 *      -----------------
 *  file  0   1   2   3
 * </pre>
 */
public final class Board {

    private final int files;
    private final int ranks;
    // on 64-bit jvm Enums are considerably slower and 8 times larger than byte
    private final byte[] squares;
    // tracks squares that are not occupied by any piece yet
    private final Set<Integer> occupiedSquares = new HashSet<>();

    public Board(final int files, final int ranks) {
        this.files = files;
        this.ranks = ranks;
        this.squares = new byte[files * ranks];
        Arrays.fill(squares, Piece.NONE.byteValue());
    }

    /** Copy constructor */
    private Board(final int files, final int ranks, final byte[] squares, final Collection<Integer> occupiedSquares) {
        if (squares.length != files * ranks) {
            throw new IllegalArgumentException("Squares array length is not equal to files*ranks");
        }
        this.files = files;
        this.ranks = ranks;
        this.squares = Arrays.copyOf(squares, squares.length);
        this.occupiedSquares.addAll(occupiedSquares);
    }

    /**
     * Translate file and rank to square number. Idiomatic way of usage
     * <pre>
     *     Board b = new Board(files, ranks);
     *     b.set(piece, b.square(f, r));
     *     b.get(b.square(f, r));
     * </pre>
     *
     * @param file file of the square
     * @param rank rank of the square
     * @return the square
     * @throws IllegalArgumentException if no such square exists
     */
    public int square(int file, int rank) throws IllegalArgumentException {
        validateSquare(file, rank);
        return file * (ranks) + rank;
    }

    /**
     * Get file of the square by its number
     *
     * @param square the square
     * @return file of the square
     * @throws IllegalArgumentException if square is invalid (i.e., not on the board)
     */
    public int file(int square) throws IllegalArgumentException {
        validateSquare(square);
        int file = square / ranks;
        return file;
    }

    /**
     * Get rank of the square by its number
     *
     * @param square the square
     * @return rank of the square
     * @throws IllegalArgumentException if square is invalid (i.e., not on the board)
     */
    public int rank(int square) throws IllegalArgumentException {
        validateSquare(square);
        int rank = square % ranks;
        return rank;
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
     * Get piece, if any, located on specified square
     *
     * @param square the square
     * @return Piece instance
     */
    public Piece get(int square) {
        validateSquare(square);
        return Piece.fromByte(squares[square]);
    }

    /**
     * Shortcut for {@code get(square(file, rank))}
     */
    public Piece get(int file, int rank) {
        return get(square(file, rank));
    }

    /**
     * Set piece to the board
     *
     * @param piece  piece to set
     * @param square the square
     */
    public void set(Piece piece, int square) {
        if (piece == null) {
            throw new NullPointerException();
        }

        validateSquare(square);

        squares[square] = piece.byteValue();

        if (piece == Piece.NONE) {
            occupiedSquares.remove(square);
        } else {
            occupiedSquares.add(square);
        }
    }

    /**
     * Shortcut for {@code set(Piece, square(file, rank))}
     */
    public void set(Piece piece, int file, int rank) {
        set(piece, square(file, rank));
    }

    /**
     * Validate that square is on the board. Throws IllegalArgumentException if it's not
     *
     * @param file file of the square
     * @param rank rank of the square
     * @throws IllegalArgumentException if square is invalid (i.e., not on the board)
     */
    private void validateSquare(int file, int rank) throws IllegalArgumentException {
        if (!isValidSquare(file, rank)) {
            throw new IllegalArgumentException("Square " + file + "x" + rank + " is not on board " + files + "x" +
                    ranks);
        }
    }

    /**
     * Validate that square is on the board. Throws IllegalArgumentException if it's not
     *
     * @param square square number
     * @throws IllegalArgumentException if square is invalid (i.e., not on the board)
     */
    private void validateSquare(int square) throws IllegalArgumentException {
        if (square < 0 | square >= squares.length) {
            throw new IllegalAccessError("Square " + square + " is not on board " + files + "x" + ranks);
        }
    }

    /** Total number of files */
    public int files() {
        return files;
    }

    /** Total number of files */
    public int ranks() {
        return ranks;
    }

    /** Total number of squares on the board */
    public int length() {
        return squares.length;
    }

    /**
     * Get squares occupied by pieces
     *
     * @return unmodifiable view of underlying collection
     */
    public Set<Integer> getOccupiedSquares() {
        return Collections.unmodifiableSet(occupiedSquares);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(squares.length);
        sb.append('\n');
        for (int r = ranks - 1; r >= 0; r--) {
            for (int f = 0; f < files; f++) {
                sb.append(Piece.fromByte(squares[square(f, r)])).append('|');
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
                Arrays.equals(squares, board.squares);
    }

    @Override
    public int hashCode() {
        return Objects.hash(files, ranks) * 31 + Arrays.hashCode(squares);
    }

    /**
     * Get immutable object which unambiguously represents board state but occupies less space than board
     * itself. Object implements {@link Object#hashCode()} and {@link Object#equals(Object)} properly.
     */
    public Object getHashableKey() {
        return new HashableKey(Arrays.copyOf(squares, squares.length));
    }

    /**
     * Create copy of board instance. The copy is a deep copy. Any modifications to original won't be reflected in copy
     * and vice-versa
     *
     * @param toCopy board to copy
     * @return new Board instance
     */
    public static Board copyOf(Board toCopy) {
        Board copy = new Board(toCopy.files, toCopy.ranks, toCopy.squares, toCopy.occupiedSquares);
        return copy;
    }

    private static class HashableKey {
        private final byte[] data;

        private HashableKey(byte[] data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashableKey that = (HashableKey) o;
            return Arrays.equals(data, that.data);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(data);
        }
    }
}
