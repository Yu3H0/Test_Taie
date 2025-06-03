package pascal.taie.analysis.pta.plugin.android;

import pascal.taie.analysis.pta.core.cs.element.Pointer;
import pascal.taie.analysis.pta.core.solver.OtherEdge;

public class AndroidTransferEdge extends OtherEdge {

    public AndroidTransferEdge(Pointer source, Pointer target) {
        super(source, target);
    }
}
