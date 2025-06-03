package pascal.taie.android.droidbench;

import pascal.taie.android.AndroidBenchTest;

import java.io.File;

public class DroidBenchTest extends AndroidBenchTest {

    private static final String BENCHMARK_HOME_PREFIX = "android-benchmarks/DroidBench/apk";

    public void run(String type, String benchmark) {
        super.run(new File(BENCHMARK_HOME_PREFIX, type).getPath(), benchmark, false);
    }

}
