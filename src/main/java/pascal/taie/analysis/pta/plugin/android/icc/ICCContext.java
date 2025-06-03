package pascal.taie.analysis.pta.plugin.android.icc;

import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.core.cs.element.CSVar;
import pascal.taie.analysis.pta.plugin.android.AndroidContext;
import pascal.taie.android.util.IntentInfoMatcher;
import pascal.taie.language.classes.JClass;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;

/**
 * Contains information used by ICC analysis handlers.
 */
public class ICCContext extends AndroidContext {

    private final IntentInfoMatcher intentInfoMatcher;

    private final MultiMap<JClass, JClass> componentICCGraph = Maps.newMultiMap();

    private final MultiMap<CSObj, IntentInfo> intent2IntentInfo = Maps.newMultiMap();

    private final MultiMap<CSObj, CSVar> componentName2Info = Maps.newMultiMap();

    private final MultiMap<CSObj, IntentInfo> intentFilter2IntentInfo = Maps.newMultiMap();

    private final MultiMap<CSObj, CSVar> messenger2Handler = Maps.newMultiMap();

    private final MultiMap<CSObj, CSVar> messenger2IBinder = Maps.newMultiMap();

    private final MultiMap<CSObj, ICCInfo> sendMessage = Maps.newMultiMap();

    private final MultiMap<CSObj, ICCInfo> handleMessage = Maps.newMultiMap();

    private final MultiMap<CSVar, ICCInfo> sourceComponent2ICCInfo = Maps.newMultiMap();

    private final MultiMap<CSVar, ICCInfo> targetComponent2ICCInfo = Maps.newMultiMap();

    private final MultiMap<CSVar, CSVar> serviceComponent2Messenger = Maps.newMultiMap();

    private final MultiMap<CSVar, CSObj> serviceComponent2IBinder = Maps.newMultiMap();

    private final MultiMap<CSVar, CSVar> intent2IBinder = Maps.newMultiMap();

    private final MultiMap<CSVar, CSVar> intents2ServiceConnection = Maps.newMultiMap();

    private final MultiMap<CSVar, CSVar> iBinder2AidlInvoke = Maps.newMultiMap();

    public IntentInfoMatcher intentInfoMatcher() {
        return intentInfoMatcher;
    }

    public MultiMap<JClass, JClass> componentICCGraph() {
        return componentICCGraph;
    }

    public MultiMap<CSObj, IntentInfo> intent2IntentInfo() {
        return intent2IntentInfo;
    }

    public MultiMap<CSObj, CSVar> componentName2Info() {
        return componentName2Info;
    }

    public MultiMap<CSObj, IntentInfo> intentFilter2IntentInfo() {
        return intentFilter2IntentInfo;
    }

    public MultiMap<CSObj, CSVar> messenger2Handler() {
        return messenger2Handler;
    }

    public MultiMap<CSObj, CSVar> messenger2IBinder() {
        return messenger2IBinder;
    }

    public MultiMap<CSObj, ICCInfo> sendMessage() {
        return sendMessage;
    }

    public MultiMap<CSObj, ICCInfo> handleMessage() {
        return handleMessage;
    }

    public MultiMap<CSVar, ICCInfo> sourceComponent2ICCInfo() {
        return sourceComponent2ICCInfo;
    }

    public MultiMap<CSVar, ICCInfo> targetComponent2ICCInfo() {
        return targetComponent2ICCInfo;
    }

    public MultiMap<CSVar, CSVar> serviceComponent2Messenger() {
        return serviceComponent2Messenger;
    }

    public MultiMap<CSVar, CSObj> serviceComponent2IBinder() {
        return serviceComponent2IBinder;
    }

    public MultiMap<CSVar, CSVar> intent2IBinder() {
        return intent2IBinder;
    }

    public MultiMap<CSVar, CSVar> intents2ServiceConnection() {
        return intents2ServiceConnection;
    }

    public MultiMap<CSVar, CSVar> iBinder2Aidl() {
        return iBinder2AidlInvoke;
    }

    public ICCContext(AndroidContext context) {
        super(context);
        this.intentInfoMatcher = new IntentInfoMatcher(context.apkInfo());
    }

}
