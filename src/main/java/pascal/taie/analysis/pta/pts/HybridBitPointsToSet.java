

package pascal.taie.analysis.pta.pts;

import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.util.Indexer;
import pascal.taie.util.collection.HybridBitSet;
import pascal.taie.util.collection.SetEx;

class HybridBitPointsToSet extends DelegatePointsToSet {

    public HybridBitPointsToSet(Indexer<CSObj> indexer, boolean isSparse) {
        this(new HybridBitSet<>(indexer, isSparse));
    }

    private HybridBitPointsToSet(SetEx<CSObj> set) {
        super(set);
    }

    @Override
    protected PointsToSet newSet(SetEx<CSObj> set) {
        return new HybridBitPointsToSet(set);
    }
}
