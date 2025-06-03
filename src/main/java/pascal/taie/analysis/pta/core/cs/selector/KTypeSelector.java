

package pascal.taie.analysis.pta.core.cs.selector;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.type.Type;

class KTypeSelector extends KLimitingSelector<Type> {

    public KTypeSelector(int k, int hk) {
        super(k, hk);
    }

    @Override
    public Context selectContext(CSCallSite callSite, JMethod callee) {
        return callSite.getContext();
    }

    @Override
    public Context selectContext(CSCallSite callSite, CSObj recv, JMethod callee) {
        return factory.append(
                recv.getContext(), recv.getObject().getContainerType(), limit);
    }
}
