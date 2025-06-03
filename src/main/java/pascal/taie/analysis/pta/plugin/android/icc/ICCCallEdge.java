package pascal.taie.analysis.pta.plugin.android.icc;

import pascal.taie.analysis.graph.callgraph.OtherEdge;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;

/**
 * Represents ICC call edges.
 */
class ICCCallEdge extends OtherEdge<CSCallSite, CSMethod> {

    /**
     * Variable pointing to the android.os.Message or android.content.Intent argument of icc implicit call,
     * which contains the arguments for the icc target method, i.e.,
     */
    private final ICCInfo iccInfo;

    ICCCallEdge(ICCInfo sourceICCInfo, CSMethod callee) {
        super(sourceICCInfo.iccCSCallSite(), callee);
        this.iccInfo = sourceICCInfo;
    }

    ICCInfo getICCInfo() {
        return iccInfo;
    }
}
