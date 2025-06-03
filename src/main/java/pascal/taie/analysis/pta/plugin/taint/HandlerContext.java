

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.pta.core.solver.Solver;

/**
 * Contains information used by taint analysis handlers.
 */
record HandlerContext(Solver solver,
                      TaintManager manager,
                      TaintConfig config) {
}
