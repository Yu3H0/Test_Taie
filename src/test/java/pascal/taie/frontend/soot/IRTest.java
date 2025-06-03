

package pascal.taie.frontend.soot;

import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.ir.IRPrinter;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.Comparator;
import java.util.List;

public class IRTest {

    private static final List<String> targets = List.of("AllInOne");

    private static void buildWorld(String mainClass) {
        Main.buildWorld("-pp", "-cp", "src/test/resources/world", "--input-classes", mainClass);
    }

    @Test
    void testBottomType() {
        String clzName = "android$widget$RemoteViews$BaseReflectionAction";
        Main.buildWorld("-ap", "-cp", "src/test/resources/world",
                "--input-classes", clzName);
        World.get().getClassHierarchy().getClass(clzName)
             .getDeclaredMethod("initActionAsync").getIR();
    }

    @Test
    void testIRBuilder() {
        targets.forEach(main -> {
            buildWorld(main);
            JClass mainClass = World.get().getClassHierarchy().getClass(main);
            mainClass.getDeclaredMethods()
                    .stream()
                    .sorted(Comparator.comparing(JMethod::toString))
                    .forEach(m ->
                            IRPrinter.print(m.getIR(), System.out));
            System.out.println("------------------------------\n");
        });
    }

    @Test
    void testDefUse() {
        String main = "DefUse";
        buildWorld(main);
        JClass mainClass = World.get().getClassHierarchy().getClass(main);
        mainClass.getDeclaredMethods().forEach(m -> {
            System.out.println(m);
            m.getIR().forEach(stmt ->
                    System.out.printf("%s, def: %s, uses: %s%n",
                            stmt, stmt.getDef(), stmt.getUses()));
            System.out.println("--------------------");
        });
    }
}
