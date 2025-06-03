

package pascal.taie.ir.exp;

import pascal.taie.ir.proginfo.MethodRef;
import pascal.taie.language.type.Type;
import pascal.taie.util.collection.ArraySet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Representation of method invocation expression.
 */
public abstract class InvokeExp implements RValue {

    /**
     * The method reference at the invocation.
     */
    protected final MethodRef methodRef;

    /**
     * The arguments of the invocation.
     */
    protected final List<Var> args;

    protected InvokeExp(MethodRef methodRef, List<Var> args) {
        this.methodRef = methodRef;
        this.args = List.copyOf(args);
    }

    @Override
    public Type getType() {
        return methodRef.getReturnType();
    }

    /**
     * @return the method reference at the invocation.
     */
    public MethodRef getMethodRef() {
        return methodRef;
    }

    /**
     * @return the number of the arguments of the invocation.
     */
    public int getArgCount() {
        return args.size();
    }

    /**
     * @return the i-th argument of the invocation.
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (index &lt; 0 || index &ge; getArgCount())
     */
    public Var getArg(int i) {
        return args.get(i);
    }

    /**
     * @return a list of arguments of the invocation.
     */
    public List<Var> getArgs() {
        return args;
    }

    public abstract String getInvokeString();

    public String getArgsString() {
        return "(" + args.stream()
                .map(Var::toString)
                .collect(Collectors.joining(", ")) + ")";
    }

    @Override
    public Set<RValue> getUses() {
        return new ArraySet<>(args);
    }
}
