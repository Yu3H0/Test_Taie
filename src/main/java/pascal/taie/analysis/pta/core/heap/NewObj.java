

package pascal.taie.analysis.pta.core.heap;

import pascal.taie.ir.stmt.New;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.type.ReferenceType;
import pascal.taie.language.type.Type;

import java.util.Optional;

/**
 * Objects that are created by new statements.
 */
public class NewObj extends Obj {

    private final New allocSite;

    NewObj(New allocSite) {
        this.allocSite = allocSite;
    }

    @Override
    public ReferenceType getType() {
        return allocSite.getRValue().getType();
    }

    @Override
    public New getAllocation() {
        return allocSite;
    }

    @Override
    public Optional<JMethod> getContainerMethod() {
        return Optional.of(allocSite.getContainer());
    }

    @Override
    public Type getContainerType() {
        return allocSite.getContainer()
                .getDeclaringClass()
                .getType();
    }

    @Override
    public String toString() {
        return String.format("NewObj{%s[%d@L%d] %s}",
                allocSite.getContainer(), allocSite.getIndex(),
                allocSite.getLineNumber(), allocSite.getRValue());
    }
}
