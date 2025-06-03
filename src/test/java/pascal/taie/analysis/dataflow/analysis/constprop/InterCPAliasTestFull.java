

package pascal.taie.analysis.dataflow.analysis.constprop;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InterCPAliasTestFull extends InterCPAliasTest {

    @ParameterizedTest
    @ValueSource(strings = {
            // Tests instance field
            "MultiLoads",
            "MultiObjs",
            "Interprocedural",
            "InheritedField",
            "FieldCorner",
            // Tests array
            "ArrayField",
            "ArrayInter",
            "ArrayCorner",
            // Other tests
            "Reference",
            "ObjSens2",
            "ArrayInField",
            "MaxPQ",
    })
    void testFull(String mainClass) {
        testInterCPAlias(mainClass);
    }

}
