

package pascal.taie.analysis.sideeffect;

import pascal.taie.World;
import pascal.taie.analysis.ProgramAnalysis;
import pascal.taie.analysis.pta.PointerAnalysis;
import pascal.taie.analysis.pta.PointerAnalysisResult;
import pascal.taie.config.AnalysisConfig;

public class SideEffectAnalysis extends ProgramAnalysis<SideEffect> {

    public static final String ID = "side-effect";

    /**
     * Whether the analysis only tracks the modifications on the objects
     * created in application code.
     */
    private final boolean onlyApp;

    public SideEffectAnalysis(AnalysisConfig config) {
        super(config);
        onlyApp = getOptions().getBoolean("only-app");
    }

    @Override
    public SideEffect analyze() {
        PointerAnalysisResult pta = World.get().getResult(PointerAnalysis.ID);
        return new TopologicalSolver(onlyApp).solve(pta);
    }
}
