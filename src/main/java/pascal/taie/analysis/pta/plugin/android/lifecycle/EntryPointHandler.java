package pascal.taie.analysis.pta.plugin.android.lifecycle;

import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.language.classes.JClass;
import pascal.taie.util.collection.Maps;

import java.util.Map;
import java.util.Set;

import static pascal.taie.android.AndroidClassNames.BUNDLE;

/**
 * Initializes android entry points for pointer analysis.
 */
public class EntryPointHandler extends LifecycleHandler {

    public EntryPointHandler(LifecycleContext context) {
        super(context);
    }

    @Override
    public void onStart() {
        Set<JClass> entryClasses = handlerContext.apkInfo().getEntrypointClasses();
        // process android lifecycle method
        for (JClass ec : entryClasses) {
            Obj thisObj = handlerContext.androidObjManager().getComponentObj(ec);
            handlerContext.lifecycleHelper().getLifeCycleMethods(ec).forEach(em -> {
                Map<Integer, Obj> paramIndex = Maps.newMap();
                for (int i = 0; i < em.getParamCount(); i++) {
                    // android.os.Bundle stores component context information
                    if (em.getParamType(i).getName().equals(BUNDLE)) {
                        paramIndex.put(i, handlerContext.androidObjManager().getLifecycleMethodParamObj(ec, em.getIR().getParam(i)));
                    }
                }
                addEntryPoint(em, thisObj, paramIndex);
            });
        }
    }

}
