package pascal.taie.analysis.pta.plugin.android.misc;

import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.plugin.android.AndroidContext;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;

/**
 * Contains information used by android other specific analysis handlers.
 */
public class AndroidMiscContext extends AndroidContext {

    private final MultiMap<CSObj, CSMethod> sharedPreferences2Callback = Maps.newMultiMap();

    public MultiMap<CSObj, CSMethod> sharedPreferences2Callback() {
        return sharedPreferences2Callback;
    }

    public AndroidMiscContext(AndroidContext androidContext) {
        super(androidContext);
    }

}
