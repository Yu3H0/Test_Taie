

package pascal.taie.analysis.pta.core.solver;

import pascal.taie.analysis.graph.flowgraph.FlowKind;
import pascal.taie.analysis.pta.core.cs.element.CSManager;
import pascal.taie.analysis.pta.core.cs.element.Pointer;
import pascal.taie.util.collection.Views;
import pascal.taie.util.graph.Edge;
import pascal.taie.util.graph.Graph;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents pointer flow graph in context-sensitive pointer analysis.
 */
public class PointerFlowGraph implements Graph<Pointer> {

    private final CSManager csManager;

    PointerFlowGraph(CSManager csManager) {
        this.csManager = csManager;
    }

    /**
     * Adds a pointer flow edge and returns the edge in the PFG.
     * If the edge to add already exists, then
     * <ul>
     *     <li>if the edge is of {@link FlowKind#OTHER},
     *     returns the existing edge;
     *     <li>otherwise, returns {@code null}, meaning that the edge
     *     does not need to be processed again.
     * </ul>
     */
    public PointerFlowEdge addEdge(PointerFlowEdge edge) {
        return edge.source().addEdge(edge);
    }

    @Override
    public Set<? extends Edge<Pointer>> getInEdgesOf(Pointer node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<PointerFlowEdge> getOutEdgesOf(Pointer pointer) {
        return pointer.getOutEdges();
    }

    public Stream<Pointer> pointers() {
        return csManager.pointers();
    }

    @Override
    public Set<Pointer> getPredsOf(Pointer node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Pointer> getSuccsOf(Pointer node) {
        return Views.toMappedSet(node.getOutEdges(),
                PointerFlowEdge::target);
    }

    @Override
    public Set<Pointer> getNodes() {
        return pointers().collect(Collectors.toUnmodifiableSet());
    }
}
