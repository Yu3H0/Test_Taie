

package pascal.taie.ir;

import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.ir.stmt.Stmt;
import pascal.taie.language.classes.JMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class IRTest {

    @Test
    void testStmtIndexer() {
        Main.buildWorld("-pp", "-cp", "src/test/resources/world",
                "--input-classes", "AllInOne");
        World.get()
                .getClassHierarchy()
                .applicationClasses()
                .forEach(c -> {
                    for (JMethod m : c.getDeclaredMethods()) {
                        if (!m.isAbstract()) {
                            IR ir = m.getIR();
                            for (Stmt s : ir) {
                                assertEquals(s, ir.getObject(s.getIndex()));
                            }
                        }
                    }
                });
    }
}
