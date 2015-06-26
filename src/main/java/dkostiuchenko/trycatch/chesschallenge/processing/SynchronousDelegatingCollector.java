package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;
import dkostiuchenko.trycatch.chesschallenge.chess.BoardFactory;
import dkostiuchenko.trycatch.chesschallenge.chess.IndependenceChecker;
import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

public class SynchronousDelegatingCollector extends AbstractObservableCollector {

    private final IndependenceChecker checker;
    private final BoardFactory boardFactory;

    public SynchronousDelegatingCollector(IndependenceChecker checker, BoardFactory boardFactory) {
        this.checker = checker;
        this.boardFactory = boardFactory;
    }

    @Override
    public void collect(Piece[] permutation) {
        Board board = boardFactory.fromRawData(permutation);
        notifyObservers(board, checker.isIndependent(board));
    }
}
