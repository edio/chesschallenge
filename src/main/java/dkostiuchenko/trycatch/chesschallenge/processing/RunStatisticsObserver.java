package dkostiuchenko.trycatch.chesschallenge.processing;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Gathers some statistics during execution and prints it periodically
 */
public class RunStatisticsObserver extends AbstractResultWritingObserver {

    private static final long REPORTING_DELAY_MILLIS = TimeUnit.SECONDS.toMillis(30);

    // these may be inconsistent due to lack of atomicity or even race condition on 32-bit os, but we're ok with that
    private volatile long totalPermutations;
    private volatile int independentPermutations;
    private volatile long totalOnLastTick;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

    /**
     * Create reporting observer wiht custom reporting delay
     */
    private RunStatisticsObserver(Writer outputWriter) {
        super(outputWriter);
    }

    /**
     * Create instance of {@link RunStatisticsObserver} and run background thread to report statistics periodically
     *
     * @param outputWriter writer to output to
     * @return new instance
     */
    public static RunStatisticsObserver create(Writer outputWriter) {
        RunStatisticsObserver instance = new RunStatisticsObserver(outputWriter);
        Thread reportingThread = new Thread(instance.new TimedPrintJob());
        reportingThread.setName("chesschallenge-statistics");
        reportingThread.setDaemon(true);
        reportingThread.start();
        return instance;
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
        StringBuilder sb = new StringBuilder();

        String date = dateFormat.format(new Date());
        sb.append(date).append('\n');

        final long totalSinceLastTick = totalPermutations - totalOnLastTick;
        totalOnLastTick = totalPermutations;

        sb.append("Total permutations: ").append(totalPermutations).append('\n');
        sb.append("Since last tick:    ").append(totalSinceLastTick).append('\n');
        sb.append("Independent:        ").append(independentPermutations).append('\n');

        write(sb.toString());
    }

    private class TimedPrintJob implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Thread.sleep(REPORTING_DELAY_MILLIS);
                    report();
                }
            } catch (InterruptedException ignored) {
            } catch (Exception e) {
                RunStatisticsObserver.this.write("Statistics reporter has crashed!");
            }
        }
    }
}
