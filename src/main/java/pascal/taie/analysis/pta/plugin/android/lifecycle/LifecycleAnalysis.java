package pascal.taie.analysis.pta.plugin.android.lifecycle;

import pascal.taie.analysis.pta.plugin.CompositePlugin;
import pascal.taie.analysis.pta.plugin.android.AndroidContext;

public class LifecycleAnalysis extends CompositePlugin {

    public LifecycleAnalysis(AndroidContext androidContext) {
        LifecycleContext lifeCycleContext = new LifecycleContext(androidContext);
        addPlugin(
                new EntryPointHandler(lifeCycleContext),
                new CallbackHandler(lifeCycleContext),
                new LayoutModel(lifeCycleContext),
                new DynamicRegisterModel(lifeCycleContext)
        );
    }

}
