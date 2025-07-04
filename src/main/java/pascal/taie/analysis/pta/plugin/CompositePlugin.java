

package pascal.taie.analysis.pta.plugin;

import pascal.taie.analysis.graph.callgraph.Edge;
import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.core.cs.element.CSVar;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.ir.stmt.Stmt;
import pascal.taie.language.classes.JMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Composite plugin which allows multiple independent plugins
 * to be used together.
 */
public class CompositePlugin implements Plugin {

    private final List<Plugin> allPlugins = new ArrayList<>();

    // Use separate lists to store plugins that overwrite
    // frequently-invoked methods.

    private final List<Plugin> onNewPointsToSetPlugins = new ArrayList<>();

    private final List<Plugin> onNewCallEdgePlugins = new ArrayList<>();

    private final List<Plugin> onNewMethodPlugins = new ArrayList<>();

    private final List<Plugin> onNewStmtPlugins = new ArrayList<>();

    private final List<Plugin> onNewCSMethodPlugins = new ArrayList<>();

    private final List<Plugin> onUnresolvedCallPlugins = new ArrayList<>();

    public void addPlugin(Plugin... plugins) {
        for (Plugin plugin : plugins) {
            allPlugins.add(plugin);
            addPlugin(plugin, onNewPointsToSetPlugins,
                    "onNewPointsToSet", CSVar.class, PointsToSet.class);
            addPlugin(plugin, onNewCallEdgePlugins, "onNewCallEdge", Edge.class);
            addPlugin(plugin, onNewMethodPlugins, "onNewMethod", JMethod.class);
            addPlugin(plugin, onNewStmtPlugins, "onNewStmt", Stmt.class, JMethod.class);
            addPlugin(plugin, onNewCSMethodPlugins, "onNewCSMethod", CSMethod.class);
            addPlugin(plugin, onUnresolvedCallPlugins,
                    "onUnresolvedCall", CSObj.class, Context.class, Invoke.class);
        }
    }

    private void addPlugin(Plugin plugin, List<Plugin> plugins,
                           String name, Class<?>... parameterTypes) {
        try {
            Method method = plugin.getClass().getMethod(name, parameterTypes);
            if (!method.getDeclaringClass().equals(Plugin.class)) {
                // the plugin does overwrite the specific method
                plugins.add(plugin);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Can't find method '" + name +
                    "' in " + plugin.getClass(), e);
        }
    }

    public void clearPlugins() {
        Stream.of(allPlugins,
                onNewPointsToSetPlugins, onNewCallEdgePlugins, onNewMethodPlugins,
                onNewStmtPlugins, onNewCSMethodPlugins, onUnresolvedCallPlugins
        ).forEach(List::clear);
    }

    @Override
    public void setSolver(Solver solver) {
        allPlugins.forEach(p -> p.setSolver(solver));
    }

    @Override
    public void onStart() {
        allPlugins.forEach(Plugin::onStart);
    }

    @Override
    public void onPhaseFinish() {
        allPlugins.forEach(Plugin::onPhaseFinish);
    }

    @Override
    public void onFinish() {
        allPlugins.forEach(Plugin::onFinish);
    }

    @Override
    public void onNewPointsToSet(CSVar csVar, PointsToSet pts) {
        onNewPointsToSetPlugins.forEach(p -> p.onNewPointsToSet(csVar, pts));
    }

    @Override
    public void onNewCallEdge(Edge<CSCallSite, CSMethod> edge) {
        onNewCallEdgePlugins.forEach(p -> p.onNewCallEdge(edge));
    }

    @Override
    public void onNewMethod(JMethod method) {
        onNewMethodPlugins.forEach(p -> p.onNewMethod(method));
    }

    @Override
    public void onNewStmt(Stmt stmt, JMethod container) {
        onNewStmtPlugins.forEach(p -> p.onNewStmt(stmt, container));
    }

    @Override
    public void onNewCSMethod(CSMethod csMethod) {
        onNewCSMethodPlugins.forEach(p -> p.onNewCSMethod(csMethod));
    }

    @Override
    public void onUnresolvedCall(CSObj recv, Context context, Invoke invoke) {
        onUnresolvedCallPlugins.forEach(p -> p.onUnresolvedCall(recv, context, invoke));
    }
}
