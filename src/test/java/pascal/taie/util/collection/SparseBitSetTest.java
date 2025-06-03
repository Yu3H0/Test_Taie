

package pascal.taie.util.collection;

public class SparseBitSetTest extends IBitSetTest {

    @Override
    protected IBitSet of(int... indexes) {
        IBitSet result = new SparseBitSet();
        for (int i : indexes) {
            result.set(i);
        }
        return result;
    }
}
