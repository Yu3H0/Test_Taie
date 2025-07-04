

package pascal.taie.analysis.dataflow.analysis;

import pascal.taie.analysis.dataflow.fact.SetFact;
import pascal.taie.analysis.graph.cfg.CFG;
import pascal.taie.analysis.graph.cfg.CFGNodeIndexer;
import pascal.taie.config.AnalysisConfig;
import pascal.taie.ir.IR;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.Stmt;
import pascal.taie.util.Indexer;
import pascal.taie.util.collection.IndexMap;
import pascal.taie.util.collection.IndexerBitSet;

import java.util.Map;

public class ReachingDefinition extends AnalysisDriver<Stmt, SetFact<Stmt>> {

    public static final String ID = "reach-def";

    public ReachingDefinition(AnalysisConfig config) {
        super(config);
    }

    @Override
    protected Analysis makeAnalysis(CFG<Stmt> cfg) {
        return new Analysis(cfg);
    }

    private static class Analysis extends AbstractDataflowAnalysis<Stmt, SetFact<Stmt>> {

        /**
         * Indexer for stmts (nodes) in the CFG.
         */
        private final Indexer<Stmt> stmtIndexer;

        /**
         * Maps a variable to all statements that define it.
         * This information can accelerate kill operation of live variable analysis.
         */
        private final Map<Var, SetFact<Stmt>> defs;

        private static final SetFact<Stmt> EMPTY_DEFS = new SetFact<>();

        private Analysis(CFG<Stmt> cfg) {
            super(cfg);
            stmtIndexer = new CFGNodeIndexer<>(cfg);
            defs = computeDefs(cfg.getIR());
        }

        /**
         * Pre-computes all definitions of all variables in given ir.
         */
        private Map<Var, SetFact<Stmt>> computeDefs(IR ir) {
            Map<Var, SetFact<Stmt>> defs = new IndexMap<>(
                    ir.getVarIndexer(), ir.getVars().size());
            for (Stmt stmt : ir) {
                stmt.getDef().ifPresent(def -> {
                    if (def instanceof Var defVar) {
                        defs.computeIfAbsent(defVar, __ -> newInitialFact())
                                .add(stmt);
                    }
                });
            }
            return defs;
        }

        @Override
        public boolean isForward() {
            return true;
        }

        @Override
        public SetFact<Stmt> newBoundaryFact() {
            return newInitialFact();
        }

        @Override
        public SetFact<Stmt> newInitialFact() {
            return new SetFact<>(new IndexerBitSet<>(stmtIndexer, false));
        }

        @Override
        public void meetInto(SetFact<Stmt> fact, SetFact<Stmt> target) {
            target.union(fact);
        }

        @Override
        public boolean transferNode(Stmt stmt, SetFact<Stmt> in, SetFact<Stmt> out) {
            SetFact<Stmt> oldOut = out.copy();
            out.set(in);
            stmt.getDef().ifPresent(def -> {
                if (def instanceof Var defVar) {
                    // kill previous definitions of defVar
                    out.removeAll(defs.getOrDefault(defVar, EMPTY_DEFS));
                    // generate definition of defVar
                    out.add(stmt);
                }
            });
            return !out.equals(oldOut);
        }
    }
}
