package pascal.taie.analysis.pta.plugin.android.misc;

import pascal.taie.analysis.pta.plugin.CompositePlugin;
import pascal.taie.analysis.pta.plugin.android.AndroidContext;

public class AndroidMiscAnalysis extends CompositePlugin {

    public AndroidMiscAnalysis(AndroidContext androidContext) {
        AndroidMiscContext miscContext = new AndroidMiscContext(androidContext);

        addPlugin(
                new MapHolderHandler(miscContext),
                new OtherMiscModel(miscContext),
                new SharedPreferencesModel(miscContext),
                new ApplicationModel(miscContext),
                new AsyncTaskModel(miscContext));
    }

}
