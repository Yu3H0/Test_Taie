

package pascal.taie.analysis.pta;

import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.analysis.graph.callgraph.CallGraph;
import pascal.taie.analysis.graph.callgraph.CallGraphBuilder;
import pascal.taie.analysis.graph.callgraph.Edge;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.classes.JMethod;
import pascal.taie.util.collection.MultiMap;
import pascal.taie.util.collection.MultiMapCollector;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CustomEntryPointTest {

    private static final String[][] CALLER_CALLEE_RELATIONS = {
            {
                    "false",
                    "<CustomEntryPoints: void entryWithEmptyParam(Param1,Param1[])>",
                    "<Param1: java.lang.String getS1()>",
            },
            {
                    "false",
                    "<CustomEntryPoints: void entryWithEmptyParam(Param1,Param1[])>",
                    "<Param1: void setS2(java.lang.String)>",
            },
            {
                    "true",
                    "<CustomEntryPoints: void entryWithDeclaredParam1(Param1,Param1[])>",
                    "<Param1: java.lang.String getS1()>",
            },
            {
                    "true",
                    "<CustomEntryPoints: void entryWithDeclaredParam1(Param1,Param1[])>",
                    "<Param1: void setS2(java.lang.String)>",
            },
            {
                    "true",
                    "<CustomEntryPoints: void entryWithDeclaredParam2(Param2)>",
                    "<Param2: java.lang.String getS1()>",
            },
            {
                    "true",
                    "<CustomEntryPoints: void entryWithDeclaredParam2(Param2)>",
                    "<Param2: Param1 getP1()>",
            },
            {
                    "true",
                    "<CustomEntryPoints: void entryWithDeclaredParam2(Param2)>",
                    "<Param1: java.lang.String getS2()>",
            },
            {
                    "true",
                    "<CustomEntryPoints: void entryWithSpecifiedParam(Param1,Param1[],java.lang.String)>",
                    "<Param1: java.lang.String getS1()>",
            },
            {
                    "true",
                    "<CustomEntryPoints: void entryWithSpecifiedParam(Param1,Param1[],java.lang.String)>",
                    "<Param1: void setS2(java.lang.String)>",
            },
            {
                    "true",
                    "<CustomEntryPoints: void entryWithSpecifiedParam(Param1,Param1[],java.lang.String)>",
                    "<java.lang.String: java.lang.String toString()>",
            },
            {
                    "true",
                    "<CustomEntryPoints: void entryWithSpecifiedParam(Param1,Param1[],java.lang.String)>",
                    "<java.lang.Object: java.lang.String toString()>",
            },
    };

    @Test
    void test() {
        Main.main("-pp",
                "-cp", "src/test/resources/pta/entrypoint",
                "--input-classes", "CustomEntryPoints",
                "-a", "pta=only-app:true;implicit-entries:false;"
                        + "plugins:[pascal.taie.analysis.pta.CustomEntryPointPlugin];",
                "-a", "cg");
        CallGraph<Invoke, JMethod> cg = World.get().getResult(CallGraphBuilder.ID);
        MultiMap<JMethod, JMethod> callerCalleeRelations = cg.edges().collect(
                MultiMapCollector.get(e -> e.getCallSite().getContainer(), Edge::getCallee));
        ClassHierarchy hierarchy = World.get().getClassHierarchy();
        for (String[] callerCalleeRelation : CALLER_CALLEE_RELATIONS) {
            boolean reachable = Boolean.parseBoolean(callerCalleeRelation[0]);
            JMethod caller = hierarchy.getMethod(callerCalleeRelation[1]);
            JMethod callee = hierarchy.getMethod(callerCalleeRelation[2]);
            assertEquals(reachable, callerCalleeRelations.contains(caller, callee),
                    caller + " -> " + callee);
        }
    }

}
