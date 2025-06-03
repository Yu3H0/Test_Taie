package pascal.taie.analysis.pta.plugin.android;

import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.plugin.CompositePlugin;
import pascal.taie.analysis.pta.plugin.android.icc.ICCAnalysis;
import pascal.taie.analysis.pta.plugin.android.lifecycle.LifecycleAnalysis;
import pascal.taie.analysis.pta.plugin.android.misc.AndroidMiscAnalysis;

public class AndroidAnalysis extends CompositePlugin {

    @Override
    public void setSolver(Solver solver) {
        AndroidContext androidContext = new AndroidContext(solver);
        addPlugin(
                new LifecycleAnalysis(androidContext),
                new ICCAnalysis(androidContext),
                new AndroidMiscAnalysis(androidContext)
        );
    }

}
