

package pascal.taie.analysis.graph.callgraph;

import pascal.taie.World;
import pascal.taie.analysis.pta.PointerAnalysis;
import pascal.taie.analysis.pta.PointerAnalysisResult;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JMethod;

/**
 * Builds call graph based on pointer analysis results.
 * This builder assumes that pointer analysis has finished,
 * and it merely returns the (context-insensitive) call graph
 * obtained from pointer analysis result.
 */
class PTABasedBuilder implements CGBuilder<Invoke, JMethod> {

    @Override
    public CallGraph<Invoke, JMethod> build() {
        PointerAnalysisResult result = World.get().getResult(PointerAnalysis.ID);
        return result.getCallGraph();
    }
}
