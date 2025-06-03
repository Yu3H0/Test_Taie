

package pascal.taie.analysis.pta.pts;

import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.util.collection.SetEx;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Delegates points-to set to a concrete set implementation.
 */
abstract class DelegatePointsToSet implements PointsToSet {

    protected final SetEx<CSObj> set;

    DelegatePointsToSet(SetEx<CSObj> set) {
        this.set = set;
    }

    @Override
    public boolean addObject(CSObj obj) {
        return set.add(obj);
    }

    @Override
    public boolean addAll(PointsToSet pts) {
        if (pts instanceof DelegatePointsToSet other) {
            return set.addAll(other.set);
        } else {
            boolean changed = false;
            for (CSObj o : pts) {
                changed |= addObject(o);
            }
            return changed;
        }
    }

    @Override
    public void removeIf(Predicate<CSObj> filter) {
        set.removeIf(filter);
    }

    @Override
    public boolean contains(CSObj obj) {
        return set.contains(obj);
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public Set<CSObj> getObjects() {
        return Collections.unmodifiableSet(set);
    }

    @Override
    public Stream<CSObj> objects() {
        return set.stream();
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public String toString() {
        return set.toString();
    }

    @Override
    public PointsToSet addAllDiff(PointsToSet pts) {
        Set<CSObj> otherSet = pts instanceof DelegatePointsToSet other ?
                other.set : pts.getObjects();
        return newSet(set.addAllDiff(otherSet));
    }

    @Override
    public PointsToSet copy() {
        return newSet(set.copy());
    }

    protected abstract PointsToSet newSet(SetEx<CSObj> set);
}
