package pascal.taie.analysis.pta.plugin.android.icc;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.plugin.util.InvokeHandler;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.Invoke;

import static pascal.taie.analysis.pta.plugin.util.InvokeUtils.BASE;

/**
 * Models componentName init methods.
 */
public class ComponentNameInfoModel extends ICCHandler {

    public ComponentNameInfoModel(ICCContext context) {
        super(context);
    }

    @InvokeHandler(signature = {
            "<android.content.ComponentName: void <init>(java.lang.String,java.lang.String)>",
            "<android.content.ComponentName: void <init>(android.content.Context,java.lang.String)>",
            "<android.content.ComponentName: void <init>(android.content.Context,java.lang.Class)>"},
            argIndexes = {BASE})
    public void componentNameInit(Context context, Invoke invoke, PointsToSet pts) {
        pts.forEach(csObj -> {
            Var arg = invoke.getInvokeExp().getArg(1);
            handlerContext.componentName2Info().put(csObj, csManager.getCSVar(context, arg));
        });
    }

}
