

package pascal.taie.analysis.pta.plugin.invokedynamic;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.plugin.util.AnalysisModelPlugin;
import pascal.taie.analysis.pta.plugin.util.CSObjs;
import pascal.taie.analysis.pta.plugin.util.InvokeHandler;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.ir.exp.MethodType;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.type.Type;

import java.util.List;

/**
 * Models invocations to MethodType.methodType(*);
 */
public class MethodTypeModel extends AnalysisModelPlugin {

    MethodTypeModel(Solver solver) {
        super(solver);
    }

    @InvokeHandler(signature = "<java.lang.invoke.MethodType: java.lang.invoke.MethodType methodType(java.lang.Class)>", argIndexes = {0})
    public void methodType1Class(Context context, Invoke invoke, PointsToSet clsObjs) {
        Var result = invoke.getResult();
        if (result != null) {
            clsObjs.forEach(obj -> {
                Type retType = CSObjs.toType(obj);
                if (retType != null) {
                    MethodType mt = MethodType.get(List.of(), retType);
                    Obj mtObj = heapModel.getConstantObj(mt);
                    solver.addVarPointsTo(context, result, mtObj);
                }
            });
        }
    }

    @InvokeHandler(signature = "<java.lang.invoke.MethodType: java.lang.invoke.MethodType methodType(java.lang.Class,java.lang.Class)>", argIndexes = {0, 1})
    public void methodType2Classes(Context context, Invoke invoke,
                                   PointsToSet retObjs, PointsToSet paramObjs) {
        Var result = invoke.getResult();
        if (result != null) {
            retObjs.forEach(retObj -> {
                Type retType = CSObjs.toType(retObj);
                if (retType != null) {
                    paramObjs.forEach(paramObj -> {
                        Type paramType = CSObjs.toType(paramObj);
                        if (paramType != null) {
                            MethodType mt = MethodType.get(List.of(paramType), retType);
                            Obj mtObj = heapModel.getConstantObj(mt);
                            solver.addVarPointsTo(context, result, mtObj);
                        }
                    });
                }
            });
        }
    }

    @InvokeHandler(signature = "<java.lang.invoke.MethodType: java.lang.invoke.MethodType methodType(java.lang.Class,java.lang.invoke.MethodType)>", argIndexes = {0, 1})
    public void methodTypeClassMT(Context context, Invoke invoke,
                                  PointsToSet retObjs, PointsToSet mtObjs) {
        Var result = invoke.getResult();
        if (result != null) {
            retObjs.forEach(retObj -> {
                Type retType = CSObjs.toType(retObj);
                if (retType != null) {
                    mtObjs.forEach(mtObj -> {
                        MethodType mt = CSObjs.toMethodType(mtObj);
                        if (mt != null) {
                            MethodType resultMT = MethodType.get(mt.getParamTypes(), retType);
                            Obj resultMTObj = heapModel.getConstantObj(resultMT);
                            solver.addVarPointsTo(context, result, resultMTObj);
                        }
                    });
                }
            });
        }
    }
}
