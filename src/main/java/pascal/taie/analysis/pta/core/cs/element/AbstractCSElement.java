

package pascal.taie.analysis.pta.core.cs.element;

import pascal.taie.analysis.pta.core.cs.context.Context;

public abstract class AbstractCSElement implements CSElement {

    protected final Context context;

    AbstractCSElement(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }
}
