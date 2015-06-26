package dkostiuchenko.trycatch.chesschallenge.chess;

import java.util.Arrays;

/**
 * Creates {@link Board} objects with constant size
 */
public class BoardFactory {
    private final int files;
    private final int ranks;

    public BoardFactory(int files, int ranks) {
        this.files = files;
        this.ranks = ranks;
    }

    /**
     * Builds board from raw data
     *
     * @param pieces raw data of pieces
     * @return always new instnace
     */
    public Board fromRawData(Piece[] pieces) {
        return new Board(files, ranks, pieces);
    }

    /**
     * Creates a copy of another board
     *
     * @param board board to copy
     * @return always new instnace
     */
    public Board copy(Board board) {
        return new Board(files, ranks, Arrays.copyOf(board.getSquares(), files * ranks));
    }
}
