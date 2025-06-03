

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.pta.core.cs.element.CSManager;
import pascal.taie.analysis.pta.core.solver.Solver;

/**
 * Abstract class for taint analysis handlers.
 */
abstract class Handler {

    protected final Solver solver;

    protected final CSManager csManager;

    protected final TaintManager manager;

    protected final boolean callSiteMode;

    protected Handler(HandlerContext context) {
        solver = context.solver();
        csManager = solver.getCSManager();
        manager = context.manager();
        callSiteMode = context.config().callSiteMode();
    }
}
