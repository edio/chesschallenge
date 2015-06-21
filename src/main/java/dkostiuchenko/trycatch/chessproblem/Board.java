package dkostiuchenko.trycatch.chessproblem;

import javax.annotation.Nullable;

/**
 * Board. May be turned to a class later
 */
public interface Board {

    /**
     * Get piece, if any, located on specified file and rank
     *
     * @param file number of vertical
     * @param rank number of horizontal
     * @return instance or <tt>null</tt>
     */
    @Nullable
    Piece get(int file, int rank);

    /**
     * Set piece to the board. Setting <tt>null</tt> is equivalent to removing piece from specific square
     *
     * @param piece piece to set
     * @param file  number of vertical
     * @param rank  number of horizontal
     */
    void set(@Nullable Piece piece, int file, int rank);

    /**
     * Count number of pieces on specified file
     *
     * @param file file
     * @return number of pieces
     */
    int countFile(int file);

    /**
     * Count number of pieces on specified rank
     *
     * @param rank rank
     * @return number of pieces
     */
    int countRank(int rank);

}
