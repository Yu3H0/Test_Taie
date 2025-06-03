

package pascal.taie.analysis.dataflow.analysis.constprop;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;
import pascal.taie.analysis.dataflow.inter.InterConstantPropagation;

public class InterCPAliasTest {

    private static final String CLASS_PATH = "src/test/resources/dataflow/constprop/alias";

    void testInterCPAlias(String mainClass) {
        Tests.testMain(mainClass, CLASS_PATH, InterConstantPropagation.ID,
                "edge-refine:false;alias-aware:true",
                "-a", "pta=cs:2-obj;implicit-entries:false"
                //, "-a", "icfg=dump:true" // <-- uncomment this code if you want
                // to output ICFGs for the test cases
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Array",
        "ArrayInter2",
        "ArrayLoops",
        "InstanceField",
        "MultiStores",
        "Interprocedural2",
        "ObjSens",
        "StaticField",
        "StaticFieldMultiStores",
    })
    void test(String mainClass) {
        testInterCPAlias(mainClass);
    }

}
