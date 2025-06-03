

package pascal.taie.analysis.dataflow.analysis.constprop;

import org.junit.jupiter.api.Test;
import pascal.taie.util.AnalysisException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValueTest {

    @Test
    void testInt() {
        Value v1 = Value.makeConstant(10);
        assertTrue(v1.isConstant());
        assertFalse(v1.isNAC() || v1.isUndef());
        assertEquals(v1.getConstant(), 10);
        Value v2 = Value.makeConstant(1);
        Value v3 = Value.makeConstant(10);
        assertNotEquals(v1, v2);
        assertEquals(v1, v3);
    }

    @Test
    void testGetIntOnNAC() {
        assertThrows(AnalysisException.class, () ->
                Value.getNAC().getConstant());
    }

    @Test
    void testGetIntOnUndef() {
        assertThrows(AnalysisException.class, () ->
                Value.getUndef().getConstant());
    }
}
