package pascal.taie.android.util;

import com.google.common.collect.Maps;
import pascal.taie.android.config.AndroidLifecycleConfig;
import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.classes.Subsignature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AndroidLifecycleHelper {

    private final Map<JClass, List<JMethod>> config;

    private final Map<JClass, List<JMethod>> componentMethods;

    private final ClassHierarchy hierarchy;

    public AndroidLifecycleHelper(ClassHierarchy hierarchy) {
        this.config = AndroidLifecycleConfig.loadAndroidLifecycleConfig()
                .stream()
                .collect(
                        Collectors.toMap(
                                config -> hierarchy.getClass(config.className()),
                                config -> config.callbackMethodSubSigs()
                                        .stream()
                                        .map(subSig -> hierarchy.getMethod("<" + config.className() + ": " + subSig + ">"))
                                        .filter(Objects::nonNull)
                                        .toList()
                        )
                );
        this.componentMethods = Maps.newHashMap();
        this.hierarchy = hierarchy;
    }

    public boolean isLifeCycleMethod(JMethod method, String component, Subsignature methodSubSig) {
        return isComponent(component, method.getDeclaringClass()) && method.getSubsignature().equals(methodSubSig);
    }

    public boolean isComponent(String component, JClass current) {
        return isComponent(hierarchy.getClass(component), current);
    }

    public boolean isComponent(JClass component, JClass current) {
        return hierarchy.isSubclass(component, current);
    }

    public List<JMethod> getLifeCycleMethods(JClass entryClass) {
        List<JMethod> lifecycleMethods = new ArrayList<>();
        if (!entryClass.isApplication()) {
            return lifecycleMethods;
        }
        JMethod init = entryClass.getDeclaredMethod(Subsignature.get(Subsignature.NO_ARG_INIT));
        if (init != null) {
            lifecycleMethods.add(init);
        }
        getLifecycleMethods(entryClass).forEach(lifecycleMethod -> {
            JMethod m = hierarchy.dispatch(entryClass, lifecycleMethod.getRef());
            if (m != null && m.isApplication()) {
                lifecycleMethods.add(m);
            }
        });
        componentMethods.put(entryClass, lifecycleMethods);
        return lifecycleMethods;
    }

    public JMethod getLifeCycleMethod(JClass entryClass, Subsignature subSig) {
        return componentMethods.computeIfAbsent(entryClass, __ -> new ArrayList<>()).stream()
                .filter(method -> method.getSubsignature().equals(subSig))
                .findFirst()
                .orElse(null);
    }

    private Set<JMethod> getLifecycleMethods(JClass entryClass) {
        return config.keySet().stream()
                .filter(component -> isComponent(component, entryClass))
                .flatMap(component -> config.get(component).stream())
                .collect(Collectors.toSet());
    }

}
