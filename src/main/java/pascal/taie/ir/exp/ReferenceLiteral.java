

package pascal.taie.ir.exp;

import pascal.taie.language.type.ReferenceType;

/**
 * Literal of reference type.
 */
public interface ReferenceLiteral extends Literal {

    @Override
    ReferenceType getType();
}
