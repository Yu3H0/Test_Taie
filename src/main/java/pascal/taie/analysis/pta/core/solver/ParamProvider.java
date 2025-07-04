

package pascal.taie.analysis.pta.core.solver;


import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.language.classes.JField;
import pascal.taie.util.collection.MultiMap;
import pascal.taie.util.collection.TwoKeyMultiMap;

import java.util.Set;

/**
 * The parameter object provider for this variable/parameters of the entry method.
 * This class also supports supplying objects pointed to by fields
 * of parameter objects, as well as elements of array objects.
 *
 * @see EmptyParamProvider
 * @see DeclaredParamProvider
 * @see SpecifiedParamProvider
 */
public interface ParamProvider {

    /**
     * @return the objects for this variable.
     */
    Set<Obj> getThisObjs();

    /**
     * @return the objects for i-th parameter (starting from 0).
     */
    Set<Obj> getParamObjs(int i);

    /**
     * @return the objects pointed to by parameter objects' fields.
     */
    TwoKeyMultiMap<Obj, JField, Obj> getFieldObjs();

    /**
     * @return the elements contained in parameter arrays.
     */
    MultiMap<Obj, Obj> getArrayObjs();
}
