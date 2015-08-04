package dkostiuchenko.trycatch.chesschallenge;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;
import dkostiuchenko.trycatch.chesschallenge.chess.Piece;
import dkostiuchenko.trycatch.chesschallenge.solver.DftSolver;
import dkostiuchenko.trycatch.chesschallenge.solver.TransientSmartCollector;

import java.util.Arrays;

/**
 * Set up everything, ties it all together and performs run
 */
public class Application {

    private final DftSolver solver;
    private final TransientSmartCollector collector;

    public Application(DftSolver solver, TransientSmartCollector collector) {
        this.solver = solver;
        this.collector = collector;
    }

    public void run() {
        solver.solve();
        System.out.println("Total solutions found: " + collector.getTotalSolutions());
    }

    public static class Builder {
        private int bishops;
        private int rooks;
        private int knights;
        private int kings;
        private int queens;

        private int boardFiles;
        private int boardRanks;

        private int printLimit1;
        private int printLimit2;
        private int printSparseFactor;

        public Application build() {
            Piece[] pieces = buildPieces();
            TransientSmartCollector collector = new TransientSmartCollector(printLimit1, printSparseFactor,
                    printLimit2);
            DftSolver solver = new DftSolver(new Board(boardFiles, boardRanks), pieces, collector);
            return new Application(solver, collector);
        }

        private Piece[] buildPieces() {
            final int boardSize = boardFiles * boardRanks;
            final int totalPieces = bishops + rooks + knights + kings + queens;
            if (totalPieces > boardSize) {
                throw new IllegalArgumentException("Total number of pieces exceeds board size");
            }

            Piece[] pieces = new Piece[totalPieces];

            int offset = 0;
            Arrays.fill(pieces, offset, offset + bishops, Piece.BISHOP); offset += bishops;
            Arrays.fill(pieces, offset, offset + rooks, Piece.ROOK); offset += rooks;
            Arrays.fill(pieces, offset, offset + knights, Piece.KNIGHT); offset += knights;
            Arrays.fill(pieces, offset, offset + kings, Piece.KING); offset += kings;
            Arrays.fill(pieces, offset, offset + queens, Piece.QUEEN); offset += queens;

            return pieces;
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

        public Builder setPrintLimit1(int printLimit1) {
            this.printLimit1 = printLimit1;
            return this;
        }

        public Builder setPrintLimit2(int printLimit2) {
            this.printLimit2 = printLimit2;
            return this;
        }

        public Builder setPrintSparseFactor(int printSparseFactor) {
            this.printSparseFactor = printSparseFactor;
            return this;
        }
    }
}
