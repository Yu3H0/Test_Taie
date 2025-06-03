

package pascal.taie.analysis.pta.plugin.assertion;

import pascal.taie.analysis.pta.PointerAnalysisResult;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.type.TypeSystem;

@FunctionalInterface
interface Checker {

    /**
     * Checks the assertion at {@code invoke}.
     */
    Result check(Invoke invoke, PointerAnalysisResult pta,
                 ClassHierarchy hierarchy, TypeSystem typeSystem);
}
