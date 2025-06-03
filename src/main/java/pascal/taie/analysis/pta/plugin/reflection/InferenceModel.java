

package pascal.taie.analysis.pta.plugin.reflection;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.plugin.util.AnalysisModelPlugin;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.ClassMember;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.classes.Reflections;
import pascal.taie.util.AnalysisException;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;
import pascal.taie.util.collection.Sets;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pascal.taie.analysis.pta.plugin.reflection.ReflectionAnalysis.IMPRECISE_THRESHOLD;

/**
 * Base class for reflection inference.
 * This class provides methods to handle class- and member-retrieving APIs for
 * the cases where the both the name and the receiver class object are known.
 * TODO: take ClassLoader.loadClass(...) into account.
 */
abstract class InferenceModel extends AnalysisModelPlugin {

    protected final MetaObjHelper helper;

    protected final Set<Invoke> invokesWithLog;

    private final MultiMap<Invoke, JClass> forNameTargets = Maps.newMultiMap();

    InferenceModel(Solver solver, MetaObjHelper helper, Set<Invoke> invokesWithLog) {
        super(solver);
        this.helper = helper;
        this.invokesWithLog = invokesWithLog;
    }

    static InferenceModel getDummy(Solver solver) {
        return new InferenceModel(solver, null, null) {};
    }

    protected void classForNameKnown(
            Context context, Invoke forName, @Nullable String className) {
        if (className != null) {
            JClass clazz = hierarchy.getClass(className);
            if (clazz != null && clazz.isApplication()) {
                forNameTargets.put(forName, clazz);
                if (forNameTargets.get(forName).size() > IMPRECISE_THRESHOLD) {
                    return;
                }
                solver.initializeClass(clazz);
                Var result = forName.getResult();
                if (result != null) {
                    Obj classObj = helper.getMetaObj(clazz);
                    solver.addVarPointsTo(context, result, classObj);
                }
            }
        }
    }

    MultiMap<Invoke, JClass> getForNameTargets() {
        return forNameTargets;
    }

    protected void classGetConstructorKnown(
            Context context, Invoke invoke, @Nullable JClass clazz) {
        if (clazz != null) {
            Var result = invoke.getResult();
            if (result != null) {
                Stream<JMethod> constructors = switch (invoke.getMethodRef().getName()) {
                    case "getConstructor" -> Reflections.getConstructors(clazz);
                    case "getDeclaredConstructor" -> Reflections.getDeclaredConstructors(clazz);
                    default -> throw new AnalysisException(
                            "Expected [getConstructor, getDeclaredConstructor], given " +
                                    invoke.getMethodRef());
                };
                constructors.map(helper::getMetaObj)
                        .forEach(ctorObj -> solver.addVarPointsTo(context, result, ctorObj));
            }
        }
    }

    protected void classGetMethodKnown(Context context, Invoke invoke,
                                       @Nullable JClass clazz, @Nullable String name) {
        if (clazz != null && name != null) {
            Var result = invoke.getResult();
            if (result != null) {
                Stream<JMethod> methods = switch (invoke.getMethodRef().getName()) {
                    case "getMethod" -> Reflections.getMethods(clazz, name);
                    case "getDeclaredMethod" -> Reflections.getDeclaredMethods(clazz, name);
                    default -> throw new AnalysisException(
                            "Expected [getMethod, getDeclaredMethod], given " +
                                    invoke.getMethodRef());
                };
                methods.map(helper::getMetaObj)
                        .forEach(mtdObj -> solver.addVarPointsTo(context, result, mtdObj));
            }
        }
    }
}
