

package pascal.taie.analysis.pta.core.cs.element;

import pascal.taie.analysis.graph.callgraph.Edge;
import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.util.collection.Sets;

import java.util.Collections;
import java.util.Set;

/**
 * Represents context-sensitive call sites.
 */
public class CSCallSite extends AbstractCSElement {

    private final Invoke callSite;

    /**
     * Context-sensitive method which contains this CS call site.
     */
    private final CSMethod container;

    /**
     * Call edges from this call site.
     */
    private final Set<Edge<CSCallSite, CSMethod>> edges = Sets.newHybridSet();

    CSCallSite(Invoke callSite, Context context, CSMethod container) {
        super(context);
        this.callSite = callSite;
        this.container = container;
    }

    /**
     * @return the call site (without context).
     */
    public Invoke getCallSite() {
        return callSite;
    }

    public CSMethod getContainer() {
        return container;
    }

    public boolean addEdge(Edge<CSCallSite, CSMethod> edge) {
        return edges.add(edge);
    }

    public Set<Edge<CSCallSite, CSMethod>> getEdges() {
        return Collections.unmodifiableSet(edges);
    }

    @Override
    public String toString() {
        return context + ":" + callSite;
    }
}
