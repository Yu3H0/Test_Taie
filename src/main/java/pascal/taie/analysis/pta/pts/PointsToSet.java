

package pascal.taie.analysis.pta.pts;

import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.util.Copyable;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Representation of points-to sets that consist of {@link CSObj}.
 */
public interface PointsToSet extends Iterable<CSObj>, Copyable<PointsToSet> {

    /**
     * Adds an object to this set.
     *
     * @return true if this points-to set changed as a result of the call,
     * otherwise false.
     */
    boolean addObject(CSObj obj);

    /**
     * Adds all objects in given pts to this set.
     *
     * @return true if this points-to set changed as a result of the call,
     * otherwise false.
     */
    boolean addAll(PointsToSet pts);

    /**
     * Adds all objects in given pts to this set.
     *
     * @return the difference between {@code pts} and this set.
     */
    PointsToSet addAllDiff(PointsToSet pts);

    /**
     * Removes objects from this set if they satisfy the filter.
     * <p>
     * <strong>Note:</strong> This method should not be called outside of
     * {@link pascal.taie.analysis.pta.plugin.Plugin#onPhaseFinish()},
     * otherwise it may break the monotonicity of pointer analysis.
     * </p>
     */
    void removeIf(Predicate<CSObj> filter);

    /**
     * @return true if this set contains given object, otherwise false.
     */
    boolean contains(CSObj obj);

    /**
     * @return whether this set if empty.
     */
    boolean isEmpty();

    /**
     * @return the number of objects in this set.
     */
    int size();

    /**
     * @return all objects in this set.
     */
    Set<CSObj> getObjects();

    /**
     * @return all objects in this set.
     */
    Stream<CSObj> objects();

    @Override
    default Iterator<CSObj> iterator() {
        return getObjects().iterator();
    }
}
