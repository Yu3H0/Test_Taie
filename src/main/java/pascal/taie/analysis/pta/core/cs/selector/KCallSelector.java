

package pascal.taie.analysis.pta.core.cs.selector;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JMethod;

class KCallSelector extends KLimitingSelector<Invoke> {

    public KCallSelector(int k, int hk) {
        super(k, hk);
    }

    @Override
    public Context selectContext(CSCallSite callSite, JMethod callee) {
        return factory.append(
                callSite.getContext(), callSite.getCallSite(), limit);
    }

    @Override
    public Context selectContext(CSCallSite callSite, CSObj recv, JMethod callee) {
        Context parent = callSite.getContext();
        return factory.append(parent, callSite.getCallSite(), limit);
    }
}
