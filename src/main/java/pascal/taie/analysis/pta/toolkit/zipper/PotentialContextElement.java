

package pascal.taie.analysis.pta.toolkit.zipper;

import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.analysis.pta.toolkit.PointerAnalysisResultEx;
import pascal.taie.analysis.pta.toolkit.util.OAGs;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.type.Type;
import pascal.taie.util.Canonicalizer;
import pascal.taie.util.Indexer;
import pascal.taie.util.SimpleIndexer;
import pascal.taie.util.collection.IndexerBitSet;
import pascal.taie.util.collection.Maps;

import java.util.Map;
import java.util.Set;

/**
 * For each object type t, this class compute the set of methods
 * which objects of t could potentially be their context element.
 */
class PotentialContextElement {

    /**
     * Map from each type to PCE methods of the objects of the type.
     */
    private final Map<Type, Set<JMethod>> type2PCEMethods;

    PotentialContextElement(PointerAnalysisResultEx pta,
                            ObjectAllocationGraph oag) {
        Map<Obj, Set<JMethod>> invokedMethods = OAGs.computeInvokedMethods(pta);
        Canonicalizer<Set<JMethod>> canonicalizer = new Canonicalizer<>();
        Indexer<JMethod> methodIndexer = new SimpleIndexer<>(
                pta.getBase().getCallGraph().getNodes());
        Set<Type> types = pta.getObjectTypes();
        type2PCEMethods = Maps.newConcurrentMap(types.size());
        types.parallelStream().forEach(type -> {
            Set<JMethod> methods = new IndexerBitSet<>(methodIndexer, true);
            // add invoked methods on objects of type
            for (Obj obj : pta.getObjectsOf(type)) {
                methods.addAll(invokedMethods.get(obj));
            }
            // add invoked methods on allocated objects of type
            for (Obj allocatee : oag.getAllocateesOf(type)) {
                methods.addAll(invokedMethods.get(allocatee));
            }
            type2PCEMethods.put(type, canonicalizer.get(methods));
        });
    }

    Set<JMethod> pceMethodsOf(Type type) {
        return type2PCEMethods.get(type);
    }
}
