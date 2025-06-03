

package pascal.taie.analysis.graph.callgraph;

/**
 * Base class for call edges of {@link CallKind#OTHER}.
 * Implementation of {@link CallKind#OTHER} call edges should inherit this class.
 *
 * @param <CallSite> type of call sites
 * @param <Method>   type of methods
 */
public abstract class OtherEdge<CallSite, Method>
        extends Edge<CallSite, Method> {

    protected OtherEdge(CallSite callSite, Method callee) {
        super(CallKind.OTHER, callSite, callee);
    }

    /**
     * @return String representation of information for this edge.
     * It contains simple name of the edge class to distinguish
     * from other classes of {@link CallKind#OTHER} call edges.
     */
    @Override
    public String getInfo() {
        return getKind().name() + "." + getClass().getSimpleName();
    }
}
