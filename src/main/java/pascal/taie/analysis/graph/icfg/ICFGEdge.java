

package pascal.taie.analysis.graph.icfg;

import pascal.taie.util.graph.AbstractEdge;

/**
 * Abstract class for ICFG edges.
 *
 * @param <Node> type of ICFG nodes
 * @see NormalEdge
 * @see CallToReturnEdge
 * @see CallEdge
 * @see ReturnEdge
 */
public abstract class ICFGEdge<Node> extends AbstractEdge<Node> {

    ICFGEdge(Node source, Node target) {
        super(source, target);
    }
}
