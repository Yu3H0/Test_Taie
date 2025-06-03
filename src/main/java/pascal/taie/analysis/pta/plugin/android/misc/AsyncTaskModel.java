package pascal.taie.analysis.pta.plugin.android.misc;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSVar;
import pascal.taie.analysis.pta.plugin.android.AndroidTransferEdge;
import pascal.taie.analysis.pta.plugin.util.InvokeHandler;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.classes.Subsignature;
import pascal.taie.language.type.ClassType;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static pascal.taie.analysis.pta.plugin.util.InvokeUtils.BASE;

public class AsyncTaskModel extends AndroidMiscHandler {

    private static final Subsignature ON_PRE_EXECUTE = Subsignature.get("void onPreExecute()");

    private static final Subsignature DO_IN_BACKGROUND_SUB_SIG = Subsignature.get("java.lang.Object doInBackground(java.lang.Object[])");

    private static final Subsignature ON_PROGRESS_UPDATE = Subsignature.get("void onProgressUpdate(java.lang.Object[])");

    private static final Subsignature ON_POST_EXECUTE_SUB_SIG = Subsignature.get("void onPostExecute(java.lang.Object)");

    private static final Subsignature ON_CANCEL = Subsignature.get("void onCancelled()");

    private static final List<Subsignature> ASYNC_TASK_METHODS = List.of(
            ON_PRE_EXECUTE,
            DO_IN_BACKGROUND_SUB_SIG,
            ON_PROGRESS_UPDATE,
            // onPostExecute is handled when doInBackground is handled
            ON_CANCEL
    );

    public AsyncTaskModel(AndroidMiscContext specificContext) {
        super(specificContext);
    }

    @InvokeHandler(signature = {
            "<android.os.AsyncTask: android.os.AsyncTask execute(java.lang.Object[])>"},
            argIndexes = {BASE, 0})
    public void asyncTaskExecute(Context context, Invoke invoke, PointsToSet baseObjs, PointsToSet argObjs) {
        AtomicReference<JMethod> onPostExecute = new AtomicReference<>();
        AtomicReference<JMethod> doInBack = new AtomicReference<>();
        baseObjs.forEach(csObj -> {
            if (csObj.getObject().getType() instanceof ClassType classType) {
                handlerContext.lifecycleHelper().getLifeCycleMethods(classType.getJClass())
                        .forEach(asyncTaskMethod -> {
                            Subsignature subsignature = asyncTaskMethod.getSubsignature();
                            addEntryPoint(asyncTaskMethod, csObj.getObject());
                            // process doInBackground
                            if (subsignature.equals(DO_IN_BACKGROUND_SUB_SIG)) {
                                doInBack.set(asyncTaskMethod);
                                solver.addVarPointsTo(emptyContext, asyncTaskMethod.getIR().getParam(0), argObjs);
                            }
                            if (subsignature.equals(ON_POST_EXECUTE_SUB_SIG)) {
                                onPostExecute.set(asyncTaskMethod);
                            }
                        });
                // process onPostExecute
                if (onPostExecute.get() != null && doInBack.get() != null) {
                    addEntryPoint(onPostExecute.get(), csObj.getObject());
                    CSVar postExecuteParamVar = csManager.getCSVar(emptyContext, onPostExecute.get().getIR().getParam(0));
                    doInBack.get().getIR().getReturnVars().forEach(returnVar -> {
                        CSVar csReturnVar = csManager.getCSVar(emptyContext, returnVar);
                        solver.addPFGEdge(new AndroidTransferEdge(csReturnVar, postExecuteParamVar));
                    });
                }
//                for (Subsignature subsignature : ASYNC_TASK_METHODS) {
//                    JMethod asyncTaskMethod = classType.getJClass().getDeclaredMethod(subsignature);
//                    if (asyncTaskMethod != null) {
//                        addEntryPoint(asyncTaskMethod, csObj.getObject());
//                        // process doInBackground
//                        if (subsignature.equals(DO_IN_BACKGROUND_SUB_SIG)) {
//                            solver.addVarPointsTo(emptyContext, asyncTaskMethod.getIR().getParam(0), argObjs);
//
//                            // process onPostExecute
//                            JMethod onPostExecute = classType.getJClass().getDeclaredMethod(ON_POST_EXECUTE_SUB_SIG);
//                            if (onPostExecute != null) {
//                                addEntryPoint(onPostExecute, csObj.getObject());
//                                CSVar postExecuteParamVar = csManager.getCSVar(emptyContext, onPostExecute.getIR().getParam(0));
//                                asyncTaskMethod.getIR().getReturnVars().forEach(returnVar -> {
//                                    CSVar csReturnVar = csManager.getCSVar(emptyContext, returnVar);
//                                    solver.addPFGEdge(new AndroidTransferEdge(csReturnVar, postExecuteParamVar));
//                                });
//                            }
//                        }
//                    }
//                }
            }
        });
    }

}
