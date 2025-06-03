

package pascal.taie.ir;

import org.junit.jupiter.api.Test;
import pascal.taie.ir.exp.DoubleLiteral;
import pascal.taie.ir.exp.FloatLiteral;
import pascal.taie.ir.exp.IntLiteral;
import pascal.taie.ir.exp.LongLiteral;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LiteralTest {

    @Test
    void testNumericLiteral() {
        Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            testIntLiteral(random.nextInt());
            testLongLiteral(random.nextLong());
            testFloatLiteral(random.nextFloat());
            testDoubleLiteral(random.nextDouble());
        }
    }

    private static void testIntLiteral(int i) {
        assertEquals(IntLiteral.get(i).getValue(), i);
    }

    private static void testLongLiteral(long l) {
        assertEquals(LongLiteral.get(l).getValue(), l);
    }

    private static void testFloatLiteral(float f) {
        assertEquals(FloatLiteral.get(f).getValue(), f, 0);
    }

    private static void testDoubleLiteral(double d) {
        assertEquals(DoubleLiteral.get(d).getValue(), d, 0);
    }
}
