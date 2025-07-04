

package pascal.taie.analysis.pta.core.cs.element;

import pascal.taie.language.type.ArrayType;
import pascal.taie.language.type.Type;

/**
 * Represents array index pointers.
 */
public class ArrayIndex extends AbstractPointer {

    private final CSObj array;

    ArrayIndex(CSObj array, int index) {
        super(index);
        this.array = array;
    }

    /**
     * @return the array object.
     */
    public CSObj getArray() {
        return array;
    }

    @Override
    public Type getType() {
        return ((ArrayType) array.getObject().getType())
                .elementType();
    }

    @Override
    public String toString() {
        return array + "[*]";
    }
}
