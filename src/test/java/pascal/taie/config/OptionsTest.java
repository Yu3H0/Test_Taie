

package pascal.taie.config;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionsTest {

    @Test
    void testHelp() {
        Options options = Options.parse("--help");
        if (options.isPrintHelp()) {
            options.printHelp();
        }
    }

    @Test
    void testJavaVersion() {
        Options options = Options.parse("-java=8");
        assertEquals(options.getJavaVersion(), 8);
    }

    @Test
    void testPrependJVM() {
        Options options = Options.parse("-pp");
        assertEquals(Options.getCurrentJavaVersion(),
                options.getJavaVersion());
    }

    @Test
    void testMainClass() {
        Options options = Options.parse("-cp", "path/to/cp", "-m", "Main");
        assertEquals("Main", options.getMainClass());
    }

    @Test
    void testAllowPhantom() {
        Options options = Options.parse();
        assertFalse(options.isAllowPhantom());
        options = Options.parse("--allow-phantom");
        assertTrue(options.isAllowPhantom());
    }

    @Test
    void testAnalyses() {
        Options options = Options.parse(
                "-a", "cfg=exception:true;scope:inter",
                "-a", "pta=timeout:1800;merge-string-objects:false;cs:2-obj",
                "-a", "throw");
        List<PlanConfig> configs = PlanConfig.readConfigs(options);
        PlanConfig cfg = configs.get(0);
        assertTrue((Boolean) cfg.getOptions().get("exception"));
        assertEquals("inter", cfg.getOptions().get("scope"));
        PlanConfig pta = configs.get(1);
        assertEquals(1800, pta.getOptions().get("timeout"));
        assertFalse((Boolean) pta.getOptions().get("merge-string-objects"));
    }

    @Test
    void testKeepResult() {
        Options options = Options.parse();
        assertEquals(Set.of(Plan.KEEP_ALL), options.getKeepResult());
        options = Options.parse("-kr", "pta,def-use");
        assertEquals(Set.of("pta", "def-use"), options.getKeepResult());
    }

    @Test
    void testClasspath() {
        Options options = Options.parse(
                "-cp", ".\\a.jar",
                "-cp", "./dir\\b",
                "-cp", "./c" + File.pathSeparator + "d.jar",
                "-cp", "e.jar" + File.pathSeparator
        );
        assertEquals(List.of(".\\a.jar", "./dir\\b", "./c", "d.jar", "e.jar"),
                options.getClassPath());
    }

}
