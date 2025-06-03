

package pascal.taie.analysis.graph.callgraph;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pascal.taie.World;
import pascal.taie.analysis.ProgramAnalysis;
import pascal.taie.config.AnalysisConfig;
import pascal.taie.config.AnalysisOptions;
import pascal.taie.config.ConfigException;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JMethod;

import java.io.File;

public class CallGraphBuilder extends ProgramAnalysis<CallGraph<Invoke, JMethod>> {

    public static final String ID = "cg";

    private static final Logger logger = LogManager.getLogger(CallGraphBuilder.class);

    private static final String CALL_GRAPH_FILE = "call-graph.dot";

    private static final String REACHABLE_METHODS_FILE = "reachable-methods.txt";

    private static final String CALL_EDGES_FILE = "call-edges.txt";

    private final String algorithm;

    public CallGraphBuilder(AnalysisConfig config) {
        super(config);
        algorithm = config.getOptions().getString("algorithm");
    }

    @Override
    public CallGraph<Invoke, JMethod> analyze() {
        CGBuilder<Invoke, JMethod> builder = switch (algorithm) {
            case "pta" -> new PTABasedBuilder();
            case "cha" -> new CHABuilder();
            default -> throw new ConfigException(
                    "Unknown call graph building algorithm: " + algorithm);
        };
        CallGraph<Invoke, JMethod> callGraph = builder.build();
        logStatistics(callGraph);
        processOptions(callGraph, getOptions());
        return callGraph;
    }

    private static void logStatistics(CallGraph<Invoke, JMethod> callGraph) {
        logger.info("Call graph has {} reachable methods and {} edges",
                callGraph.getNumberOfMethods(),
                callGraph.getNumberOfEdges());
    }

    private static void processOptions(CallGraph<Invoke, JMethod> callGraph,
                                       AnalysisOptions options) {
        File outputDir = World.get().getOptions().getOutputDir();
        if (options.getBoolean("dump")) {
            CallGraphs.dumpCallGraph(callGraph,
                    new File(outputDir, CALL_GRAPH_FILE));
        }
        if (options.getBoolean("dump-methods")) {
            CallGraphs.dumpMethods(callGraph,
                    new File(outputDir, REACHABLE_METHODS_FILE));
        }
        if (options.getBoolean("dump-call-edges")) {
            CallGraphs.dumpCallEdges(callGraph,
                    new File(outputDir, CALL_EDGES_FILE));
        }
    }
}
