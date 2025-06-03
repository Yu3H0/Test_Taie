

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.graph.flowgraph.FlowEdge;
import pascal.taie.analysis.graph.flowgraph.Node;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;
import pascal.taie.util.collection.Sets;
import pascal.taie.util.collection.Views;
import pascal.taie.util.graph.Graph;

import java.util.Set;

class TaintFlowGraph implements Graph<Node> {

    private final Set<Node> sourceNodes;

    private final Set<Node> sinkNodes;

    private final Set<Node> nodes = Sets.newHybridSet();

    private final MultiMap<Node, FlowEdge> inEdges = Maps.newMultiMap();

    private final MultiMap<Node, FlowEdge> outEdges = Maps.newMultiMap();

    TaintFlowGraph(Set<Node> sourceNodes, Set<Node> sinkNodes) {
        this.sourceNodes = Set.copyOf(sourceNodes);
        nodes.addAll(sourceNodes);
        this.sinkNodes = Set.copyOf(sinkNodes);
        nodes.addAll(sinkNodes);
    }

    Set<Node> getSourceNodes() {
        return sourceNodes;
    }

    Set<Node> getSinkNodes() {
        return sinkNodes;
    }

    void addEdge(FlowEdge edge) {
        nodes.add(edge.source());
        nodes.add(edge.target());
        inEdges.put(edge.target(), edge);
        outEdges.put(edge.source(), edge);
    }

    @Override
    public Set<Node> getPredsOf(Node node) {
        return Views.toMappedSet(getInEdgesOf(node), FlowEdge::source);
    }

    @Override
    public Set<FlowEdge> getInEdgesOf(Node node) {
        return inEdges.get(node);
    }

    @Override
    public Set<Node> getSuccsOf(Node node) {
        return Views.toMappedSet(getOutEdgesOf(node), FlowEdge::target);
    }

    @Override
    public Set<FlowEdge> getOutEdgesOf(Node node) {
        return outEdges.get(node);
    }

    @Override
    public Set<Node> getNodes() {
        return nodes;
    }
}
