

package pascal.taie.analysis.bugfinder.nullpointer;

import pascal.taie.analysis.graph.cfg.CFGEdge;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.If;
import pascal.taie.language.type.ReferenceType;

import javax.annotation.CheckForNull;

class IsNullConditionDecision {

    private final If conditionStmt;

    private final Var varTested;

    private final IsNullValue ifTrueDecision;

    private final IsNullValue ifFalseDecision;

    public IsNullConditionDecision(
            If stmt, Var varTested,
            IsNullValue ifTrueDecision, IsNullValue ifFalseDecision) {
        assert varTested.getType() instanceof ReferenceType;
        assert !(ifTrueDecision == null && ifFalseDecision == null);
        this.conditionStmt = stmt;
        this.varTested = varTested;
        this.ifTrueDecision = ifTrueDecision;
        this.ifFalseDecision = ifFalseDecision;
    }

    public If getConditionStmt() {
        return conditionStmt;
    }

    public Var getVarTested() {
        return varTested;
    }

    public boolean isEdgeFeasible(CFGEdge.Kind edgeKind) {
        return getDecision(edgeKind) != null;
    }

    @CheckForNull
    public IsNullValue getDecision(CFGEdge.Kind edgeKind) {
        return switch (edgeKind) {
            case IF_TRUE -> ifTrueDecision;
            case IF_FALSE -> ifFalseDecision;
            default -> throw new UnsupportedOperationException("Incorrect edge kind: " + edgeKind);
        };
    }
}
