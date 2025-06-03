

package pascal.taie.config;

import org.junit.jupiter.api.Test;
import pascal.taie.util.collection.Maps;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnalysisOptionsTest {

    @Test
    void testNullOption() {
        AnalysisOptions options = new AnalysisOptions(getOptionValues());
        assertNull(options.get("z"));
    }

    @Test
    void testNonExistOption() {
        assertThrows(ConfigException.class, () -> {
            AnalysisOptions options = new AnalysisOptions(getOptionValues());
            options.get("non-exist-key");
        });
    }

    private Map<String, Object> getOptionValues() {
        Map<String, Object> values = Maps.newMap();
        values.put("x", 100);
        values.put("y", "666");
        values.put("z", null);
        return values;
    }
}
