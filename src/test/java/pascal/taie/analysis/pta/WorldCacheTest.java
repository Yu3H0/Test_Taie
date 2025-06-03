


package pascal.taie.analysis.pta;

import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.frontend.cache.CachedIRBuilder;
import pascal.taie.frontend.cache.CachedWorldBuilder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldCacheTest {

    @Test
    void testWorldCache() {
        String[] args = {
                "-wc",
                "-java", "8",
                "-cp", "src/test/resources/pta/contextsensitivity",
                "-m", "LinkedQueue",
                "-a", """
                        pta=
                        cs:2-obj;
                        implicit-entries:false;
                        expected-file:src/test/resources/pta/contextsensitivity/LinkedQueue-pta-expected.txt;
                        only-app:true
                        """
        };
        Main.main(args);
        Main.main(args);
        World world2 = World.get();
        CachedWorldBuilder.getWorldCacheFile(world2.getOptions()).delete();
        assertTrue(world2.getIRBuilder() instanceof CachedIRBuilder);
    }

}
