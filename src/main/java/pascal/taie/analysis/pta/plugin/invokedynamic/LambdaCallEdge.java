

package pascal.taie.analysis.pta.plugin.invokedynamic;

import pascal.taie.analysis.graph.callgraph.OtherEdge;
import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.ir.exp.InvokeDynamic;
import pascal.taie.ir.exp.Var;

import java.util.List;

/**
 * Represents call edge on lambda functional object.
 * The edge carries the information about invokedynamic invocation site
 * where the lambda functional object was created.
 */
class LambdaCallEdge extends OtherEdge<CSCallSite, CSMethod> {

    private final InvokeDynamic lambdaIndy;

    private final Context lambdaContext;

    LambdaCallEdge(CSCallSite csCallSite, CSMethod callee,
                   InvokeDynamic lambdaIndy, Context lambdaContext) {
        super(csCallSite, callee);
        this.lambdaIndy = lambdaIndy;
        this.lambdaContext = lambdaContext;
    }

    List<Var> getCapturedArgs() {
        return lambdaIndy.getArgs();
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
        if (!super.equals(o)) {
            return false;
        }
        LambdaCallEdge that = (LambdaCallEdge) o;
        return lambdaIndy.equals(that.lambdaIndy) &&
                lambdaContext.equals(that.lambdaContext);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + lambdaIndy.hashCode();
        result = 31 * result + lambdaContext.hashCode();
        return result;
    }
}
