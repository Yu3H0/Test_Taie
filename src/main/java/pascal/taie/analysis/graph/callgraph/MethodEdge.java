

package pascal.taie.analysis.graph.callgraph;

import pascal.taie.util.graph.Edge;

/**
 * Represents call edge between caller and callee.
 */
record MethodEdge<CallSite, Method>(
        Method caller, Method callee, CallSite callSite)
        implements Edge<Method> {

    @Override
    public Method source() {
        return caller;
    }

    @Override
    public Method target() {
        return callee;
    }
}
