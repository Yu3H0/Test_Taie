

package pascal.taie.analysis.pta.plugin.reflection;

import pascal.taie.World;
import pascal.taie.analysis.graph.callgraph.Edge;
import pascal.taie.analysis.graph.flowgraph.FlowKind;
import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.ArrayIndex;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.core.cs.element.CSVar;
import pascal.taie.analysis.pta.core.cs.element.InstanceField;
import pascal.taie.analysis.pta.core.cs.element.StaticField;
import pascal.taie.analysis.pta.core.heap.Descriptor;
import pascal.taie.analysis.pta.core.heap.MockObj;
import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.analysis.pta.core.solver.PointerFlowEdge;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.plugin.util.AnalysisModelPlugin;
import pascal.taie.analysis.pta.plugin.util.CSObjs;
import pascal.taie.analysis.pta.plugin.util.InvokeHandler;
import pascal.taie.analysis.pta.plugin.util.InvokeUtils;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.ir.exp.IntLiteral;
import pascal.taie.ir.exp.NewArray;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.ir.stmt.New;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JField;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.classes.Subsignature;
import pascal.taie.language.type.ArrayType;
import pascal.taie.language.type.ClassType;
import pascal.taie.language.type.ReferenceType;
import pascal.taie.language.type.Type;
import pascal.taie.language.type.VoidType;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static pascal.taie.analysis.graph.flowgraph.FlowKind.INSTANCE_STORE;
import static pascal.taie.analysis.graph.flowgraph.FlowKind.PARAMETER_PASSING;
import static pascal.taie.analysis.graph.flowgraph.FlowKind.RETURN;
import static pascal.taie.analysis.graph.flowgraph.FlowKind.STATIC_STORE;
import static pascal.taie.analysis.pta.plugin.reflection.ReflectionAnalysis.IMPRECISE_THRESHOLD;
import static pascal.taie.analysis.pta.plugin.util.InvokeUtils.BASE;

/**
 * Models reflective-action methods, currently supports
 * <ul>
 *     <li>Class.newInstance()
 *     <li>Constructor.newInstance(Object[])
 *     <li>Method.invoke(Object,Object[])
 *     <li>Field.get(Object)
 *     <li>Field.set(Object,Object)
 *     <li>Array.newInstance(Class,int)
 * </ul>
 * TODO: check accessibility
 */
public class ReflectiveActionModel extends AnalysisModelPlugin {

    /**
     * Descriptor for objects created by reflective newInstance() calls.
     */
    private static final Descriptor REF_OBJ_DESC = () -> "ReflectiveObj";

    private final Subsignature initNoArg;

    private final MetaObjHelper helper;

    private final TypeMatcher typeMatcher;

    /**
     * Associates argument variable (Object[]) to reflective call edges.
     */
    private final MultiMap<CSVar, ReflectiveCallEdge> reflectiveArgs = Maps.newMultiMap();

    /**
     * Set of invocations that are annotated by the reflection log.
     */
    private final Set<Invoke> invokesWithLog;

    /**
     * Records all reflective targets resolved by reflection analysis.
     */
    private final MultiMap<Invoke, Object> allTargets = Maps.newMultiMap();

    ReflectiveActionModel(Solver solver, MetaObjHelper helper,
                          TypeMatcher typeMatcher, Set<Invoke> invokesWithLog) {
        super(solver);
        initNoArg = Subsignature.getNoArgInit();
        this.helper = helper;
        this.typeMatcher = typeMatcher;
        this.invokesWithLog = invokesWithLog;
    }

    @InvokeHandler(signature = "<java.lang.Class: java.lang.Object newInstance()>", argIndexes = {BASE})
    public void classNewInstance(Context context, Invoke invoke, PointsToSet classes) {
        classes.forEach(obj -> {
            if (isInvalidTarget(invoke, obj)) {
                return;
            }
            JClass clazz = CSObjs.toClass(obj);
            if (clazz != null) {
                JMethod init = clazz.getDeclaredMethod(initNoArg);
                if (init != null && !typeMatcher.isUnmatched(invoke, init)) {
                    ClassType type = clazz.getType();
                    CSObj csNewObj = newReflectiveObj(context, invoke, type);
                    addReflectiveCallEdge(context, invoke, csNewObj, init, null);
                }
            }
        });
    }

    @InvokeHandler(signature = "<java.lang.reflect.Constructor: java.lang.Object newInstance(java.lang.Object[])>", argIndexes = {BASE})
    public void constructorNewInstance(Context context, Invoke invoke, PointsToSet constructors) {
        constructors.forEach(obj -> {
            if (isInvalidTarget(invoke, obj)) {
                return;
            }
            JMethod constructor = CSObjs.toConstructor(obj);
            if (constructor != null && !typeMatcher.isUnmatched(invoke, constructor)) {
                ClassType type = constructor.getDeclaringClass().getType();
                CSObj csNewObj = newReflectiveObj(context, invoke, type);
                addReflectiveCallEdge(context, invoke, csNewObj,
                        constructor, invoke.getInvokeExp().getArg(0));
            }
        });
    }

    private CSObj newReflectiveObj(Context context, Invoke invoke, ReferenceType type) {
        Obj newObj = heapModel.getMockObj(REF_OBJ_DESC,
                invoke, type, invoke.getContainer());
        // TODO: double-check if the heap context is proper
        CSObj csNewObj = csManager.getCSObj(context, newObj);
        Var result = invoke.getResult();
        if (result != null) {
            solver.addVarPointsTo(context, result, csNewObj);
        }
        return csNewObj;
    }

    @InvokeHandler(signature = "<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object,java.lang.Object[])>", argIndexes = {BASE, 0})
    public void methodInvoke(Context context, Invoke invoke,
                             PointsToSet mtdObjs, PointsToSet recvObjs) {
        Var argsVar = invoke.getInvokeExp().getArg(1);
        mtdObjs.forEach(mtdObj -> {
            if (isInvalidTarget(invoke, mtdObj)) {
                return;
            }
            JMethod target = CSObjs.toMethod(mtdObj);
            if (target != null && !typeMatcher.isUnmatched(invoke, target)) {
                if (target.isStatic()) {
                    addReflectiveCallEdge(context, invoke, null, target, argsVar);
                } else {
                    recvObjs.forEach(recvObj ->
                            addReflectiveCallEdge(context, invoke, recvObj, target, argsVar));
                }
            }
        });
    }

    @InvokeHandler(signature = "<java.lang.reflect.Field: java.lang.Object get(java.lang.Object)>", argIndexes = {BASE, 0})
    public void fieldGet(Context context, Invoke invoke,
                         PointsToSet fldObjs, PointsToSet baseObjs) {
        Var result = invoke.getResult();
        if (result == null) {
            return;
        }
        CSVar to = csManager.getCSVar(context, result);
        fldObjs.forEach(fldObj -> {
            if (isInvalidTarget(invoke, fldObj)) {
                return;
            }
            JField field = CSObjs.toField(fldObj);
            if (field != null) {
                if (field.isStatic()) {
                    StaticField sField = csManager.getStaticField(field);
                    solver.addPFGEdge(sField, to, FlowKind.STATIC_LOAD);
                } else {
                    Type declType = field.getDeclaringClass().getType();
                    baseObjs.forEach(baseObj -> {
                        Type objType = baseObj.getObject().getType();
                        if (typeSystem.isSubtype(declType, objType)) {
                            InstanceField ifield = csManager.getInstanceField(baseObj, field);
                            solver.addPFGEdge(ifield, to, FlowKind.INSTANCE_LOAD);
                            allTargets.put(invoke, field); // record target
                        }
                    });
                }
            }
        });
    }

    @InvokeHandler(signature = "<java.lang.reflect.Field: void set(java.lang.Object,java.lang.Object)>", argIndexes = {BASE, 0})
    public void fieldSet(Context context, Invoke invoke,
                         PointsToSet fldObjs, PointsToSet baseObjs) {
        CSVar from = csManager.getCSVar(context, invoke.getInvokeExp().getArg(1));
        fldObjs.forEach(fldObj -> {
            if (isInvalidTarget(invoke, fldObj)) {
                return;
            }
            JField field = CSObjs.toField(fldObj);
            if (field != null) {
                if (field.isStatic()) {
                    StaticField sfield = csManager.getStaticField(field);
                    solver.addPFGEdge(new PointerFlowEdge(
                            STATIC_STORE, from, sfield),
                            sfield.getType());
                } else {
                    Type declType = field.getDeclaringClass().getType();
                    baseObjs.forEach(baseObj -> {
                        Type objType = baseObj.getObject().getType();
                        if (typeSystem.isSubtype(declType, objType)) {
                            InstanceField ifield = csManager.getInstanceField(baseObj, field);
                            solver.addPFGEdge(new PointerFlowEdge(
                                    INSTANCE_STORE, from, ifield),
                                    ifield.getType());
                            allTargets.put(invoke, field); // record target
                        }
                    });
                }
            }
        });
    }

    @InvokeHandler(signature = {
            "<java.lang.reflect.Array: java.lang.Object newInstance(java.lang.Class,int)>",
            "<java.lang.reflect.Array: java.lang.Object newInstance(java.lang.Class,int[])>"
    }, argIndexes = {0, 1})
    public void arrayNewInstance(Context context, Invoke invoke, PointsToSet pts, PointsToSet arrayObjs) {
        Var result = invoke.getResult();
        if (result == null) {
            return;
        }
        pts.forEach(obj -> {
            if (isInvalidTarget(invoke, obj)) {
                return;
            }
            Type baseType = CSObjs.toType(obj);
            if (baseType != null && !(baseType instanceof VoidType)) {
                List<Integer> dimensions = new ArrayList<>();

                if (InvokeUtils.getVar(invoke, 1).getType() instanceof ArrayType) {
                    arrayObjs.forEach(arrayObj -> {
                        if (arrayObj.getObject().getAllocation() instanceof New stmt
                                && stmt.getRValue() instanceof NewArray newArray
                                && newArray.getLength().isConst()
                                && newArray.getLength().getConstValue() instanceof IntLiteral intLiteral) {
                            dimensions.add(intLiteral.getValue());
                        }
                    });
                } else {
                    dimensions.add(1);
                }

                dimensions.forEach(d -> {
                    ArrayType arrayType = typeSystem.getArrayType(baseType, d);
                    allTargets.put(invoke, arrayType);
                    if (allTargets.get(invoke).size() > IMPRECISE_THRESHOLD) {
                        return;
                    }
                    CSObj csNewArray = newReflectiveObj(context, invoke, arrayType);
                    solver.addVarPointsTo(context, result, csNewArray);
                    processNewMultiArray(context, invoke, csNewArray.getObject(), arrayType, d);
                });

            }
        });
    }

    private void processNewMultiArray(
            Context arrayContext, Invoke invoke, Obj array, ArrayType arrayType, int dimensions) {
        Obj[] newArrays = new MockObj[dimensions - 1];
        for (int i = 1; i < dimensions; ++i) {
            Type type = arrayType.elementType();
            newArrays[i - 1] = heapModel.getMockObj(REF_OBJ_DESC,
                    invoke + "[" + (i - 1) + "]", type, invoke.getContainer());
        }
        for (Obj newArray : newArrays) {
            Context elemContext = selector
                    .selectHeapContext(csManager.getCSMethod(arrayContext, invoke.getContainer()), newArray);
            CSObj arrayObj = csManager.getCSObj(arrayContext, array);
            ArrayIndex arrayIndex = csManager.getArrayIndex(arrayObj);
            solver.addPointsTo(arrayIndex, elemContext, newArray);
            array = newArray;
            arrayContext = elemContext;
        }
    }

    /**
     * If a reflective invocation {@code invoke} is annotated by the log,
     * and the given {@code metaObj} is not generated by the log, then we treat
     * {@code metaObj} as an invalid target for {@code invoke}.
     */
    private boolean isInvalidTarget(Invoke invoke, CSObj metaObj) {
        return invokesWithLog.contains(invoke) && !helper.isLogMetaObj(metaObj);
    }

    private void addReflectiveCallEdge(
            Context callerCtx, Invoke callSite,
            @Nullable CSObj recvObj, JMethod callee, Var args) {
        if (!callee.isConstructor() && !callee.isStatic()) {
            // dispatch for instance method (except constructor)
            assert recvObj != null : "recvObj is required for instance method";
            callee = hierarchy.dispatch(recvObj.getObject().getType(),
                    callee.getRef());
            if (callee == null) {
                return;
            }
        }
        allTargets.put(callSite, callee); // record target
        if (World.get().getOptions().isAndroidMode()) {
            if (!callee.isApplication() || allTargets.get(callSite).size() > IMPRECISE_THRESHOLD) {
                return;
            }
        }
        CSCallSite csCallSite = csManager.getCSCallSite(callerCtx, callSite);
        Context calleeCtx;
        if (callee.isStatic()) {
            calleeCtx = selector.selectContext(csCallSite, callee);
        } else {
            calleeCtx = selector.selectContext(csCallSite, recvObj, callee);
            // pass receiver object to 'this' variable of callee
            solver.addVarPointsTo(calleeCtx, callee.getIR().getThis(), recvObj);
        }
        ReflectiveCallEdge callEdge = new ReflectiveCallEdge(csCallSite,
                csManager.getCSMethod(calleeCtx, callee), args);
        solver.addCallEdge(callEdge);
    }

    @Override
    public void onNewCallEdge(Edge<CSCallSite, CSMethod> edge) {
        if (edge instanceof ReflectiveCallEdge refEdge) {
            Context callerCtx = refEdge.getCallSite().getContext();
            // pass argument
            Var args = refEdge.getArgs();
            if (args != null) {
                CSVar csArgs = csManager.getCSVar(callerCtx, args);
                passReflectiveArgs(refEdge, solver.getPointsToSetOf(csArgs));
                // record args for later-arrive array objects
                reflectiveArgs.put(csArgs, refEdge);
            }
            // pass return value
            Invoke invoke = refEdge.getCallSite().getCallSite();
            Context calleeCtx = refEdge.getCallee().getContext();
            JMethod callee = refEdge.getCallee().getMethod();
            Var result = invoke.getResult();
            if (result != null && isConcerned(callee.getReturnType())) {
                CSVar csResult = csManager.getCSVar(callerCtx, result);
                callee.getIR().getReturnVars().forEach(ret -> {
                    CSVar csRet = csManager.getCSVar(calleeCtx, ret);
                    solver.addPFGEdge(csRet, csResult, RETURN);
                });
            }
        }
    }

    private void passReflectiveArgs(ReflectiveCallEdge edge, PointsToSet arrays) {
        Context calleeCtx = edge.getCallee().getContext();
        JMethod callee = edge.getCallee().getMethod();
        arrays.forEach(array -> {
            ArrayIndex elems = csManager.getArrayIndex(array);
            callee.getIR().getParams().forEach(param -> {
                Type paramType = param.getType();
                if (isConcerned(paramType)) {
                    CSVar csParam = csManager.getCSVar(calleeCtx, param);
                    solver.addPFGEdge(new PointerFlowEdge(
                                    PARAMETER_PASSING, elems, csParam),
                            paramType);
                }
            });
        });
    }

    private static boolean isConcerned(Type type) {
        return type instanceof ClassType || type instanceof ArrayType;
    }

    @Override
    public void onNewPointsToSet(CSVar csVar, PointsToSet pts) {
        super.onNewPointsToSet(csVar, pts);
        reflectiveArgs.get(csVar).forEach(edge -> passReflectiveArgs(edge, pts));
    }

    MultiMap<Invoke, Object> getAllTargets() {
        return allTargets;
    }
}
