package dkostiuchenko.trycatch.chesschallenge;

import dkostiuchenko.trycatch.chesschallenge.chess.BoardFactory;
import dkostiuchenko.trycatch.chesschallenge.chess.IndependenceChecker;
import dkostiuchenko.trycatch.chesschallenge.chess.Piece;
import dkostiuchenko.trycatch.chesschallenge.permutation.HeapMultisetPermutationStrategy;
import dkostiuchenko.trycatch.chesschallenge.permutation.HeapPermutationStrategy;
import dkostiuchenko.trycatch.chesschallenge.permutation.PermutationCollector;
import dkostiuchenko.trycatch.chesschallenge.permutation.PermutationStrategy;
import dkostiuchenko.trycatch.chesschallenge.processing.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Brings up all dependencies, ties them all together and performs run
 */
public class Application {

    private final Piece[] initialPermutation;
    private final PermutationStrategy permutationStrategy;
    private final PermutationCollector collector;
    private final Iterable<AbstractResultWritingObserver> resultObservers;

    public Application(Piece[] initialPermutation, PermutationStrategy permutationStrategy, PermutationCollector
            collector, Iterable<AbstractResultWritingObserver> resultObservers) {
        this.initialPermutation = initialPermutation;
        this.permutationStrategy = permutationStrategy;
        this.collector = collector;
        this.resultObservers = resultObservers;
    }

    public void run() {
        permutationStrategy.permute(initialPermutation, collector);
        for (AbstractResultWritingObserver observer : resultObservers) {
            observer.report();
        }
    }

    public enum PermutationStrategyType {
        HEAP,
        HEAP_MULTISET
    }

    public static class Builder {
        private int bishops;
        private int rooks;
        private int knights;
        private int kings;
        private int queens;
        private int boardFiles;
        private int boardRanks;
        private int independentLimit;
        private boolean verbose;
        private PermutationStrategyType permutationStrategy;
        private int concurrencyLevel;

        public Application build() {
            Piece[] initialPermutation = buildInitialPermutation();

            PermutationStrategy strategy = createPermutationStrategy();

            BoardFactory boardFactory = new BoardFactory(boardFiles, boardRanks);
            ObservableCollector collector = createCollector(boardFactory);
            Iterable<AbstractResultWritingObserver> interimObservers = createInterimObservers(boardFactory);
            for (ResultObserver resultObserver : interimObservers) {
                collector.addObserver(resultObserver);
            }
            Iterable<AbstractResultWritingObserver> endObsevers = createEndObservers(boardFactory);
            for (ResultObserver resultObserver : endObsevers) {
                collector.addObserver(resultObserver);
            }

            return new Application(initialPermutation, strategy, collector, endObsevers);
        }

        private SynchronousDelegatingCollector createCollector(BoardFactory boardFactory) {
            IndependenceChecker checker = new IndependenceChecker();
            if (concurrencyLevel <= 0) {
                return new SynchronousDelegatingCollector(checker, boardFactory);
            } else {
                throw new IllegalArgumentException("Concurrency is not supported yet");
            }
        }

        private PermutationStrategy createPermutationStrategy() {
            PermutationStrategy strategy;
            switch (permutationStrategy) {
                case HEAP:
                    strategy = new HeapPermutationStrategy();
                    break;
                case HEAP_MULTISET:
                    strategy = new HeapMultisetPermutationStrategy();
                    break;
                default:
                    throw new IllegalArgumentException("Unhandled permutation strategy type " + permutationStrategy);
            }
            return strategy;
        }

        private Iterable<AbstractResultWritingObserver> createInterimObservers(BoardFactory boardFactory) {
            List<AbstractResultWritingObserver> observers = new ArrayList<>();

            if (verbose) {
                observers.add(new RunStatisticsObserver(new PrintWriter(System.err)));
            }

            return observers;
        }

        private Iterable<AbstractResultWritingObserver> createEndObservers(BoardFactory boardFactory) {
            List<AbstractResultWritingObserver> observers = new ArrayList<>();

            if (independentLimit > 0) {
                observers.add(new IndependentPermutationsObserver(new PrintWriter(System.out), independentLimit,
                        boardFactory));
            }

            observers.add(new FinalStatisticsObserver(new PrintWriter(System.out), System.currentTimeMillis()));

            return observers;
        }

        private Piece[] buildInitialPermutation() {
            final int boardSize = boardFiles * boardRanks;
            int nones = boardSize - bishops - rooks - knights - kings - queens;
            if (nones < 0) {
                throw new IllegalArgumentException("Total number of pieces exceeds board size");
            }

            List<Piece> tmpBoard = new ArrayList<>();
            for (int i = 0; i < nones; i++) {
                tmpBoard.add(Piece.NONE);
            }
            for (int i = 0; i < bishops; i++) {
                tmpBoard.add(Piece.BISHOP);
            }
            for (int i = 0; i < knights; i++) {
                tmpBoard.add(Piece.KNIGHT);
            }
            for (int i = 0; i < rooks; i++) {
                tmpBoard.add(Piece.ROOK);
            }
            for (int i = 0; i < kings; i++) {
                tmpBoard.add(Piece.KING);
            }
            for (int i = 0; i < queens; i++) {
                tmpBoard.add(Piece.QUEEN);
            }
            Piece[] board = new Piece[boardSize];
            return tmpBoard.toArray(board);
        }

        public Builder setBishops(int bishops) {
            this.bishops = bishops;
            return this;
        }

        public Builder setRooks(int rooks) {
            this.rooks = rooks;
            return this;
        }

        public Builder setKnights(int knights) {
            this.knights = knights;
            return this;
        }

        public Builder setKings(int kings) {
            this.kings = kings;
            return this;
        }

        public Builder setQueens(int queens) {
            this.queens = queens;
            return this;
        }

        public Builder setBoardFiles(int boardFiles) {
            this.boardFiles = boardFiles;
            return this;
        }

        public Builder setBoardRanks(int boardRanks) {
            this.boardRanks = boardRanks;
            return this;
        }

        public Builder setIndependentLimit(int independentLimit) {
            this.independentLimit = independentLimit;
            return this;
        }

        public Builder setVerbose(boolean verbose) {
            this.verbose = verbose;
            return this;
        }

        public Builder setPermutationStrategy(PermutationStrategyType permutationStrategy) {
            this.permutationStrategy = permutationStrategy;
            return this;
        }

        public Builder setConcurrencyLevel(int concurrencyLevel) {
            this.concurrencyLevel = concurrencyLevel;
            return this;
        }
    }
}
