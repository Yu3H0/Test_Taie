package pascal.taie.analysis.pta.plugin.android.lifecycle;

import pascal.taie.analysis.pta.plugin.android.AndroidHandler;
import pascal.taie.language.classes.Subsignature;

/**
 * Abstract class for android lifecycle handlers.
 */
public abstract class LifecycleHandler extends AndroidHandler {

    protected static final Subsignature ON_ATTACH = Subsignature.get("void onAttach(android.app.Activity)");

    protected LifecycleHandler(LifecycleContext context) {
        super(context);
    }

}
