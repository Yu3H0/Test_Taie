

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.core.cs.element.CSVar;
import pascal.taie.ir.IR;
import pascal.taie.ir.exp.Var;
import pascal.taie.language.classes.JMethod;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;

import java.util.function.Predicate;

import static pascal.taie.analysis.pta.plugin.util.InvokeUtils.BASE;

/**
 * Handles sanitizers in taint analysis.
 */
class SanitizerHandler extends OnFlyHandler {

    private final MultiMap<JMethod, ParamSanitizer> paramSanitizers = Maps.newMultiMap();

    /**
     * Used to filter out taint objects from points-to set.
     */
    private final Predicate<CSObj> taintFilter;

    SanitizerHandler(HandlerContext context) {
        super(context);
        taintFilter = o -> !context.manager().isTaint(o.getObject());
        context.config().paramSanitizers()
                .forEach(s -> this.paramSanitizers.put(s.method(), s));
    }

    /**
     *
     * Handles parameter sanitizers.
     */
    @Override
    public void onNewCSMethod(CSMethod csMethod) {
        JMethod method = csMethod.getMethod();
        if (paramSanitizers.containsKey(method)) {
            Context context = csMethod.getContext();
            IR ir = method.getIR();
            paramSanitizers.get(method).forEach(sanitizer -> {
                Var param = getParam(ir, sanitizer.index());
                CSVar csParam = csManager.getCSVar(context, param);
                solver.addPointerFilter(csParam, taintFilter);
            });
        }
    }

    private static Var getParam(IR ir, int index) {
        return switch (index) {
            case BASE -> ir.getThis();
            default -> ir.getParam(index);
        };
    }
}
