

package pascal.taie.ir.exp;

import pascal.taie.language.type.ReferenceType;

/**
 * Representation of new expressions.
 */
public interface NewExp extends RValue {

    @Override
    ReferenceType getType();
}
