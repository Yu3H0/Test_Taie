

package pascal.taie.analysis.pta.toolkit;

import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;
import pascal.taie.util.collection.Sets;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Computes collection methods in the program.
 */
public class CollectionMethods {

    /**
     * Collection base classes.
     */
    private static final Set<String> COLLECTION_BASES = Set.of(
            "java.util.Collection", "java.util.Map", "java.util.Dictionary");

    /**
     * Collection utility classes.
     */
    private static final Set<String> COLLECTION_UTILS = Set.of(
            "java.util.Collections", "java.util.Arrays");

    private final ClassHierarchy hierarchy;

    public CollectionMethods(ClassHierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    /**
     * @return set of collection methods in the program.
     */
    public Set<JMethod> get() {
        Set<JClass> collectionClasses = Sets.newSet();
        COLLECTION_BASES.stream()
                .map(hierarchy::getJREClass)
                .map(hierarchy::getAllSubclassesOf)
                .flatMap(Collection::stream)
                .filter(Predicate.not(this::isExcluded))
                .forEach(collectionClasses::add);
        COLLECTION_UTILS.stream()
                .map(hierarchy::getJREClass)
                .forEach(collectionClasses::add);
        Set<JClass> allCollectionClasses = Sets.newSet(collectionClasses);
        collectionClasses.forEach(c ->
                allCollectionClasses.addAll(getAllInnerClassesOf(c)));
        return allCollectionClasses.stream()
                .map(JClass::getDeclaredMethods)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableSet());
    }

    private boolean isExcluded(JClass jclass) {
        return jclass.isApplication() &&
                getAllInnerClassesOf(jclass).size() > 10;
    }

    private Set<JClass> getAllInnerClassesOf(JClass jclass) {
        Set<JClass> innerClasses = Sets.newHybridSet();
        hierarchy.getDirectInnerClassesOf(jclass).forEach(inner -> {
            innerClasses.add(inner);
            innerClasses.addAll(getAllInnerClassesOf(inner));
        });
        return innerClasses;
    }
}
