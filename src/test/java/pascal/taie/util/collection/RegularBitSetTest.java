

package pascal.taie.util.collection;

public class RegularBitSetTest extends IBitSetTest {

    @Override
    protected IBitSet of(int... indexes) {
        return IBitSet.of(indexes);
    }
}
