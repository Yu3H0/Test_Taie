

package pascal.taie.analysis.pta.core.cs.element;

import pascal.taie.analysis.pta.core.cs.context.Context;

/**
 * Context-sensitive elements. Each element is associate with a context.
 */
public interface CSElement {

    /**
     * @return the context of the context-sensitive element.
     */
    Context getContext();
}
