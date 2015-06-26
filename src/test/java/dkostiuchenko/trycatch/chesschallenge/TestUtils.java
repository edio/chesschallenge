package dkostiuchenko.trycatch.chesschallenge;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

public class TestUtils {
    public static String failureMsg(Board b, String... description) {
        StringBuilder sb = new StringBuilder();
        for (String s : description) {
            sb.append(s).append("\n");
        }
        sb.append("\n").append(b.toString());
        return sb.toString();
    }
}
