package pascal.taie.android;

import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.analysis.misc.IRDumper;
import pascal.taie.analysis.pta.PointerAnalysis;
import pascal.taie.analysis.pta.PointerAnalysisResultImpl;
import pascal.taie.analysis.pta.plugin.taint.TaintAnalysis;
import pascal.taie.analysis.pta.plugin.taint.TaintFlow;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AndroidBenchTest {

    private static final String BENCHMARK_INFO = "benchmark-info.yml";

    protected static final String TAINT_CONFIG_MICRO = "android-benchmarks/taint-config-micro.yml";

    protected static final String TAINT_CONFIG_REAL = "android-benchmarks/taint-config-real.yml";

    public void run(String benchmarkPrefix, String benchmark, boolean isRealWorld) {
        System.out.println("\nAnalyzing " + benchmark);
        Map<String, AndroidBenchmarkInfo> benchmarkInfos = AndroidBenchmarkInfo.load(benchmarkPrefix, BENCHMARK_INFO);
        AndroidBenchmarkInfo info = benchmarkInfos.get(benchmark);
        Main.main(composeArgs(benchmarkPrefix, info, isRealWorld));
        PointerAnalysisResultImpl result = World.get().getResult(PointerAnalysis.ID);
        Set<TaintFlow> res = result.getResult(TaintAnalysis.class.getName());
        if (!isRealWorld) {
            assertEquals(info.expected(), res.size());
        }
    }

    private String[] composeArgs(String benchmarkHomePrefix, AndroidBenchmarkInfo info, boolean isRealWorld) {
        List<String> args = new ArrayList<>();
        Collections.addAll(args,
                "-pp",
                "-cp", new File(benchmarkHomePrefix, info.apk()).getPath(),
                "-am");
        Map<String, String> ptaArgs = Map.of(
                "implicit-entries", "false",
                "distinguish-string-constants", "app",
                "merge-string-objects", "true",
                "taint-config", isRealWorld ? TAINT_CONFIG_REAL : TAINT_CONFIG_MICRO,
                "propagate-types", "[reference,int,long,double,char,float]",
                "reflection-inference", "string-constant"
        );

        Collections.addAll(args,
                "-a", "pta=" + ptaArgs.entrySet()
                        .stream()
                        .map(e -> e.getKey() + ":" + e.getValue())
                        .collect(Collectors.joining(";"))
        );
        return args.toArray(new String[0]);
    }

}
