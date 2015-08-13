package dkostiuchenko.trycatch.chesschallenge;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.action.StoreArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentGroup;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * Handles arguments and provides convenient command-line interface
 */
public class Cli {

    private final ArgumentParser parser = buildParser();

    public Namespace parseArgs(String... args) {
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        return ns;
    }

    private ArgumentParser buildParser() {
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
                .help("Print each solution to stdout up to that limit");
        outputSettings.addArgument("--print-limit2").action(new StoreArgumentAction())
                .type(Integer.class)
                .setDefault(5000000)
                .help("Print every N'th solution up to that limit to stdout where N is determined by " +
                        "--print-sparse-factor");
        outputSettings.addArgument("--print-sparse-factor").action(new StoreArgumentAction())
                .type(Integer.class)
                .setDefault(500000)
                .help("N parameter for --print-limit2 option");

        return parser;
    }
}
