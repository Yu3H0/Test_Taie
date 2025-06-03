

package pascal.taie.analysis.dataflow.analysis.constprop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MeetValueTest {

    private final Value i1 = Value.makeConstant(1);
    private final Value i0 = Value.makeConstant(0);
    private final Value NAC = Value.getNAC();
    private final Value undef = Value.getUndef();
    private final ConstantPropagation.Analysis cp =
            new ConstantPropagation.Analysis(null, true);

    @Test
    void testMeet() {
        assertEquals(cp.meetValue(undef, undef), undef);
        assertEquals(cp.meetValue(undef, i0), i0);
        assertEquals(cp.meetValue(undef, NAC), NAC);
        assertEquals(cp.meetValue(NAC, NAC), NAC);
        assertEquals(cp.meetValue(NAC, i0), NAC);
        assertEquals(cp.meetValue(NAC, undef), NAC);
        assertEquals(cp.meetValue(i0, i0), i0);
        assertEquals(cp.meetValue(i0, i1), NAC);
        assertEquals(cp.meetValue(i0, undef), i0);
        assertEquals(cp.meetValue(i0, NAC), NAC);
    }
}
