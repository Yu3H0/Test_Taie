

package pascal.taie.analysis.pta.core.heap;

import pascal.taie.ir.exp.ReferenceLiteral;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.type.Type;

import java.util.Optional;

/**
 * Objects that represent constants.
 */
public class ConstantObj extends Obj {

    private final ReferenceLiteral value;

    ConstantObj(ReferenceLiteral value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return value.getType();
    }

    @Override
    public ReferenceLiteral getAllocation() {
        return value;
    }

    @Override
    public Optional<JMethod> getContainerMethod() {
        return Optional.empty();
    }

    @Override
    public Type getContainerType() {
        return getType();
    }

    @Override
    public String toString() {
        return String.format("ConstantObj{%s: %s}", getType(), value);
    }
}
