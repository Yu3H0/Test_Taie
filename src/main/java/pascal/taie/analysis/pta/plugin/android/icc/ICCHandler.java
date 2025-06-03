package pascal.taie.analysis.pta.plugin.android.icc;

import pascal.taie.analysis.pta.plugin.android.AndroidHandler;

/**
 * Abstract class for android icc-handlers.
 */
public abstract class ICCHandler extends AndroidHandler {

    protected final ICCContext handlerContext;

    protected ICCHandler(ICCContext context) {
        super(context);
        this.handlerContext = context;
    }

}
