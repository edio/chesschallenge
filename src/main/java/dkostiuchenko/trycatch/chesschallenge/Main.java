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
                    .setIndependentLimit(nameSpace.getInt("print_limit"))
                    .setVerbose(nameSpace.getBoolean("verbose"))
                    .setPermutationStrategy(nameSpace.<Application.PermutationStrategyType>get("permutation_strategy"))
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

        final ArgumentGroup calculation = parser.addArgumentGroup("Calculation settings");
        calculation.addArgument("-p", "--permutation-strategy").action(new StoreArgumentAction()).required(true)
                .type(Application.PermutationStrategyType.class)
                .choices(Application.PermutationStrategyType.values())
                .help("Permutation strategy to use");

        final ArgumentGroup outputSettings = parser.addArgumentGroup("Output settings");
        outputSettings.addArgument("-v", "--verbose").action(new StoreConstArgumentAction()).setDefault(false)
                .type(Boolean.class)
                .setConst(true)
                .help("Be verbose. Print progress and statistics to stderr");
        outputSettings.addArgument("-l", "--print-limit").action(new StoreArgumentAction())
                .type(Integer.class)
                .setDefault(10)
                .help("Print no more than l permutations. This setting doesn't impact calculation");

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
