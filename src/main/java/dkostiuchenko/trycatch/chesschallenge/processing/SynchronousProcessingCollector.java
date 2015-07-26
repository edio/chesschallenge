package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;
import dkostiuchenko.trycatch.chesschallenge.chess.BoardFactory;
import dkostiuchenko.trycatch.chesschallenge.permutation.Permutation;
import dkostiuchenko.trycatch.chesschallenge.permutation.PermutationCollector;

import java.util.LinkedList;
import java.util.Queue;

/**
 * {@link dkostiuchenko.trycatch.chesschallenge.permutation.PermutationCollector} which synchronously checks, whether
 * permutation is independent and passes results to all subscribed observers.
 * <p/>
 * Major benefit of synchronous processing is that massive data copying can be avoided
 */
public class SynchronousProcessingCollector implements PermutationCollector {

    private final IndependenceChecker checker;
    private final BoardFactory boardFactory;
    private final Queue<ResultObserver> observers = new LinkedList<>();

    public SynchronousProcessingCollector(IndependenceChecker checker, BoardFactory boardFactory) {
        this.checker = checker;
        this.boardFactory = boardFactory;
    }

    @Override
    public void collect(Permutation permutation) {
        Board board = boardFactory.fromPermutation(permutation);
        notifyObservers(board, checker.isIndependent(board));
    }

    public void addObserver(ResultObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(Board board, boolean isIndependent) {
        for (ResultObserver observer : observers) {
            observer.notifyResult(board, isIndependent);
        }
    }
}
