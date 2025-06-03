

package pascal.taie.analysis.pta.core.heap;

import pascal.taie.World;
import pascal.taie.ir.IR;
import pascal.taie.ir.exp.StringLiteral;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Predicate for checking whether given string constants are
 * in application code.
 */
public class IsApplicationString implements Predicate<String> {

    private final Set<String> stringsInApp;

    public IsApplicationString() {
        stringsInApp = World.get().getClassHierarchy()
                .applicationClasses()
                .map(JClass::getDeclaredMethods)
                .flatMap(Collection::stream)
                .filter(Predicate.not(JMethod::isAbstract))
                .map(JMethod::getIR)
                .map(IR::getVars)
                .flatMap(Collection::stream)
                .filter(v -> v.isConst() && v.getConstValue() instanceof StringLiteral)
                .map(v -> ((StringLiteral) v.getConstValue()).getString())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean test(String s) {
        return stringsInApp.contains(s);
    }
}
