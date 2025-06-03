package pascal.taie.android.iccbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.android.AndroidBenchTest;

public class ICCBenchTest extends AndroidBenchTest {

    private static final String BENCHMARK_HOME_PREFIX = "android-benchmarks/ICC-Bench/apks/";

    @ParameterizedTest
    @ValueSource(strings = {
            "rpc_localservice",
            "rpc_messengerservice",
            "rpc_returnsensitive",
            "rpc_remoteservice",
            "icc_rpc_comprehensive",
            "icc_dynregister1",
            "icc_dynregister2",
            "icc_explicit1",
            "icc_implicit_action",
            "icc_implicit_category",
            "icc_implicit_data1",
            "icc_implicit_data2",
            "icc_implicit_mix1",
            "icc_implicit_mix2",
            "icc_explicit_nosrc_nosink",
            "icc_explicit_nosrc_sink",
            "icc_explicit_src_nosink",
            "icc_explicit_src_sink",
            "icc_implicit_nosrc_nosink",
            "icc_implicit_nosrc_sink",
            "icc_implicit_src_nosink",
            "icc_implicit_src_sink",
            "icc_intentservice",
            "icc_stateful",
    })
    void test(String benchmark) {
        run(BENCHMARK_HOME_PREFIX, benchmark, false);
    }
}
