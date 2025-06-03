package pascal.taie.android;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import pascal.taie.android.droidbench.DroidBenchTestSuite;
import pascal.taie.android.iccbench.ICCBenchTest;
import pascal.taie.android.ubcbench.UBCBenchTest;

@Suite
@SelectClasses({
        DroidBenchTestSuite.class,
        ICCBenchTest.class,
        UBCBenchTest.class
})
public class AndroidBenchTestSuite {
}
