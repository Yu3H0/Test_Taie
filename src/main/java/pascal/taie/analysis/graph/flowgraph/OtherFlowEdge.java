

package pascal.taie.analysis.graph.flowgraph;

import pascal.taie.util.Canonicalizer;

record OtherFlowEdge(String info, Node source, Node target)
        implements FlowEdge {

    private static final Canonicalizer<String> canonicalizer = new Canonicalizer<>();

    OtherFlowEdge(String info, Node source, Node target) {
        this.info = canonicalizer.get(info);
        this.source = source;
        this.target = target;
    }

    @Override
    public FlowKind kind() {
        return FlowKind.OTHER;
    }
}
