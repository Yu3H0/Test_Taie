package pascal.taie.analysis.pta.plugin.android.misc;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.plugin.util.InvokeHandler;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JClass;

import static pascal.taie.analysis.pta.plugin.util.InvokeUtils.BASE;

public class ApplicationModel extends AndroidMiscHandler {

    public ApplicationModel(AndroidMiscContext specificContext) {
        super(specificContext);
    }

    @InvokeHandler(signature = {
            "<android.app.Activity: android.app.Application getApplication()>",
            "<android.app.Service: android.app.Application getApplication()>"},
            argIndexes = {BASE})
    public void getApplication(Context context, Invoke invoke, PointsToSet pts) {
        Var result = invoke.getResult();
        JClass application = handlerContext.apkInfo().application();
        if (result != null && application != null) {
            solver.addVarPointsTo(context, result, handlerContext.androidObjManager().getComponentObj(application));
        }
    }

}
