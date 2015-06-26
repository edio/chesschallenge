package dkostiuchenko.trycatch.chesschallenge.processing;

import java.io.IOException;
import java.io.Writer;

/**
 * Result observe which writer about observed results to some output
 */
public abstract class AbstractResultWritingObserver implements ResultObserver {

    protected final Writer outputWriter;

    public AbstractResultWritingObserver(Writer outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Report observer results to writer
     */
    public abstract void report();

    protected void write(final String str) {
        try {
            outputWriter.write(str);
            outputWriter.flush();
        } catch (IOException ioe) {
            System.err.println("Error during write: " + ioe.getMessage() + ".\nMessage:\n" + str);
        }
    }
}
