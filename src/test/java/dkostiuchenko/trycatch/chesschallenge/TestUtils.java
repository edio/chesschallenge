package dkostiuchenko.trycatch.chesschallenge;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

import java.util.Objects;
import java.util.StringJoiner;

public class TestUtils {
    /** Build failure message for Board instance */
    public static String failureMsg(Board b, String... description) {
        StringBuilder sb = new StringBuilder();
        for (String s : description) {
            sb.append(s).append("\n");
        }
        sb.append("\n").append(b.toString());
        return sb.toString();
    }
}
