

package pascal.taie.analysis.pta.plugin.reflection;

import pascal.taie.analysis.pta.core.cs.context.Context;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.analysis.pta.core.solver.Solver;
import pascal.taie.analysis.pta.plugin.Plugin;
import pascal.taie.analysis.pta.plugin.util.CSObjs;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.proginfo.MethodRef;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.annotation.Annotation;
import pascal.taie.language.annotation.ClassElement;
import pascal.taie.language.annotation.Element;
import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.classes.ClassNames;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.Subsignature;

/**
 * Handles annotation-related APIs.
 * Since usually these APIs are tightly coupled with reflection and use
 * reflection objects, we put this class in this package.
 */
class AnnotationModel implements Plugin {

    private final Solver solver;

    private final ClassHierarchy hierarchy;

    private final MetaObjHelper helper;

    private final JClass annotation;

    private final Subsignature annotationType;

    AnnotationModel(Solver solver, MetaObjHelper helper) {
        this.solver = solver;
        this.hierarchy = solver.getHierarchy();
        this.helper = helper;
        this.annotation = hierarchy.getClass(ClassNames.ANNOTATION);
        this.annotationType = Subsignature.get("java.lang.Class annotationType()");
    }

    /**
     * Annotation objects are of interface types, thus the calls on them
     * can not be resolved, and we handle such calls here.
     */
    @Override
    public void onUnresolvedCall(CSObj recv, Context context, Invoke invoke) {
        MethodRef ref = invoke.getMethodRef();
        JClass declaringClass = ref.getDeclaringClass();
        if (declaringClass.equals(annotation) &&
                ref.getSubsignature().equals(annotationType)) {
            annotationType(recv, context, invoke);
        }
        if (hierarchy.isSubclass(annotation, declaringClass)) {
            getElement(recv, context, invoke);
        }
    }

    private void annotationType(CSObj recv, Context context, Invoke invoke) {
        Var result = invoke.getResult();
        if (result != null) {
            Annotation a = CSObjs.toAnnotation(recv);
            if (a != null) {
                JClass annoType = hierarchy.getClass(a.getType());
                Obj annoTypeObj = helper.getMetaObj(annoType);
                solver.addVarPointsTo(context, result, annoTypeObj);
            }
        }
    }

    /**
     * Models APIs to obtain annotation elements.
     */
    private void getElement(CSObj recv, Context context, Invoke invoke) {
        Var result = invoke.getResult();
        if (result != null) {
            Annotation a = CSObjs.toAnnotation(recv);
            if (a != null) {
                String methodName = invoke.getMethodRef().getName();
                Element elem = a.getElement(methodName);
                if (elem instanceof ClassElement classElem) {
                    JClass clazz = hierarchy.getClass(classElem.classDescriptor());
                    Obj classObj = helper.getMetaObj(clazz);
                    solver.addVarPointsTo(context, result, classObj);
                }
            }
        }
    }
}
