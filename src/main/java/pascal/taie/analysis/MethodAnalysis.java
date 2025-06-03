

package pascal.taie.analysis;

import pascal.taie.config.AnalysisConfig;
import pascal.taie.ir.IR;

/**
 * Abstract base class for all method analyses, or say, intra-procedural analyses.
 *
 * @param <R> result type
 */
public abstract class MethodAnalysis<R> extends Analysis {

    // private boolean isParallel;

    protected MethodAnalysis(AnalysisConfig config) {
        super(config);
    }

    /**
     * Runs this analysis for the given {@link IR}.
     * The result will be stored in {@link IR}. If the result is not used
     * by following analyses, then this method should return {@code null}.
     *
     * @param ir IR of the method to be analyzed
     * @return the analysis result for given ir.
     */
    public abstract R analyze(IR ir);
}
