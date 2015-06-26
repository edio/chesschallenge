package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.permutation.PermutationCollector;

public interface ObservableCollector extends PermutationCollector {
    /**
     * Add observer to this provider
     */
    void addObserver(ResultObserver observer);
}
