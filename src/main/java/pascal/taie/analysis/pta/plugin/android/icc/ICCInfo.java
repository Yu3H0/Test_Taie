package pascal.taie.analysis.pta.plugin.android.icc;

import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.core.cs.element.CSVar;

import javax.annotation.Nullable;

/**
 * ICC related info.
 * @param info ICC related intent or message
 * @param kind ICC Kind
 * @param handlerObj it is valid only if info is messenger init with android.os.Handler
 * @param iccCSCallSite it is valid only if it needs to add ICCCallEdge
 */
public record ICCInfo(CSVar info,
                      ICCInfoKind kind,
                      @Nullable
                      CSObj handlerObj,
                      @Nullable
                      CSCallSite iccCSCallSite) {

}
