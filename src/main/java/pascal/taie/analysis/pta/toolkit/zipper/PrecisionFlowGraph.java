

package pascal.taie.analysis.pta.toolkit.zipper;

import pascal.taie.analysis.graph.flowgraph.FlowEdge;
import pascal.taie.analysis.graph.flowgraph.Node;
import pascal.taie.analysis.graph.flowgraph.ObjectFlowGraph;
import pascal.taie.analysis.graph.flowgraph.VarNode;
import pascal.taie.language.type.Type;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;
import pascal.taie.util.collection.Views;
import pascal.taie.util.graph.Graph;

import java.util.Set;
import java.util.stream.Collectors;

class PrecisionFlowGraph implements Graph<Node> {

    private final Type type;

    private final ObjectFlowGraph ofg;

    private final Set<Node> nodes;

    private final Set<VarNode> outNodes;

    private final MultiMap<Node, FlowEdge> inWUEdges;

    private final MultiMap<Node, FlowEdge> outWUEdges;

    PrecisionFlowGraph(Type type, ObjectFlowGraph ofg,
                       Set<Node> nodes, Set<VarNode> outNodes,
                       MultiMap<Node, FlowEdge> outWUEdges) {
        this.type = type;
        this.ofg = ofg;
        this.nodes = nodes;
        this.outNodes = outNodes
                .stream()
                .filter(nodes::contains)
                .collect(Collectors.toUnmodifiableSet());
        this.outWUEdges = outWUEdges;
        this.inWUEdges = Maps.newMultiMap();
        outWUEdges.values()
                .forEach(edge -> inWUEdges.put(edge.target(), edge));
    }

    Type getType() {
        return type;
    }

    Set<VarNode> getOutNodes() {
        return outNodes;
    }

    @Override
    public boolean hasEdge(Node source, Node target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Node> getPredsOf(Node node) {
        return Views.toMappedSet(getInEdgesOf(node), FlowEdge::source);
    }

    @Override
    public Set<FlowEdge> getInEdgesOf(Node node) {
        Set<FlowEdge> inEdges = ofg.getInEdgesOf(node)
                .stream()
                .filter(e -> nodes.contains(e.source()))
                .collect(Collectors.toSet());
        inEdges.addAll(inWUEdges.get(node));
        return inEdges;
    }

    @Override
    public Set<Node> getSuccsOf(Node node) {
        return Views.toMappedSet(getOutEdgesOf(node), FlowEdge::target);
    }

    @Override
    public Set<FlowEdge> getOutEdgesOf(Node node) {
        Set<FlowEdge> outEdges = ofg.getOutEdgesOf(node)
                .stream()
                .filter(e -> nodes.contains(e.target()))
                .collect(Collectors.toSet());
        outEdges.addAll(outWUEdges.get(node));
        return outEdges;
    }

    @Override
    public Set<Node> getNodes() {
        return nodes;
    }
}
