package dkostiuchenko.trycatch.chesschallenge;

import com.google.common.truth.TestVerb;
import com.google.common.truth.Truth;
import dkostiuchenko.trycatch.chesschallenge.chess.Board;

public class BoardTruth {
    /**
     * Run assertion for board. Method produces descriptive message in case of assertion failure
     */
    public static TestVerb assertWithBoard(Board b) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int rank = b.ranks() - 1; rank >= 0; rank--) {
            for (int file = 0; file < b.files(); file++) {
                sb.append(String.format("%3d%s|", b.square(file, rank), b.get(file, rank)));
            }
            sb.append("\n");
        }
        return Truth.assertWithMessage(sb.toString());
    }
}
