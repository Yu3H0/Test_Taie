

package pascal.taie.analysis.pta.plugin.invokedynamic;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.ir.exp.InvokeDynamic;
import pascal.taie.util.Hashes;

class InstanceInvoInfo {

    private final CSCallSite csCallSite;

    private final InvokeDynamic lambdaIndy;

    private final Context lambdaContext;

    InstanceInvoInfo(CSCallSite csCallSite,
                     InvokeDynamic lambdaIndy,
                     Context lambdaContext) {
        this.csCallSite = csCallSite;
        this.lambdaIndy = lambdaIndy;
        this.lambdaContext = lambdaContext;
    }

    CSCallSite getCSCallSite() {
        return csCallSite;
    }

    InvokeDynamic getLambdaIndy() {
        return lambdaIndy;
    }

    Context getLambdaContext() {
        return lambdaContext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InstanceInvoInfo that = (InstanceInvoInfo) o;
        return csCallSite.equals(that.csCallSite) &&
                lambdaIndy.equals(that.lambdaIndy) &&
                lambdaContext.equals(that.lambdaContext);
    }

    @Override
    public int hashCode() {
        return Hashes.hash(csCallSite, lambdaIndy, lambdaContext);
    }
}
