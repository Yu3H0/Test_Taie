package pascal.taie.analysis.pta.plugin.android;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.analysis.pta.core.solver.DeclaredParamProvider;
import pascal.taie.analysis.pta.core.solver.EntryPoint;
import pascal.taie.analysis.pta.core.solver.SpecifiedParamProvider;
import pascal.taie.analysis.pta.plugin.util.AnalysisModelPlugin;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JMethod;

import java.util.Map;

/**
 * Abstract class for android handlers.
 */
public abstract class AndroidHandler extends AnalysisModelPlugin {

    protected final AndroidContext handlerContext;

    protected AndroidHandler(AndroidContext context) {
        super(context.solver());
        this.handlerContext = context;
    }

    protected void addEntryPoint(JMethod entry, Obj thisObj) {
        addEntryPoint(entry, thisObj, null);
    }

    protected void addEntryPoint(JMethod entry, Obj thisObj, Map<Integer, Obj> paramIndex) {
        SpecifiedParamProvider.Builder builder =
                new SpecifiedParamProvider.Builder(entry);
        builder.addThisObj(thisObj)
                .setDelegate(new DeclaredParamProvider(entry, solver.getHeapModel()));
        if (paramIndex != null) {
            paramIndex.forEach(builder::addParamObj);
        }
        solver.addEntryPoint(new EntryPoint(entry, builder.build()));
    }

    protected CSObj generateInvokeResultObj(Context context, Invoke invoke) {
        Var result = invoke.getResult();
        if (result != null) {
            CSObj resultCSObj = csManager.getCSObj(context, handlerContext.androidObjManager()
                    .getAndroidSpecificObj(result, invoke));
            solver.addVarPointsTo(context, result, resultCSObj);
            return resultCSObj;
        }
        return null;
    }

}
