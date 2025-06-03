

package pascal.taie.analysis.pta.plugin.reflection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pascal.taie.World;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.plugin.CompositePlugin;
import pascal.taie.ir.proginfo.MethodRef;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.ir.stmt.Stmt;
import pascal.taie.language.classes.JMethod;
import pascal.taie.util.collection.MapEntry;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;

import java.util.Comparator;
import java.util.Set;

public class ReflectionAnalysis extends CompositePlugin {

    private static final Logger logger = LogManager.getLogger(ReflectionAnalysis.class);

    public static final int IMPRECISE_THRESHOLD = 5;

    private LogBasedModel logBasedModel;

    private InferenceModel inferenceModel;

    private ReflectiveActionModel reflectiveActionModel;

    /**
     * @return short name of reflection API in given {@link Invoke}.
     */
    public static String getShortName(Invoke invoke) {
        MethodRef ref = invoke.getMethodRef();
        String className = ref.getDeclaringClass().getSimpleName();
        String methodName = ref.getName();
        return className + "." + methodName;
    }

    @Override
    public void setSolver(Solver solver) {
        MetaObjHelper helper = new MetaObjHelper(solver);
        TypeMatcher typeMatcher = new TypeMatcher(solver.getTypeSystem());
        String logPath = solver.getOptions().getString("reflection-log");
        logBasedModel = new LogBasedModel(solver, helper, logPath);
        Set<Invoke> invokesWithLog = logBasedModel.getInvokesWithLog();
        String reflection = solver.getOptions().getString("reflection-inference");
        if ("string-constant".equals(reflection)) {
            inferenceModel = new StringBasedModel(solver, helper, invokesWithLog);
        } else if ("solar".equals(reflection)) {
            inferenceModel = new SolarModel(solver, helper, typeMatcher, invokesWithLog);
        } else if (reflection == null) {
            inferenceModel = InferenceModel.getDummy(solver);
        } else {
            throw new IllegalArgumentException("Illegal reflection option: " + reflection);
        }
        reflectiveActionModel = new ReflectiveActionModel(solver, helper,
                typeMatcher, invokesWithLog);

        addPlugin(logBasedModel,
                inferenceModel,
                reflectiveActionModel,
                new AnnotationModel(solver, helper),
                new OthersModel(solver, helper));
    }

    @Override
    public void onNewStmt(Stmt stmt, JMethod container) {
        if (World.get().getOptions().isAndroidMode()
                && stmt instanceof Invoke invoke
                && !invoke.isDynamic()
                && invoke.getContainer().isApplication()) {
            super.onNewStmt(stmt, container);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        reportImpreciseCalls();
    }

    /**
     * Report that may be resolved imprecisely.
     */
    private void reportImpreciseCalls() {
        MultiMap<Invoke, Object> allTargets = collectAllTargets();
        Set<Invoke> invokesWithLog = logBasedModel.getInvokesWithLog();
        var impreciseCalls = allTargets.keySet()
                .stream()
                .map(invoke -> new MapEntry<>(invoke, allTargets.get(invoke)))
                .filter(e -> !invokesWithLog.contains(e.getKey()))
                .filter(e -> e.getValue().size() > IMPRECISE_THRESHOLD)
                .toList();
        if (!impreciseCalls.isEmpty()) {
            logger.info("Imprecise reflective calls:");
            impreciseCalls.stream()
                    .sorted(Comparator.comparingInt(
                            (MapEntry<Invoke, Set<Object>> e) -> -e.getValue().size())
                            .thenComparing(MapEntry::getKey))
                    .forEach(e -> {
                        Invoke invoke = e.getKey();
                        String shortName = getShortName(invoke);
                        logger.info("[{}]{}, #targets: {}",
                                shortName, invoke, e.getValue().size());
                    });
        }
    }

    /**
     * Collects all reflective targets resolved by reflection analysis.
     */
    private MultiMap<Invoke, Object> collectAllTargets() {
        MultiMap<Invoke, Object> allTargets = Maps.newMultiMap();
        allTargets.putAll(logBasedModel.getForNameTargets());
        allTargets.putAll(inferenceModel.getForNameTargets());
        allTargets.putAll(reflectiveActionModel.getAllTargets());
        return allTargets;
    }
}
