package pascal.taie.analysis.pta.plugin.android.icc;

import pascal.taie.analysis.pta.core.cs.element.CSVar;

import java.util.List;

public record IntentInfo(List<CSVar> csVar,
                         IntentInfoKind kind) {

}
