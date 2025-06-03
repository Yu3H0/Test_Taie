

package pascal.taie.ir.exp;

import pascal.taie.language.type.Type;
import pascal.taie.util.collection.ArraySet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PhiExp implements RValue {

    private final Type type;

    private final List<Var> vars;

    public PhiExp(Type type, List<Var> vars) {
        this.type = type;
        this.vars = List.copyOf(vars);
    }

    public List<Var> getVars() {
        return vars;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Set<RValue> getUses() {
        return new ArraySet<>(vars);
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }


    @Override
    public String toString() {
        return String.format("phi(%s)", vars.stream()
                .map(Var::toString)
                .collect(Collectors.joining(", ")));
    }

}
