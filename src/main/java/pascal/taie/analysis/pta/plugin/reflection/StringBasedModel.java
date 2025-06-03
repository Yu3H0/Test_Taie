

package pascal.taie.analysis.pta.plugin.reflection;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.plugin.util.CSObjs;
import pascal.taie.analysis.pta.plugin.util.InvokeHandler;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.Set;

import static pascal.taie.analysis.pta.plugin.util.InvokeUtils.BASE;

public class StringBasedModel extends InferenceModel {

    StringBasedModel(Solver solver, MetaObjHelper helper, Set<Invoke> invokesWithLog) {
        super(solver, helper, invokesWithLog);
    }

    @InvokeHandler(signature = {
            "<java.lang.Class: java.lang.Class forName(java.lang.String)>",
            "<java.lang.Class: java.lang.Class forName(java.lang.String,boolean,java.lang.ClassLoader)>",
            "<java.lang.ClassLoader: java.lang.Class loadClass(java.lang.String)>"},
            argIndexes = {0})
    public void classForName(Context context, Invoke invoke, PointsToSet nameObjs) {
        if (invokesWithLog.contains(invoke)) {
            return;
        }
        nameObjs.forEach(obj ->
                classForNameKnown(context, invoke, CSObjs.toString(obj)));
    }

    @InvokeHandler(signature = {
            "<java.lang.Class: java.lang.reflect.Constructor getConstructor(java.lang.Class[])>",
            "<java.lang.Class: java.lang.reflect.Constructor getDeclaredConstructor(java.lang.Class[])>"},
            argIndexes = {BASE})
    public void classGetConstructor(Context context, Invoke invoke, PointsToSet classObjs) {
        if (invokesWithLog.contains(invoke)) {
            return;
        }
        classObjs.forEach(obj ->
                classGetConstructorKnown(context, invoke, CSObjs.toClass(obj)));
    }

    @InvokeHandler(signature = {
            "<java.lang.Class: java.lang.reflect.Method getMethod(java.lang.String,java.lang.Class[])>",
            "<java.lang.Class: java.lang.reflect.Method getDeclaredMethod(java.lang.String,java.lang.Class[])>"},
            argIndexes = {BASE, 0})
    public void classGetMethod(Context context, Invoke invoke,
                               PointsToSet classObjs, PointsToSet nameObjs) {
        if (invokesWithLog.contains(invoke)) {
            return;
        }
        classObjs.forEach(classObj -> {
            JClass clazz = CSObjs.toClass(classObj);
            nameObjs.forEach(nameObj -> {
                String name = CSObjs.toString(nameObj);
                classGetMethodKnown(context, invoke, clazz, name);
            });
        });
    }

    @InvokeHandler(signature = {
            "<java.lang.reflect.Method: java.lang.Class getDeclaringClass()>"
    }, argIndexes = {BASE})
    public void methodGetDeclaringClass(Context context, Invoke invoke, PointsToSet methodObjs) {
        if (invokesWithLog.contains(invoke)) {
            return;
        }
        methodObjs.forEach(methodObj -> {
            JMethod jMethod = CSObjs.toMethod(methodObj);
            if (jMethod != null) {
                classForNameKnown(context, invoke, jMethod.getDeclaringClass().getName());
            }
        });
    }
}
