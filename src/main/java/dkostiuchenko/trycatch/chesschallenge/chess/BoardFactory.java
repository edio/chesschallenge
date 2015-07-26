package dkostiuchenko.trycatch.chesschallenge.chess;

import dkostiuchenko.trycatch.chesschallenge.permutation.Permutation;

/**
 * Creates {@link Board} objects with constant size.
 * <p/>
 * This implementation caches returned board instance to reduce amount of produced garbage.
 */
public class BoardFactory {
    private final int files;
    private final int ranks;
    private final Board cachedInstance;

    public BoardFactory(int files, int ranks) {
        this.files = files;
        this.ranks = ranks;
        this.cachedInstance = new Board(files, ranks);
    }

    /**
     * Builds board from permutation
     *
     * @return always same board instance. Use {@link #copy(Board)} if you need a copy
     */
    public Board fromPermutation(Permutation permutation) {
        cachedInstance.setPermutation(permutation);
        return cachedInstance;
    }

}
