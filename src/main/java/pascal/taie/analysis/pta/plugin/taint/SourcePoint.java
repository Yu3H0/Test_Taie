

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.language.classes.JMethod;

import java.util.Comparator;

/**
 * Represents a program location where the taint object is generated.
 */
public interface SourcePoint extends Comparable<SourcePoint> {

    Comparator<SourcePoint> COMPARATOR =
            Comparator.comparing((SourcePoint sp) -> sp.getContainer().toString())
                    .thenComparingInt(SourcePoint::getPriority);

    JMethod getContainer();

    /**
     * The sort order of the source point.
     */
    int getPriority();

    static int compare(SourcePoint sp1, SourcePoint sp2) {
        return COMPARATOR.compare(sp1, sp2);
    }
}
