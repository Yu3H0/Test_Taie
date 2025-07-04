

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.ir.stmt.Invoke;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
 * Represents a program location where taint objects flow to a sink.
 *
 * @param sinkCall call site of the sink method.
 * @param indexRef    index of the sensitive argument at {@code sinkCall}.
 */
public record SinkPoint(Invoke sinkCall, IndexRef indexRef)
        implements Comparable<SinkPoint> {

    private static final Comparator<SinkPoint> COMPARATOR =
            Comparator.comparing(SinkPoint::sinkCall)
                    .thenComparing(SinkPoint::indexRef);

    @Override
    public int compareTo(@Nonnull SinkPoint other) {
        return COMPARATOR.compare(this, other);
    }

    @Override
    public String toString() {
        return sinkCall + "/" + indexRef;
    }
}
