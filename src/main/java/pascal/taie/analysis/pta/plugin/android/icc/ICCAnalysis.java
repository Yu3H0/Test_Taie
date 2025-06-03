package pascal.taie.analysis.pta.plugin.android.icc;

import pascal.taie.analysis.pta.plugin.CompositePlugin;
import pascal.taie.analysis.pta.plugin.android.AndroidContext;

public class ICCAnalysis extends CompositePlugin {

    public ICCAnalysis(AndroidContext androidContext) {
        ICCContext iccContext = new ICCContext(androidContext);
        addPlugin(
                new ComponentICCHandler(iccContext),
                new StartICCModel(iccContext),
                new IntentInfoHandler(iccContext),
                new ComponentNameInfoModel(iccContext),
                new IntentFilterInfoModel(iccContext),
                new MessageHandler(iccContext),
                new AidlHandler(iccContext),
                new SendAndReplyICCHandler(iccContext)
        );
    }

}
