package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

import java.io.Writer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

/**
 * Gathers some statistics to print in the end
 */
public class FinalStatisticsObserver extends AbstractResultWritingObserver {

    public static final String DURATION_FORMAT = "HH:mm:ss.SSS";
    private final AtomicLong totalPermutations = new AtomicLong();
    private final AtomicInteger independentPermutations = new AtomicInteger();
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
        totalPermutations.incrementAndGet();
        if (isIndependent) {
            independentPermutations.incrementAndGet();
        }
    }

    @Override
    public void report() {
        final long runningTime = System.currentTimeMillis() - approximateStartTime;
        StringBuilder sb = new StringBuilder();
        sb.append("Total permutations: ").append(totalPermutations.get()).append('\n');
        sb.append("Independent:        ").append(independentPermutations.get()).append('\n');
        sb.append("Running time:       ").append(formatDuration(runningTime, DURATION_FORMAT)).append('\n');
        write(sb.toString());
    }

}
