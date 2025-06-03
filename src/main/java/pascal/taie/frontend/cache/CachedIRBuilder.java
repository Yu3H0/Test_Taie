

package pascal.taie.frontend.cache;


import pascal.taie.ir.IR;
import pascal.taie.ir.IRBuilder;
import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The {@link pascal.taie.ir.IRBuilder} is for keeping the {@link IR}s of all methods to
 * prevent cyclic references with too long a path which may make
 * the serialization fail or {@link java.lang.StackOverflowError}.
 */
public class CachedIRBuilder implements IRBuilder {

    private final Map<String, IR> methodSig2IR;

    public CachedIRBuilder(IRBuilder irBuilder, ClassHierarchy hierarchy) {
        irBuilder.buildAll(hierarchy);
        methodSig2IR = hierarchy.allClasses()
                .map(JClass::getDeclaredMethods)
                .flatMap(Collection::stream)
                .filter(m -> !m.isAbstract() || m.isNative())
                .collect(Collectors.toMap(JMethod::getSignature, JMethod::getIR));
    }

    /**
     * This method will be called by {@link JMethod#getIR()} only once,
     * so remove the IR from the map after returning it.
     */
    @Override
    public IR buildIR(JMethod method) {
        return methodSig2IR.remove(method.getSignature());
    }

    @Override
    public void buildAll(ClassHierarchy hierarchy) {
        hierarchy.allClasses()
                .map(JClass::getDeclaredMethods)
                .flatMap(Collection::stream)
                .filter(m -> !m.isAbstract() || m.isNative())
                .forEach(JMethod::getIR);
    }
}
