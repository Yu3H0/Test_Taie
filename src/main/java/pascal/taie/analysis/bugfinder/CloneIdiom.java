

package pascal.taie.analysis.bugfinder;

import pascal.taie.World;
import pascal.taie.analysis.ClassAnalysis;
import pascal.taie.config.AnalysisConfig;
import pascal.taie.ir.exp.InvokeExp;
import pascal.taie.ir.exp.InvokeSpecial;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.ir.stmt.Stmt;
import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.classes.ClassNames;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;
import pascal.taie.util.collection.Sets;

import java.util.Set;

public class CloneIdiom extends ClassAnalysis<Set<BugInstance>> {

    public static final String ID = "clone-idiom";

    public CloneIdiom(AnalysisConfig config) {
        super(config);
    }

    /*
        1.analyze class
        2.analyze clone method
        3.analyze the ir of clone method
        4.produce result
     */
    @Override
    public Set<BugInstance> analyze(JClass jclass) {
        boolean implementsCloneableDirectly = false;
        boolean invokesSuperClone = false;
        boolean isCloneable = false;
        boolean isFinal = jclass.isFinal();
        Set<BugInstance> bugInstances = Sets.newSet();

        if (jclass.isInterface() || jclass.isAbstract()) {
            return Set.of();
        }

        for (JClass iface : jclass.getInterfaces()) {
            if (ClassNames.CLONEABLE.equals(iface.getName())) {
                implementsCloneableDirectly = true;
                isCloneable = true;
                break;
            }
        }

        ClassHierarchy hierarchy = World.get().getClassHierarchy();
        JClass classCloneable = hierarchy.getClass(ClassNames.CLONEABLE);
        if (hierarchy.isSubclass(classCloneable, jclass)) {
            isCloneable = true;
        }

        boolean cloneIsDeprecated = false;
        boolean hasCloneMethod = false;
        for (JMethod jmethod : jclass.getDeclaredMethods()) {
            if (!jmethod.isAbstract() && jmethod.isPublic() && jmethod.getSubsignature().toString().equals("java.lang.Object clone()")) {
                hasCloneMethod = true;
                cloneIsDeprecated = jmethod.getAnnotation("java.lang.Deprecated") != null;
                // ir
                for (Stmt stmt : jmethod.getIR()) {
                    if (stmt instanceof Invoke invoke) {
                        InvokeExp invokeExp = invoke.getInvokeExp();
                        if (invokeExp instanceof InvokeSpecial && ((InvokeSpecial) invokeExp).getBase().getName().equals("%this")) {
                            invokesSuperClone = true;
                        }
                    }
                }
            }
        }

        //generate analysis result
        if (implementsCloneableDirectly && !hasCloneMethod) {
            bugInstances.add(new BugInstance(BugType.CN_IDIOM, Severity.MINOR, jclass));
        }

        if (hasCloneMethod && isCloneable && !invokesSuperClone && !isFinal && jclass.isPublic()) {
            bugInstances.add(new BugInstance(
                    BugType.CN_IDIOM_NO_SUPER_CALL, Severity.MINOR, jclass));
        } else if (hasCloneMethod && !isCloneable && !cloneIsDeprecated && !jclass.isAbstract()) {
            bugInstances.add(new BugInstance(
                    BugType.CN_IMPLEMENTS_CLONE_BUT_NOT_CLONEABLE, Severity.MINOR, jclass));
        }
        return bugInstances;
    }

    private enum BugType implements pascal.taie.analysis.bugfinder.BugType {
        CN_IDIOM,
        CN_IDIOM_NO_SUPER_CALL,
        CN_IMPLEMENTS_CLONE_BUT_NOT_CLONEABLE
    }
}
