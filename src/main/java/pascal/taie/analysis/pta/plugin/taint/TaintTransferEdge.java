

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.pta.core.cs.element.Pointer;
import pascal.taie.analysis.pta.core.solver.OtherEdge;

class TaintTransferEdge extends OtherEdge {

    TaintTransferEdge(Pointer source, Pointer target) {
        super(source, target);
    }
}
