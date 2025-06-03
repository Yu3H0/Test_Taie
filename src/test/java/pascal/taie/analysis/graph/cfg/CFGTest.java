

package pascal.taie.analysis.graph.cfg;

import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.analysis.exception.ThrowAnalysis;

public class CFGTest {

    @Test
    void testCFG() {
        test("CFG", "explicit");
    }

    @Test
    void testException() {
        test("Exceptions", "all");
    }

    private static void test(String main, String exception) {
        Main.main(
                "-pp", "-cp", "src/test/resources/controlflow", "--input-classes", main,
                "-a", ThrowAnalysis.ID + "=exception:" + exception,
                "-a", CFGBuilder.ID + "=exception:" + exception + ";dump:true"
        );
    }
}
