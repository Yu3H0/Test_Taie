

package pascal.taie.analysis.pta.plugin.util;

import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.plugin.Plugin;

import java.lang.reflect.Method;

/**
 * Provides common functionalities for implementing the plugins which model APIs.
 *
 * @see InvokeHandler
 */
abstract class ModelPlugin extends SolverHolder implements Plugin {

    protected ModelPlugin(Solver solver) {
        super(solver);
    }

    protected void registerHandlers() {
        Class<?> clazz = getClass();
        for (Method method : clazz.getMethods()) {
            InvokeHandler[] invokeHandlers = method.getAnnotationsByType(InvokeHandler.class);
            if (invokeHandlers != null) {
                for (InvokeHandler invokeHandler : invokeHandlers) {
                    registerHandler(invokeHandler, method);
                }
            }
        }
    }

    protected abstract void registerHandler(InvokeHandler invokeHandler, Method handler);
}
