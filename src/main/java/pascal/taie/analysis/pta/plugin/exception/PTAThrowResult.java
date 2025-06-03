

package pascal.taie.analysis.pta.plugin.exception;

import pascal.taie.language.classes.JMethod;
import pascal.taie.util.collection.Maps;

import java.util.Map;
import java.util.Optional;

public class PTAThrowResult {

    private final Map<JMethod, MethodThrowResult> results = Maps.newMap(1024);

    MethodThrowResult getOrCreateResult(JMethod method) {
        return results.computeIfAbsent(method, MethodThrowResult::new);
    }

    public Optional<MethodThrowResult> getResult(JMethod method) {
        return Optional.ofNullable(results.get(method));
    }
}
