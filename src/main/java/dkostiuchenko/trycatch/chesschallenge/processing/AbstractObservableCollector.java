package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

import java.util.LinkedList;
import java.util.Queue;

public abstract class AbstractObservableCollector implements ObservableCollector {

    private final Queue<ResultObserver> observers = new LinkedList<>();

    @Override
    public void addObserver(ResultObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies all subscribed observers
     */
    protected void notifyObservers(Board board, boolean isIndependent) {
        for (ResultObserver observer : observers) {
            observer.notifyResult(board, isIndependent);
        }
    }
}
