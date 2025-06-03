

package pascal.taie.analysis.pta.core.heap;

import pascal.taie.config.AnalysisOptions;
import pascal.taie.ir.stmt.New;

public class AllocationSiteBasedModel extends AbstractHeapModel {

    public AllocationSiteBasedModel(AnalysisOptions options) {
        super(options);
    }

    @Override
    protected Obj doGetObj(New allocSite) {
        return getNewObj(allocSite);
    }
}
