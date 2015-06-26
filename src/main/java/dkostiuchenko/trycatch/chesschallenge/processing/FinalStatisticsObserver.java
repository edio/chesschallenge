package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

import java.io.Writer;

import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

/**
 * Gathers some statistics to print in the end
 */
public class FinalStatisticsObserver extends AbstractResultWritingObserver {

    private static final String DURATION_FORMAT = "HH:mm:ss.SSS";
    private long totalPermutations;
    private long independentPermutations;
    private final long approximateStartTime;

    /**
     * Create reporting observer with custom reporting delay
     */
    public FinalStatisticsObserver(Writer outputWriter, long approximateStartTime) {
        super(outputWriter);
        this.approximateStartTime = approximateStartTime;
    }

    @Override
    public void notifyResult(Board b, boolean isIndependent) {
        totalPermutations++;
        if (isIndependent) {
            independentPermutations++;
        }
    }

    @Override
    public void report() {
        final long runningTime = System.currentTimeMillis() - approximateStartTime;
        StringBuilder sb = new StringBuilder();
        sb.append("Total permutations: ").append(totalPermutations).append('\n');
        sb.append("Independent:        ").append(independentPermutations).append('\n');
        sb.append("Running time:       ").append(formatDuration(runningTime, DURATION_FORMAT)).append('\n');
        write(sb.toString());
    }

}
