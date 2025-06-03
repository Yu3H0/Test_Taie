

package pascal.taie.analysis.pta.core.solver;

import pascal.taie.analysis.graph.flowgraph.FlowKind;
import pascal.taie.analysis.pta.core.cs.element.Pointer;
import pascal.taie.util.Hashes;
import pascal.taie.util.collection.Sets;
import pascal.taie.util.graph.Edge;

import java.util.Set;

public class PointerFlowEdge implements Edge<Pointer> {

    private final FlowKind kind;

    private final Pointer source;

    private final Pointer target;

    private final Set<Transfer> transfers = Sets.newHybridSet();

    public PointerFlowEdge(FlowKind kind, Pointer source, Pointer target) {
        this.kind = kind;
        this.source = source;
        this.target = target;
    }

    public FlowKind kind() {
        return kind;
    }

    public Pointer source() {
        return source;
    }

    public Pointer target() {
        return target;
    }

    /**
     * @return String representation of information for this edge.
     * By default, the information represents the {@link FlowKind},
     * and other subclasses of {@link PointerFlowEdge} may contain
     * additional content.
     */
    public String getInfo() {
        return kind.name();
    }

    public boolean addTransfer(Transfer transfer) {
        return transfers.add(transfer);
    }

    public Set<Transfer> getTransfers() {
        return transfers;
    }

    @Override
    public int hashCode() {
        return Hashes.hash(source, target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointerFlowEdge that = (PointerFlowEdge) o;
        return source.equals(that.source) && target.equals(that.target);
    }

    @Override
    public String toString() {
        return "[" + getInfo() + "]" + source + " -> " + target;
    }
}
