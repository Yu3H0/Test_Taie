

package pascal.taie.analysis.exception;

import pascal.taie.ir.IR;

interface ExplicitThrowAnalysis {

    /**
     * Analyzes explicit exceptions (of Throw and Invoke) in given ir
     * and store result.
     */
    void analyze(IR ir, ThrowResult result);
}
