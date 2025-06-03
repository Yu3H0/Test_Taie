package pascal.taie.android.droidbench;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        AliasingTest.class,
        AndroidSpecificTest.class,
        ArraysAndListsTest.class,
        CallbackTest.class,
//        DynamicLoadingTest.class,
        EmulatorDetectionTest.class,
        FieldAndObjectSensitivityTest.class,
        GeneralJavaTest.class,
//        ImplicitFlowsTest.class,
        InterAppCommunicationTest.class,
        InterComponentCommunicationTest.class,
        LifecycleTest.class,
//        NativeTest.class,
//        ReflectionICCTest.class,
        ReflectionTest.class,
//        SelfModificationTest.class,
        ThreadingTest.class,
        UnreachableCodeTest.class
})
public class DroidBenchTestSuite {
}
