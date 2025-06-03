package pascal.taie.analysis.pta.plugin.android.icc;

import pascal.taie.analysis.graph.callgraph.Edge;
import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.core.cs.element.CSVar;
import pascal.taie.analysis.pta.plugin.util.InvokeUtils;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JMethod;

/**
 * Models message related methods.
 */
public class AidlHandler extends ICCHandler {

    private static final String AS_INTERFACE = "asInterface(android.os.IBinder)";

    public AidlHandler(ICCContext context) {
        super(context);
    }

    @Override
    public void onNewCallEdge(Edge<CSCallSite, CSMethod> edge) {
        JMethod callee = edge.getCallee().getMethod();
        CSCallSite csCallSite = edge.getCallSite();
        Invoke callSite = csCallSite.getCallSite();
        if (callSite.isStatic() && callee.getSignature().contains(AS_INTERFACE)) {
            Context context = csCallSite.getContext();
            CSObj aidlObj = generateInvokeResultObj(context, callSite);
            if (aidlObj != null) {
                CSVar iBinderArg = csManager.getCSVar(context, InvokeUtils.getVar(callSite, 0));
                CSVar aidl = csManager.getCSVar(context, InvokeUtils.getVar(callSite, InvokeUtils.RESULT));
                handlerContext.iBinder2Aidl().put(iBinderArg, aidl);
            }
        }
    }

}
