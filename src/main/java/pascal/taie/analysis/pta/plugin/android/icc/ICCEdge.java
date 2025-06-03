package pascal.taie.analysis.pta.plugin.android.icc;

import pascal.taie.analysis.pta.core.cs.element.Pointer;
import pascal.taie.analysis.pta.plugin.android.AndroidTransferEdge;

class ICCEdge extends AndroidTransferEdge {

    ICCEdge(Pointer source, Pointer target) {
        super(source, target);
    }
}
