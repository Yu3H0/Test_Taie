

package pascal.taie.analysis.pta.plugin.invokedynamic;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.plugin.util.AnalysisModelPlugin;
import pascal.taie.analysis.pta.plugin.util.CSObjs;
import pascal.taie.analysis.pta.plugin.util.InvokeHandler;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.ir.exp.MethodHandle;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.classes.Reflections;

import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Models java.lang.invoke.MethodHandles.Lookup.find*(...).
 * For details, please refer to
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/invoke/MethodHandles.Lookup.html">MethodHandles.Lookup.html</a>
 * TODO: take Lookup.lookupClass's visibility into account
 * TODO: take MethodType into account
 */
public class LookupModel extends AnalysisModelPlugin {

    LookupModel(Solver solver) {
        super(solver);
    }

    @InvokeHandler(signature = "<java.lang.invoke.MethodHandles$Lookup: java.lang.invoke.MethodHandle findConstructor(java.lang.Class,java.lang.invoke.MethodType)>", argIndexes = {0})
    public void findConstructor(Context context, Invoke invoke, PointsToSet clsObjs) {
        Var result = invoke.getResult();
        if (result != null) {
            clsObjs.forEach(clsObj -> {
                JClass cls = CSObjs.toClass(clsObj);
                if (cls != null) {
                    Reflections.getDeclaredConstructors(cls)
                            .map(ctor -> MethodHandle.get(
                                    MethodHandle.Kind.REF_newInvokeSpecial,
                                    ctor.getRef()))
                            .map(heapModel::getConstantObj)
                            .forEach(mhObj -> solver.addVarPointsTo(context, result, mhObj));
                }
            });
        }
    }

    @InvokeHandler(signature = "<java.lang.invoke.MethodHandles$Lookup: java.lang.invoke.MethodHandle findVirtual(java.lang.Class,java.lang.String,java.lang.invoke.MethodType)>", argIndexes = {0, 1})
    public void findVirtual(Context context, Invoke invoke,
                            PointsToSet clsObjs, PointsToSet nameObjs) {
        // TODO: find private methods in (direct/indirect) super class.
        findMethod(context, invoke, clsObjs, nameObjs, (cls, name) ->
                        Reflections.getDeclaredMethods(cls, name)
                                .filter(Predicate.not(JMethod::isStatic)),
                MethodHandle.Kind.REF_invokeVirtual);
    }

    @InvokeHandler(signature = "<java.lang.invoke.MethodHandles$Lookup: java.lang.invoke.MethodHandle findStatic(java.lang.Class,java.lang.String,java.lang.invoke.MethodType)>", argIndexes = {0, 1})
    public void findStatic(Context context, Invoke invoke,
                           PointsToSet clsObjs, PointsToSet nameObjs) {
        // TODO: find static methods in (direct/indirect) super class.
        findMethod(context, invoke, clsObjs, nameObjs, (cls, name) ->
                        Reflections.getDeclaredMethods(cls, name)
                                .filter(JMethod::isStatic),
                MethodHandle.Kind.REF_invokeStatic);
    }

    private void findMethod(Context context, Invoke invoke,
            PointsToSet clsObjs, PointsToSet nameObjs,
            BiFunction<JClass, String, Stream<JMethod>> getter,
            MethodHandle.Kind kind) {
        Var result = invoke.getResult();
        if (result != null) {
            clsObjs.forEach(clsObj -> {
                JClass cls = CSObjs.toClass(clsObj);
                if (cls != null) {
                    nameObjs.forEach(nameObj -> {
                        String name = CSObjs.toString(nameObj);
                        if (name != null) {
                            getter.apply(cls, name)
                                    .map(mtd -> MethodHandle.get(kind, mtd.getRef()))
                                    .map(heapModel::getConstantObj)
                                    .forEach(mhObj ->
                                            solver.addVarPointsTo(context, result, mhObj));
                        }
                    });
                }
            });
        }
    }
}
