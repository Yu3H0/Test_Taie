

package pascal.taie.analysis.pta.plugin.util;

import pascal.taie.analysis.pta.core.cs.element.CSVar;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.Invoke;

/**
 * Model for special APIs.
 *
 * @deprecated Use {@link AnalysisModelPlugin} instead.
 */
@Deprecated
public interface Model {

    void handleNewInvoke(Invoke invoke);

    boolean isRelevantVar(Var var);

    void handleNewPointsToSet(CSVar csVar, PointsToSet pts);
}
