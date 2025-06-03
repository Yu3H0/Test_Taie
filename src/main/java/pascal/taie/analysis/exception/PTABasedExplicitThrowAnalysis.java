

package pascal.taie.analysis.exception;

import pascal.taie.World;
import pascal.taie.analysis.pta.PointerAnalysis;
import pascal.taie.analysis.pta.PointerAnalysisResult;
import pascal.taie.analysis.pta.plugin.exception.ExceptionAnalysis;
import pascal.taie.analysis.pta.plugin.exception.PTAThrowResult;
import pascal.taie.ir.IR;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.ir.stmt.Throw;
import pascal.taie.language.type.ClassType;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Analyzes explicit exceptions based on pointer analysis.
 */
class PTABasedExplicitThrowAnalysis implements ExplicitThrowAnalysis {

    private final PTAThrowResult ptaThrowResult;

    PTABasedExplicitThrowAnalysis() {
        PointerAnalysisResult result = World.get().getResult(PointerAnalysis.ID);
        this.ptaThrowResult = result.getResult(ExceptionAnalysis.class.getName());
    }

    @Override
    public void analyze(IR ir, ThrowResult result) {
        ptaThrowResult.getResult(ir.getMethod())
                .ifPresent(ptaResult ->
                        ir.forEach(stmt -> {
                            Set<ClassType> exceptions = ptaResult
                                    .mayThrowExplicitly(stmt)
                                    .stream()
                                    .map(o -> (ClassType) o.getType())
                                    .collect(Collectors.toUnmodifiableSet());
                            if (!exceptions.isEmpty()) {
                                if (stmt instanceof Throw) {
                                    result.addExplicit((Throw) stmt, exceptions);
                                } else if (stmt instanceof Invoke) {
                                    result.addExplicit((Invoke) stmt, exceptions);
                                }
                            }
                        })
                );
    }
}
