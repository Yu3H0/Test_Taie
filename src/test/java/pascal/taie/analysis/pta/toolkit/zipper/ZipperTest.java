

package pascal.taie.analysis.pta.toolkit.zipper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.World;
import pascal.taie.analysis.Tests;
import pascal.taie.analysis.graph.flowgraph.FlowGraphDumper;
import pascal.taie.analysis.graph.flowgraph.ObjectFlowGraph;
import pascal.taie.analysis.pta.PointerAnalysis;
import pascal.taie.analysis.pta.PointerAnalysisResult;
import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.analysis.pta.toolkit.PointerAnalysisResultExImpl;
import pascal.taie.util.graph.DotDumper;

import java.io.File;

public class ZipperTest {

    private static final String CS = "contextsensitivity";

    private static final String BASIC = "basic";

    private static final String MISC = "misc";

    @Test
    void testOAG() {
        dumpOAG("TwoObject", "cs:2-obj");
    }

    private static void dumpOAG(String main, String... opts) {
        Tests.testPTA(false, CS, main, opts);
        PointerAnalysisResult pta = World.get().getResult(PointerAnalysis.ID);
        ObjectAllocationGraph oag = new ObjectAllocationGraph(
                new PointerAnalysisResultExImpl(pta, true));
        File output = new File(World.get().getOptions().getOutputDir(), main + "-oag.dot");
        new DotDumper<Obj>().dump(oag, output);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Cast",
            "StoreLoad",
            "Array",
            "CallParamRet",
            "Cycle",
    })
    void testOFG(String mainClass) {
        dumpOFG(mainClass);
    }

    private static void dumpOFG(String main) {
        Tests.testPTA(false, BASIC, main);
        PointerAnalysisResult pta = World.get().getResult(PointerAnalysis.ID);
        ObjectFlowGraph ofg = pta.getObjectFlowGraph();
        File output = new File(World.get().getOptions().getOutputDir(), main + "-ofg.dot");
        FlowGraphDumper.dump(ofg, output);
    }

    @Test
    void testPFGBuilder() {
        Tests.testPTA(false, MISC, "Zipper", "advanced:zipper");
    }
}
