

package pascal.taie.analysis.pta.pts;

import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.util.collection.HybridHashSet;
import pascal.taie.util.collection.SetEx;

class HybridHashPointsToSet extends DelegatePointsToSet {

    HybridHashPointsToSet() {
        this(new HybridHashSet<>());
    }

    private HybridHashPointsToSet(SetEx<CSObj> set) {
        super(set);
    }

    @Override
    protected PointsToSet newSet(SetEx<CSObj> set) {
        return new HybridHashPointsToSet(set);
    }
}
