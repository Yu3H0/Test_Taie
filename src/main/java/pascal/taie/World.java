

package pascal.taie;

import pascal.taie.android.info.ApkInfo;
import pascal.taie.config.Options;
import pascal.taie.frontend.cache.CachedIRBuilder;
import pascal.taie.ir.IRBuilder;
import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.natives.NativeModel;
import pascal.taie.language.type.TypeSystem;
import pascal.taie.util.AbstractResultHolder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Manages the whole-program information of the program being analyzed.
 * Note that the setters of this class are protected: they are supposed
 * to be called (once) by the world builder, not analysis classes.
 */
public final class World extends AbstractResultHolder
        implements Serializable {

    /**
     * ZA WARUDO, i.e., the current world.
     */
    private static World theWorld;

    /**
     * The callbacks that will be invoked at resetting.
     * This is useful to clear class-level caches.
     */
    private static final List<Runnable> resetCallbacks = new ArrayList<>();

    /**
     * Notes: This field is {@code transient} because it
     * should be set after deserialization.
     */
    private transient Options options;

    private TypeSystem typeSystem;

    private ClassHierarchy classHierarchy;

    /**
     * Notes: add {@code transient} to wrap this {@link IRBuilder} using
     * {@link pascal.taie.frontend.cache.CachedIRBuilder} in serialization.
     *
     * @see #writeObject(ObjectOutputStream)
     * @see #readObject(ObjectInputStream)
     */
    private transient IRBuilder irBuilder;

    private NativeModel nativeModel;

    private JMethod mainMethod;

    private Collection<JMethod> implicitEntries;

    private ApkInfo apkInfo;

    /**
     * Sets current world to {@code world}.
     */
    public static void set(World world) {
        theWorld = world;
    }

    /**
     * @return the current {@code World} instance.
     */
    public static World get() {
        return theWorld;
    }

    public static void registerResetCallback(Runnable callback) {
        resetCallbacks.add(callback);
    }

    public static void reset() {
        theWorld = null;
        resetCallbacks.forEach(Runnable::run);
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        checkAndSet("options", options);
    }

    public TypeSystem getTypeSystem() {
        return typeSystem;
    }

    public void setTypeSystem(TypeSystem typeSystem) {
        checkAndSet("typeSystem", typeSystem);
    }

    public ClassHierarchy getClassHierarchy() {
        return classHierarchy;
    }

    public void setClassHierarchy(ClassHierarchy classHierarchy) {
        checkAndSet("classHierarchy", classHierarchy);
    }

    public IRBuilder getIRBuilder() {
        return irBuilder;
    }

    public void setIRBuilder(IRBuilder irBuilder) {
        checkAndSet("irBuilder", irBuilder);
    }

    public NativeModel getNativeModel() {
        return nativeModel;
    }

    public void setNativeModel(NativeModel nativeModel) {
        checkAndSet("nativeModel", nativeModel);
    }

    public JMethod getMainMethod() {
        return mainMethod;
    }

    public void setMainMethod(JMethod mainMethod) {
        checkAndSet("mainMethod", mainMethod);
    }

    public Collection<JMethod> getImplicitEntries() {
        return implicitEntries;
    }

    public void setImplicitEntries(Collection<JMethod> implicitEntries) {
        checkAndSet("implicitEntries", implicitEntries);
    }

    public ApkInfo getApkInfo() {
        return apkInfo;
    }

    public void setApkInfo(ApkInfo apkInfo) {
        checkAndSet("apkInfo", apkInfo);
    }

    /**
     * Sets value for specified field (by {@code fieldName}).
     * Ensures that the specified field is set at most once.
     */
    private void checkAndSet(String fieldName, Object value) {
        try {
            Field field = World.class.getDeclaredField(fieldName);
            if (field.get(this) != null) {
                throw new IllegalStateException(
                        "World." + fieldName + " already set");
            }
            field.set(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set World." + fieldName);
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new CachedIRBuilder(irBuilder, classHierarchy));
    }

    @Serial
    private void readObject(ObjectInputStream s) throws IOException,
            ClassNotFoundException {
        s.defaultReadObject();
        setIRBuilder((IRBuilder) s.readObject());
    }
}
