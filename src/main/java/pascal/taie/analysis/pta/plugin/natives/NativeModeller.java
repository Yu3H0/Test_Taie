

package pascal.taie.analysis.pta.plugin.natives;

import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.plugin.CompositePlugin;

/**
 * This class models some native calls by "inlining" their side effects
 * at the call sites to provide better precision for pointer analysis.
 */
public class NativeModeller extends CompositePlugin {

    @Override
    public void setSolver(Solver solver) {
        addPlugin(new ArrayModel(solver),
                new UnsafeModel(solver),
                new DoPriviledgedModel(solver));
    }
}
