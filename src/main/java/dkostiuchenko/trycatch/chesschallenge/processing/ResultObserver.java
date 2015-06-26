package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

/**
 * Is notified every time some results of independence check are available from {@link SynchronousProcessingCollector}
 */
public interface ResultObserver {
    void notifyResult(Board b, boolean isIndependent);
}
