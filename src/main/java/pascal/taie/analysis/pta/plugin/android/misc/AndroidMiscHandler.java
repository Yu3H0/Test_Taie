package pascal.taie.analysis.pta.plugin.android.misc;

import pascal.taie.analysis.pta.plugin.android.AndroidHandler;

/**
 * Abstract class for android other specific handlers.
 */
public abstract class AndroidMiscHandler extends AndroidHandler {

    protected final AndroidMiscContext handlerContext;

    protected AndroidMiscHandler(AndroidMiscContext context) {
        super(context);
        this.handlerContext = context;
    }

}
