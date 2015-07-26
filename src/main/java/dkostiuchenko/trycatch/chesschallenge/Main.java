package dkostiuchenko.trycatch.chesschallenge;

import net.sourceforge.argparse4j.inf.Namespace;

public class Main {
    public static void main(String[] args) {
        Namespace nameSpace = new Cli().parseArgs(args);
        try {
            Application app = new Application.Builder()
                    .setBishops(nameSpace.getInt("bishops"))
                    .setKnights(nameSpace.getInt("knights"))
                    .setRooks(nameSpace.getInt("rooks"))
                    .setKings(nameSpace.getInt("kings"))
                    .setQueens(nameSpace.getInt("queens"))
                    .setBoardFiles(nameSpace.getInt("board_files"))
                    .setBoardRanks(nameSpace.getInt("board_ranks"))
                    .setPrintLimit1(nameSpace.getInt("print_limit1"))
                    .setPrintLimit2(nameSpace.getInt("print_limit2"))
                    .setPrintSparseFactor(nameSpace.getInt("print_sparse_factor"))
                    .build();
            app.run();
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            iae.printStackTrace();
            System.exit(1);
        }
    }
}
