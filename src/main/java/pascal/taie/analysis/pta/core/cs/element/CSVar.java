

package pascal.taie.analysis.pta.core.cs.element;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.ir.exp.Var;
import pascal.taie.language.type.Type;

/**
 * Represents context-sensitive variables.
 */
public class CSVar extends AbstractPointer implements CSElement {

    private final Var var;

    private final Context context;

    CSVar(Var var, Context context, int index) {
        super(index);
        this.var = var;
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    /**
     * @return the variable (without context).
     */
    public Var getVar() {
        return var;
    }

    @Override
    public Type getType() {
        return var.getType();
    }

    @Override
    public String toString() {
        return context + ":" + var.getMethod() + "/" + var.getName();
    }
}
