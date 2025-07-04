

package pascal.taie.analysis.pta.core.heap;

import pascal.taie.language.classes.JMethod;
import pascal.taie.language.type.Type;
import pascal.taie.util.Indexable;

import java.util.Optional;

/**
 * Represents of abstract objects in pointer analysis.
 *
 * @see HeapModel
 */
public abstract class Obj implements Indexable {

    private int index = -1;

    void setIndex(int index) {
        if (this.index != -1) {
            throw new IllegalStateException("index already set");
        }
        if (index < 0) {
            throw new IllegalArgumentException(
                    "index must be 0 or positive number, given: " + index);
        }
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    /**
     * An object is functional means that it can hold fields (or array indexes).
     *
     * @return {@code true} if this is a function {@link Obj}.
     */
    public boolean isFunctional() {
        return true;
    }

    /**
     * @return the type of the object.
     */
    public abstract Type getType();

    /**
     * @return the allocation of the object.
     */
    public abstract Object getAllocation();

    /**
     * @return the method containing the allocation site of this object.
     * For some special objects, e.g., string constants, which are not
     * allocated in any method, this API returns an empty Optional.
     */
    public abstract Optional<JMethod> getContainerMethod();

    /**
     * This method is useful for type sensitivity.
     *
     * @return the type containing the allocation site of this object.
     * For special objects, the return values of this method are also special.
     */
    public abstract Type getContainerType();
}
