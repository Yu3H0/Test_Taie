

package pascal.taie.analysis.pta.core.solver;

import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.language.classes.JField;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;
import pascal.taie.util.collection.TwoKeyMultiMap;

import java.util.Set;

/**
 * This {@link ParamProvider} ignored all parameters.
 */
public enum EmptyParamProvider implements ParamProvider {

    INSTANCE;

    public static EmptyParamProvider get() {
        return INSTANCE;
    }

    @Override
    public Set<Obj> getThisObjs() {
        return Set.of();
    }

    @Override
    public Set<Obj> getParamObjs(int i) {
        return Set.of();
    }

    @Override
    public TwoKeyMultiMap<Obj, JField, Obj> getFieldObjs() {
        return Maps.emptyTwoKeyMultiMap();
    }

    @Override
    public MultiMap<Obj, Obj> getArrayObjs() {
        return Maps.emptyMultiMap();
    }
}
