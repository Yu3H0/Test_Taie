

package pascal.taie.analysis.pta.core.cs.selector;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.core.heap.NewObj;
import pascal.taie.language.classes.JMethod;

/**
 * Selector for context-insensitive analysis.
 * Context insensitivity does not use any context elements,
 * thus the type of context elements is irrelevant.
 */
class ContextInsensitiveSelector extends AbstractContextSelector<Void> {

    @Override
    public Context selectContext(CSCallSite callSite, JMethod callee) {
        return getEmptyContext();
    }

    @Override
    public Context selectContext(CSCallSite callSite, CSObj recv, JMethod callee) {
        return getEmptyContext();
    }

    @Override
    protected Context selectNewObjContext(CSMethod method, NewObj obj) {
        return getEmptyContext();
    }
}
