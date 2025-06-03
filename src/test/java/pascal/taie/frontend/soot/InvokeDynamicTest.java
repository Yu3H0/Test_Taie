

package pascal.taie.frontend.soot;

import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.ir.IR;
import pascal.taie.ir.IRPrinter;
import pascal.taie.ir.exp.InvokeDynamic;
import pascal.taie.ir.proginfo.MethodRef;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvokeDynamicTest {

    private static final String CP = "src/test/resources/pta/invokedynamic";

    private static final boolean isPrintIR = true;

    @Test
    void testFunction() {
        final String main = "Function";
        Main.buildWorld("-pp", "-cp", CP, "-m", main);
        JClass mainClass = World.get().getClassHierarchy().getClass(main);
        JMethod mainMethod = mainClass.getDeclaredMethod("main");
        printIR(mainMethod.getIR());
        extractInvokeDynamics(mainMethod.getIR()).forEach(
                indy -> assertEquals("apply", indy.getMethodName()));
    }

    @Test
    void testInterface() {
        final String main = "Interface";
        Main.buildWorld("-pp", "-cp", CP, "-m", main);
        JClass mainClass = World.get().getClassHierarchy().getClass(main);
        JMethod mainMethod = mainClass.getDeclaredMethod("main");
        printIR(mainMethod.getIR());
        extractInvokeDynamics(mainMethod.getIR()).forEach(indy -> {
            if (indy.getMethodName().equals("test")) {
                assertEquals("java.util.function.Predicate",
                        indy.getType().getName());
            }
            if (indy.getMethodName().equals("accept")) {
                assertEquals("java.util.function.Consumer",
                        indy.getType().getName());
            }
        });
    }

    @Test
    void testMultiStatement() {
        final String main = "MultiStatement";
        Main.buildWorld("-pp", "-cp", CP, "-m", main);
        JClass mainClass = World.get().getClassHierarchy().getClass(main);
        JMethod mainMethod = mainClass.getDeclaredMethod("main");
        printIR(mainMethod.getIR());
        extractInvokeDynamics(mainMethod.getIR()).forEach(indy -> {
            MethodRef bootstrapMethodRef = indy.getBootstrapMethodRef();
            assertEquals("metafactory",
                    bootstrapMethodRef.getName());
        });
        printIR(mainClass.getDeclaredMethod("getValue").getIR());
    }

    @Test
    void testWithArgs() {
        final String main = "WithArgs";
        Main.buildWorld("-pp", "-cp", CP, "-m", main);
        JClass mainClass = World.get().getClassHierarchy().getClass(main);
        JMethod mainMethod = mainClass.getDeclaredMethod("main");
        printIR(mainMethod.getIR());
        extractInvokeDynamics(mainMethod.getIR()).forEach(indy ->
                assertEquals("actionPerformed",
                        indy.getMethodName())
        );
    }

    @Test
    void testCapture() {
        final String main = "Capture";
        Main.buildWorld("-pp", "-cp", CP, "-m", main);
        JClass mainClass = World.get().getClassHierarchy().getClass(main);
        mainClass.getDeclaredMethods().forEach(m -> printIR(m.getIR()));
    }

    private static Stream<InvokeDynamic> extractInvokeDynamics(IR ir) {
        return ir.invokes(true)
                .filter(Invoke::isDynamic)
                .map(s -> (InvokeDynamic) s.getInvokeExp());
    }

    private static void printIR(IR ir) {
        if (isPrintIR) {
            IRPrinter.print(ir, System.out);
        }
    }
}
