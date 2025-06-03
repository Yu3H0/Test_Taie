

package pascal.taie;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import pascal.taie.analysis.bugfinder.BugFinderTestSuite;
import pascal.taie.analysis.dataflow.DataflowTestSuite;
import pascal.taie.analysis.deadcode.DeadCodeTestFull;
import pascal.taie.analysis.defuse.DefUseTest;
import pascal.taie.analysis.graph.callgraph.cha.CHATestFull;
import pascal.taie.analysis.pta.PTATestSuite;
import pascal.taie.analysis.sideeffect.SideEffectTest;
import pascal.taie.android.droidbench.DroidBenchTestSuite;
import pascal.taie.config.OptionsTest;
import pascal.taie.frontend.cache.SerializationTest;
import pascal.taie.frontend.soot.SootFrontendTest;
import pascal.taie.language.DefaultMethodTest;
import pascal.taie.language.FieldTest;
import pascal.taie.language.HierarchyTest;
import pascal.taie.language.TypeTest;
import pascal.taie.language.classes.StringRepsTest;
import pascal.taie.language.generics.GSignaturesTest;
import pascal.taie.util.UtilTestSuite;

@Suite
@SelectClasses({
        // world
        SootFrontendTest.class,
        TypeTest.class,
        GSignaturesTest.class,
        HierarchyTest.class,
        DefaultMethodTest.class,
        FieldTest.class,
        SerializationTest.class,
        // analysis
        BugFinderTestSuite.class,
        DataflowTestSuite.class,
        DeadCodeTestFull.class,
        DefUseTest.class,
        CHATestFull.class,
        PTATestSuite.class,
        SideEffectTest.class,
        // util
        OptionsTest.class,
        UtilTestSuite.class,
        StringRepsTest.class,
        // android
        DroidBenchTestSuite.class,
})
public class TaieTestSuite {
}
