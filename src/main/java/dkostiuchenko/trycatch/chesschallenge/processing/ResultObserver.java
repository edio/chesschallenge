package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

/**
 * Is notified every time some results of independence check are available from IndependenceResultListener.
 * <p/>
 * Implementations are assumed to be threadsafe
 */
public interface ResultObserver {
    /**
     * May be called in multiple threads.
     */
    void notifyResult(Board b, boolean isIndependent);
}
