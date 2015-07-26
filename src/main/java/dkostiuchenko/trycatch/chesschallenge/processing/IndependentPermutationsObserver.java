package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;
import dkostiuchenko.trycatch.chesschallenge.chess.BoardFactory;

import java.io.Writer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Stores independent permutations and prints them when requested
 */
public class IndependentPermutationsObserver extends AbstractResultWritingObserver {

    private final BoardFactory boardFactory;
    private final BlockingQueue<Board> independentPermutations;

    /**
     * Creates observer which stores up to {@code numberOfPermutations} independent permutations
     *
     * @param writer               writer to write results to
     * @param numberOfPermutations maximum number of permutations to store
     * @param boardFactory         boardFactory instance
     */
    public IndependentPermutationsObserver(Writer writer, int numberOfPermutations, BoardFactory boardFactory) {
        super(writer);
        this.boardFactory = boardFactory;
        this.independentPermutations = new ArrayBlockingQueue<>(numberOfPermutations);
    }

    @Override
    public void notifyResult(Board b, boolean isIndependent) {
        if (isIndependent) {
            independentPermutations.offer(Board.copyOf(b));
        }
    }

    @Override
    public void report() {
        int ordinal = 0;
        for (Board independentPermutation : independentPermutations) {
            write(ordinal++ + "\n" + independentPermutation.toString() + "\n");
        }
    }
}
