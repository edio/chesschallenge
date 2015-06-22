package dkostiuchenko.trycatch.chessproblem;

import dkostiuchenko.trycatch.chessproblem.piece.Piece;
import dkostiuchenko.trycatch.chessproblem.piece.Pieces;

import javax.annotation.Nullable;

/**
 * Board.
 * Indexed from bottom-left corner, as real chess-board.
 */
public class Board {

    private final int files;
    private final int ranks;
    private final int[] squares;
    // hold pre-calculated number of occupied squares for each file and rank
    private final int[] filesCounts;
    private final int[] ranksCounts;

    public Board(final int files, final int ranks) {
        this.files = files;
        this.ranks = ranks;
        this.squares = new int[files * ranks];
        this.filesCounts = new int[files];
        this.ranksCounts = new int[ranks];
    }

    /**
     * Validate that square is on the board. Throws IllegalArgumentException if it's not
     *
     * @param file file of the square
     * @param rank rank of the square
     * @throws if square is invalid (i.e., not on the board)
     */
    public void validateSquare(int file, int rank) throws IllegalArgumentException {
        if (!isValidSquare(file, rank)) {
            throw new IllegalArgumentException("Square " + file + "x" + rank + " is not on board " + files + "x" + ranks);
        }
    }

    /**
     * Checks, whether square is on board
     *
     * @param file file of the square
     * @param rank rank of the square
     * @return <tt>true</tt> if square is on board, <tt>false</tt> otherwise
     */
    public boolean isValidSquare(int file, int rank) {
        return file >= 0 & file < files & rank >= 0 & rank < ranks;
    }


    /**
     * Get piece, if any, located on specified file and rank
     *
     * @param file file of the square
     * @param rank rank of the square
     * @return instance or <tt>null</tt>
     * @throws IllegalArgumentException if <tt>file</tt> or <tt>rank</tt> is out of board bounds
     */
    @Nullable
    public Piece get(int file, int rank) throws IllegalArgumentException {
        final int index = getIndex(file, rank);
        final int pieceCode = squares[index];
        return pieceCode == 0 ? null : Pieces.ofCode(pieceCode);
    }

    /**
     * Checks, whether board has some piece on specified square
     *
     * @param file file of the square
     * @param rank rank of the square
     * @return <tt>true</tt> if there's some piece on specific square, <tt>false</tt> otherwise
     * @throws IllegalArgumentException
     */
    public boolean hasPiece(int file, int rank) throws IllegalArgumentException {
        final int index = getIndex(file, rank);
        return squares[index] > 0;
    }

    /**
     * Set piece to the board. Setting <tt>null</tt> is equivalent to removing piece from specific square
     *
     * @param piece piece to set
     * @param file  number of vertical
     * @param rank  number of horizontal
     */
    public void set(@Nullable Piece piece, int file, int rank) {
        final int index = getIndex(file, rank);
        if (piece == null) {
            int previous = squares[index];
            squares[index] = 0;
            if (previous > 0) {
                filesCounts[file]--;
                ranksCounts[rank]--;
            }
        } else {
            int previous = squares[index];
            squares[index] = piece.code();
            if (previous == 0) {
                filesCounts[file]++;
                ranksCounts[rank]++;
            }
        }
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
        return filesCounts[file];
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
        return ranksCounts[rank];
    }

    /**
     * Count pieces which are placed on the diagonal that includes specific square and is parallel to long black diagonal
     *
     * @return number of pieces
     */
    public int countForwardDiagonal(int file, int rank) {
        final int initialIndex = getIndex(file, rank);

        int count = 0;

        if (squares[initialIndex] > 0) {
            count++;
        }

        int step = ranks + 1;

        int stepsUp = Math.min(files - file - 1, ranks - rank - 1);
        for (int s = 0, index = initialIndex + step; s < stepsUp; s++, index += step) {
            if (squares[index] > 0) {
                count++;
            }
        }

        int stepsDown = Math.min(file, rank);
        for (int s = 0, index = initialIndex - step; s < stepsDown; s++, index -= step) {
            if (squares[index] > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Count pieces which are placed on the diagonal that includes specific square and is parallel to long white diagonal
     *
     * @return number of pieces
     */
    public int countBackDiagonal(int file, int rank) {
        final int initialIndex = getIndex(file, rank);

        int count = 0;

        if (squares[initialIndex] > 0) {
            count++;
        }

        int step = ranks - 1;

        int stepsUp = Math.min(files - file - 1, rank);
        for (int s = 0, index = initialIndex + step; s < stepsUp; s++, index += step) {
            if (squares[index] > 0) {
                count++;
            }
        }

        int stepsDown = Math.min(file, ranks - rank - 1);
        for (int s = 0, index = initialIndex - step; s < stepsDown; s++, index -= step) {
            if (squares[index] > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Size
     *
     * @return total number of files
     */
    public int files() {
        return files;
    }

    /**
     * Size
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(squares.length);
        for (int r = ranks - 1; r >= 0; r--) {
            for (int f = 0; f < files; f++) {
                final int index = getIndex(f, r);
                final int pieceCode = squares[index];
                if (pieceCode == 0) {
                    sb.append("_|");
                } else {
                    sb.append(Pieces.ofCode(pieceCode)).append('|');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }


}
