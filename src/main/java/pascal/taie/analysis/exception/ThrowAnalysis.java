

package pascal.taie.analysis.exception;

import pascal.taie.analysis.MethodAnalysis;
import pascal.taie.config.AnalysisConfig;
import pascal.taie.ir.IR;

public class ThrowAnalysis extends MethodAnalysis<ThrowResult> {

    public static final String ID = "throw";

    /**
     * If this field is null, then this analysis ignores implicit exceptions.
     */
    private final ImplicitThrowAnalysis implicitThrowAnalysis;

    private final ExplicitThrowAnalysis explicitThrowAnalysis;

    public ThrowAnalysis(AnalysisConfig config) {
        super(config);
        if ("all".equals(getOptions().getString("exception"))) {
            implicitThrowAnalysis = new ImplicitThrowAnalysis();
        } else {
            implicitThrowAnalysis = null;
        }
        if ("pta".equals(getOptions().getString("algorithm"))) {
            explicitThrowAnalysis = new PTABasedExplicitThrowAnalysis();
        } else {
            explicitThrowAnalysis = new IntraExplicitThrowAnalysis();
        }
    }

    @Override
    public ThrowResult analyze(IR ir) {
        ThrowResult result = new ThrowResult(
                ir, implicitThrowAnalysis);
        explicitThrowAnalysis.analyze(ir, result);
        return result;
    }
}
