

package pascal.taie.analysis.pta.core.cs.element;

import pascal.taie.analysis.graph.flowgraph.FlowKind;
import pascal.taie.analysis.pta.core.solver.PointerFlowEdge;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.util.collection.ArraySet;
import pascal.taie.util.collection.HybridIndexableSet;
import pascal.taie.util.collection.Sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

abstract class AbstractPointer implements Pointer {

    private PointsToSet pointsToSet;

    private final int index;

    private final Set<Pointer> successors = new HybridIndexableSet<>(true);

    private final ArrayList<PointerFlowEdge> outEdges = new ArrayList<>(4);

    private Set<Predicate<CSObj>> filters = Set.of();

    protected AbstractPointer(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public PointsToSet getPointsToSet() {
        return pointsToSet;
    }

    @Override
    public void setPointsToSet(PointsToSet pointsToSet) {
        this.pointsToSet = pointsToSet;
    }

    @Override
    public void addFilter(Predicate<CSObj> filter) {
        if (filters.isEmpty()) {
            filters = Sets.newHybridSet();
        }
        filters.add(filter);
    }

    @Override
    public Set<Predicate<CSObj>> getFilters() {
        return filters;
    }

    @Override
    public Set<CSObj> getObjects() {
        PointsToSet pts = getPointsToSet();
        return pts == null ? Set.of() : pts.getObjects();
    }

    @Override
    public Stream<CSObj> objects() {
        return getObjects().stream();
    }

    @Override
    public PointerFlowEdge addEdge(PointerFlowEdge edge) {
        assert edge.source() == this;
        if (successors.add(edge.target())) {
            outEdges.add(edge);
            return edge;
        } else if (edge.kind() == FlowKind.OTHER) {
            for (PointerFlowEdge outEdge : outEdges) {
                if (outEdge.equals(edge)) {
                    return outEdge;
                }
            }
            outEdges.add(edge);
            return edge;
        }
        return null;
    }

    @Override
    public void removeEdgesIf(Predicate<PointerFlowEdge> filter) {
        outEdges.removeIf(filter);
    }

    @Override
    public Set<PointerFlowEdge> getOutEdges() {
        return Collections.unmodifiableSet(new ArraySet<>(outEdges, true));
    }

    @Override
    public int getOutDegree() {
        return outEdges.size();
    }
}
