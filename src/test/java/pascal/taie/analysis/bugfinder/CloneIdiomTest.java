


package pascal.taie.analysis.bugfinder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.World;
import pascal.taie.analysis.Tests;
import pascal.taie.util.collection.Sets;

import java.util.Set;

public class CloneIdiomTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "CloneIdiom1",
            "CloneIdiom2",
            "CloneIdiom3",
            "CloneIdiom4",
    })
    void test(String inputClass) {
        Tests.testInput(inputClass, "src/test/resources/bugfinder/CloneIdiom", CloneIdiom.ID);
        Set<BugInstance> bugInstances = Sets.newSet();
        World.get()
                .getClassHierarchy()
                .applicationClasses()
                .forEach(jClass -> bugInstances.addAll(jClass.getResult(CloneIdiom.ID)));
    }

}
