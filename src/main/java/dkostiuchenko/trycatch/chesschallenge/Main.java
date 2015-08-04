package dkostiuchenko.trycatch.chesschallenge;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.action.StoreArgumentAction;
import net.sourceforge.argparse4j.impl.action.StoreConstArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentGroup;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class Main {
    public static void main(String[] args) {

        final Namespace nameSpace = parseArgs(args);

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

    private static Namespace parseArgs(String[] args) {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("chesschallenge")
                .defaultHelp(true)
                .description("Finds independent positions for arbitrary board and set of chess pieces");

        final ArgumentGroup boardConfiguration = parser.addArgumentGroup("Board configuration");
        boardConfiguration.addArgument("-f", "--board-files").action(new StoreArgumentAction())
                .type(Integer.class)
                .setDefault(8)
                .help("Vertical size of the board");
        boardConfiguration.addArgument("-r", "--board-ranks").action(new StoreArgumentAction())
                .type(Integer.class)
                .setDefault(8)
                .help("Horizontal size of the board");
        boardConfiguration.addArgument("-B", "--bishops").action(new StoreArgumentAction()).required(false)
                .type(Integer.class)
                .setDefault(0)
                .help("Number of bishops on the board");
        boardConfiguration.addArgument("-R", "--rooks").action(new StoreArgumentAction()).required(false)
                .type(Integer.class)
                .setDefault(0)
                .help("Number of rooks on the board");
        boardConfiguration.addArgument("-N", "--knights").action(new StoreArgumentAction()).required(false)
                .type(Integer.class)
                .setDefault(0)
                .help("Number of knights on the board");
        boardConfiguration.addArgument("-Q", "--queens").action(new StoreArgumentAction()).required(false)
                .type(Integer.class)
                .setDefault(0)
                .help("Number of queens on the board");
        boardConfiguration.addArgument("-K", "--kings").action(new StoreArgumentAction()).required(false)
                .type(Integer.class)
                .setDefault(0)
                .help("Number of kings on the board");

        final ArgumentGroup outputSettings = parser.addArgumentGroup("Output settings");
        outputSettings.addArgument("--print-limit1").action(new StoreArgumentAction())
                .type(Integer.class)
                .setDefault(8)
                .help("Print each solution up to that limit");
        outputSettings.addArgument("--print-limit2").action(new StoreArgumentAction())
                .type(Integer.class)
                .setDefault(5000000)
                .help("Print every N'th solution up to that limit if where N is determined by --print-sparse-factor");
        outputSettings.addArgument("--print-sparse-factor").action(new StoreArgumentAction())
                .type(Integer.class)
                .setDefault(500000)
                .help("N parameter for --print-limit2 option");

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        return ns;
    }
}
