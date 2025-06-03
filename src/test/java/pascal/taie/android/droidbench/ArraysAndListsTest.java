package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ArraysAndListsTest extends DroidBenchTest {

    static final String CATEGORY = "ArraysAndLists";

    /**
     * Tests for ArraysAndLists CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "ArrayAccess1",
            "ArrayAccess2",
            "ArrayAccess3",
            "ArrayAccess4",
            "ArrayAccess5",
            "ArrayCopy1",
            "ArrayToString1",
            "HashMapAccess1",
            "ListAccess1",
            "MultidimensionalArray1"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
