package pascal.taie.analysis.pta.plugin.android;

import pascal.taie.World;
import pascal.taie.analysis.pta.core.cs.element.CSVar;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.android.info.ApkInfo;
import pascal.taie.android.util.AndroidLifecycleHelper;
import pascal.taie.language.classes.JClass;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;

/**
 * Contains information used by android analysis handlers.
 */
public class AndroidContext {

    private final Solver solver;

    private final AndroidObjManager androidObjManager;

    private final ApkInfo apkInfo;

    private final AndroidLifecycleHelper lifecycleHelper;

    private final MultiMap<JClass, CSVar> dynamicReceiver2IntentFilter;

    public Solver solver() {
        return solver;
    }

    public AndroidObjManager androidObjManager() {
        return androidObjManager;
    }

    public ApkInfo apkInfo() {
        return apkInfo;
    }

    public AndroidLifecycleHelper lifecycleHelper() {
        return lifecycleHelper;
    }

    public MultiMap<JClass, CSVar> dynamicReceiver2IntentFilter() {
        return dynamicReceiver2IntentFilter;
    }

    public AndroidContext(AndroidContext androidContext) {
        this.solver = androidContext.solver;
        this.androidObjManager = androidContext.androidObjManager;
        this.apkInfo = androidContext.apkInfo;
        this.lifecycleHelper = androidContext.lifecycleHelper;
        this.dynamicReceiver2IntentFilter = androidContext.dynamicReceiver2IntentFilter;
    }

    public AndroidContext(Solver solver) {
        this.solver = solver;
        this.androidObjManager = new AndroidObjManager(solver.getHeapModel());
        this.apkInfo = World.get().getApkInfo();
        this.lifecycleHelper = new AndroidLifecycleHelper(solver.getHierarchy());
        this.dynamicReceiver2IntentFilter = Maps.newMultiMap();
    }
}

