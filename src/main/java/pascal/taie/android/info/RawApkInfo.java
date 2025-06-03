package pascal.taie.android.info;

import pascal.taie.android.util.LayoutFileParser;
import soot.jimple.infoflow.android.manifest.ProcessManifest;
import soot.jimple.infoflow.android.resources.ARSCFileParser;

public record RawApkInfo(ProcessManifest manifest,
                         LayoutFileParser layoutFile,
                         ARSCFileParser resources) {

}
