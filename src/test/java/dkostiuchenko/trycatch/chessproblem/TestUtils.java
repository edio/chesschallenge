package dkostiuchenko.trycatch.chessproblem;

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
