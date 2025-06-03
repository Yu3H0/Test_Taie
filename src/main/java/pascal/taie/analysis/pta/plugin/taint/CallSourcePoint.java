

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JMethod;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
 * A {@code CallSourcePoint} is variable at an invocation site.
 */
public record CallSourcePoint(Invoke sourceCall, IndexRef indexRef)
        implements SourcePoint {

    private static final Comparator<CallSourcePoint> COMPARATOR =
            Comparator.comparing((CallSourcePoint csp) -> csp.sourceCall)
                    .thenComparing(CallSourcePoint::indexRef);

    @Override
    public int compareTo(@Nonnull SourcePoint sp) {
        if (sp instanceof CallSourcePoint csp) {
            return COMPARATOR.compare(this, csp);
        }
        return SourcePoint.compare(this, sp);
    }

    @Override
    public JMethod getContainer() {
        return sourceCall.getContainer();
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String toString() {
        return sourceCall.toString() + "/" + indexRef;
    }

}
