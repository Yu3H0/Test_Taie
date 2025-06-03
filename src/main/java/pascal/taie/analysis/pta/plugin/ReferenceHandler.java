

package pascal.taie.analysis.pta.plugin;

import pascal.taie.analysis.pta.core.cs.element.CSManager;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.analysis.pta.core.cs.element.CSVar;
import pascal.taie.analysis.pta.core.cs.element.StaticField;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.language.classes.JField;
import pascal.taie.language.classes.JMethod;

import static pascal.taie.analysis.graph.flowgraph.FlowKind.STATIC_STORE;
import static pascal.taie.language.classes.Signatures.REFERENCE_INIT;
import static pascal.taie.language.classes.Signatures.REFERENCE_PENDING;

/**
 * Models GC behavior that it assigns every reference to Reference.pending.
 * As a result, Reference.pending can point to every reference.
 * The ReferenceHandler takes care of enqueueing the references in a
 * reference queue. If we do not model this GC behavior, Reference.pending
 * points to nothing, and finalize() methods won't get invoked.
 * TODO: update it for Java 9+ (current model doesn't work for Java 9+).
 */
public class ReferenceHandler implements Plugin {

    private Solver solver;

    private CSManager csManager;

    /**
     * This variable of Reference.&lt;init&gt;.
     */
    private JMethod referenceInit;

    /**
     * The static field Reference.pending.
     * This field has been deprecated since Java 9.
     */
    private JField referencePending;

    @Override
    public void setSolver(Solver solver) {
        this.solver = solver;
        csManager = solver.getCSManager();
        referenceInit = solver.getHierarchy()
                .getJREMethod(REFERENCE_INIT);
        referencePending = solver.getHierarchy()
                .getJREField(REFERENCE_PENDING);
    }

    @Override
    public void onNewCSMethod(CSMethod csMethod) {
        if (csMethod.getMethod().equals(referenceInit)) {
            CSVar initThis = csManager.getCSVar(
                    csMethod.getContext(), referenceInit.getIR().getThis());
            StaticField pending = csManager.getStaticField(referencePending);
            solver.addPFGEdge(initThis, pending, STATIC_STORE);
        }
    }
}
