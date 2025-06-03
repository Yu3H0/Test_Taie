

package pascal.taie.frontend.soot;

import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.ir.IR;
import pascal.taie.ir.IRPrinter;
import pascal.taie.ir.exp.Var;
import pascal.taie.language.classes.JClass;

public class ConstVarTest {

    @Test
    void test() {
        String main = "ConstVar";
        Main.buildWorld("-pp", "-cp", "src/test/resources/world", "--input-classes", main);
        JClass jclass = World.get().getClassHierarchy().getClass(main);
        jclass.getDeclaredMethods().forEach(m -> {
            IR ir = m.getIR();
            IRPrinter.print(ir, System.out);
            ir.getVars()
                    .stream()
                    .filter(Var::isConst)
                    .forEach(v -> System.out.println(v + " -> " + v.getConstValue()));
        });
    }
}
