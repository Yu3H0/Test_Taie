

package pascal.taie.analysis.graph.icfg;

import pascal.taie.analysis.graph.cfg.CFGEdge;

/**
 * The edge connecting a call site and its return site.
 *
 * @param <Node> type of nodes
 */
public class CallToReturnEdge<Node> extends ICFGEdge<Node> {

    public CallToReturnEdge(CFGEdge<Node> edge) {
        super(edge.source(), edge.target());
    }
}
