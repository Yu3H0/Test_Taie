

package pascal.taie.analysis.pta.plugin;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.analysis.pta.core.heap.Descriptor;
import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.ir.exp.NullLiteral;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.AssignLiteral;
import pascal.taie.ir.stmt.Stmt;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.type.NullType;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;

/**
 * Handles {@link AssignLiteral} var = null.
 */
public class NullHandler implements Plugin {

    private static final Descriptor NULL_DESC = () -> "NullObj";

    private final MultiMap<JMethod, Var> nullVars = Maps.newMultiMap();

    private Solver solver;

    private Obj nullObj;

    @Override
    public void setSolver(Solver solver) {
        this.solver = solver;
        nullObj = solver.getHeapModel().getMockObj(
                NULL_DESC, NullLiteral.get(), NullType.NULL, false);
    }

    @Override
    public void onNewStmt(Stmt stmt, JMethod container) {
        if (stmt instanceof AssignLiteral assign &&
                assign.getRValue() instanceof NullLiteral) {
            nullVars.put(container, assign.getLValue());
        }
    }

    @Override
    public void onNewCSMethod(CSMethod csMethod) {
        Context ctx = csMethod.getContext();
        nullVars.get(csMethod.getMethod()).forEach(var ->
                solver.addVarPointsTo(ctx, var, nullObj));
    }
}
