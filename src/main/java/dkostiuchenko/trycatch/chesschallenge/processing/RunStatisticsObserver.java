package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Gathers some statistics during execution and prints it periodically
 */
public class RunStatisticsObserver extends AbstractResultWritingObserver {

    public static final long REPORTING_DELAY_MILLIS = TimeUnit.SECONDS.toMillis(30);

    private final AtomicLong totalPermutations = new AtomicLong();
    private final AtomicInteger independentPermutations = new AtomicInteger();
    private long totalOnLastTick;
    private final long reportingDelayMillis;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:SS.sss");

    /**
     * Create reporting observer wiht default reporting delay of {@link #REPORTING_DELAY_MILLIS}
     */
    public RunStatisticsObserver(Writer outputWriter) {
        this(outputWriter, REPORTING_DELAY_MILLIS);
    }

    /**
     * Create reporting observer wiht custom reporting delay
     */
    public RunStatisticsObserver(Writer outputWriter, long reportingDelayMillis) {
        super(outputWriter);
        this.reportingDelayMillis = reportingDelayMillis;

        if (reportingDelayMillis > 0) {
            Thread reportingThread = new Thread(new TimedPrintJob());
            reportingThread.setName("chesschallenge-statistics");
            reportingThread.setDaemon(true);
            reportingThread.start();
        }
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
        StringBuilder sb = new StringBuilder();

        String date = DATE_FORMAT.format(new Date());
        sb.append(date).append('\n');

        final long total = totalPermutations.get();
        final long totalSinceLastTick = total - totalOnLastTick;
        totalOnLastTick = total;
        final int independent = independentPermutations.get();

        sb.append("Total permutations: ").append(total).append('\n');
        sb.append("Since last tick:    ").append(totalSinceLastTick).append('\n');
        sb.append("Independent:        ").append(independent).append('\n');

        write(sb.toString());
    }

    private class TimedPrintJob implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Thread.sleep(reportingDelayMillis);
                    report();
                }
            } catch (InterruptedException ignored) {
            } catch (Exception e) {
                RunStatisticsObserver.this.write("Statistics reporter has crashed!");
            }
        }
    }
}
