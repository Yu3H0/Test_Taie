

package pascal.taie.analysis.pta.core.cs.context;

/**
 * Representation of contexts in context-sensitive pointer analysis.
 * Each context can be seen as a list of zero or more context elements.
 */
public interface Context {

    /**
     * @return the length (i.e., the number of elements) of this context.
     */
    int getLength();

    /**
     * @return the i-th element of this context. Starts from 0.
     */
    Object getElementAt(int i);
}
