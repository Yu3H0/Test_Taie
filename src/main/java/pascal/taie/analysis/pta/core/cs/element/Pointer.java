

package pascal.taie.analysis.pta.core.cs.element;

import pascal.taie.analysis.graph.flowgraph.FlowKind;
import pascal.taie.analysis.pta.core.solver.PointerFlowEdge;
import pascal.taie.analysis.pta.pts.PointsToSet;
import pascal.taie.language.type.Type;
import pascal.taie.util.Indexable;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Represents all pointers (nodes) in context-sensitive
 * pointer analysis (pointer flow graph).
 */
public interface Pointer extends Indexable {

    /**
     * Retrieves the points-to set associated with this pointer.
     * <p>
     * This method may return {@code null}.
     * We recommend use {@link #getObjects()} and {@link #objects()}
     * for accessing the objects pointed by this pointer after
     * the pointer analysis finishes.
     *
     * @return the points-to set associated with this pointer.
     */
    @Nullable
    PointsToSet getPointsToSet();

    /**
     * Sets the associated points-to set of this pointer.
     */
    void setPointsToSet(PointsToSet pointsToSet);

    /**
     * Adds filter to filter out objects pointed to by this pointer.
     */
    void addFilter(Predicate<CSObj> filter);

    /**
     * @return all filters added to this pointer.
     */
    Set<Predicate<CSObj>> getFilters();

    /**
     * Safely retrieves context-sensitive objects pointed to by this pointer.
     *
     * @return an empty set if {@code pointer} has not been associated
     * a {@code PointsToSet}; otherwise, returns set of objects in the
     * {@code PointsToSet}.
     */
    Set<CSObj> getObjects();

    /**
     * Safely retrieves context-sensitive objects pointed to by this pointer.
     *
     * @return an empty stream if {@code pointer} has not been associated
     * a {@code PointsToSet}; otherwise, returns stream of objects in the
     * {@code PointsToSet}.
     */
    Stream<CSObj> objects();

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
    PointerFlowEdge addEdge(PointerFlowEdge edge);

    /**
     * Removes out edges of this pointer if they satisfy the filter.
     * <p>
     * <strong>Note:</strong> This method should not be called outside of
     * {@link pascal.taie.analysis.pta.plugin.Plugin#onPhaseFinish()},
     * otherwise it may break the monotonicity of pointer analysis.
     * </p>
     */
    void removeEdgesIf(Predicate<PointerFlowEdge> filter);

    /**
     * @return out edges of this pointer in pointer flow graph.
     */
    Set<PointerFlowEdge> getOutEdges();

    /**
     * @return out degree of this pointer in pointer flow graph.
     */
    int getOutDegree();

    /**
     * @return the type of this pointer
     */
    Type getType();
}
