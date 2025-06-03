

package pascal.taie.analysis.graph.callgraph;

interface CGBuilder<CallSite, Method> {

    CallGraph<CallSite, Method> build();
}
