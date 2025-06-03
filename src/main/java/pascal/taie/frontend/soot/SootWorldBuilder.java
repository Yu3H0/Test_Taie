

package pascal.taie.frontend.soot;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pascal.taie.AbstractWorldBuilder;
import pascal.taie.World;
import pascal.taie.analysis.pta.PointerAnalysis;
import pascal.taie.analysis.pta.plugin.reflection.LogItem;
import pascal.taie.android.info.ApkInfo;
import pascal.taie.android.info.RawApkInfo;
import pascal.taie.android.util.LayoutFileParser;
import pascal.taie.config.AnalysisConfig;
import pascal.taie.config.Options;
import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.classes.ClassHierarchyImpl;
import pascal.taie.language.classes.StringReps;
import pascal.taie.language.type.TypeSystem;
import pascal.taie.language.type.TypeSystemImpl;
import soot.AndroidPlatformException;
import soot.G;
import soot.ModulePathSourceLocator;
import soot.PackManager;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootResolver;
import soot.Transform;
import soot.jimple.infoflow.android.manifest.ProcessManifest;
import soot.jimple.infoflow.android.resources.ARSCFileParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static soot.SootClass.HIERARCHY;

public class SootWorldBuilder extends AbstractWorldBuilder {

    private static final Logger logger = LogManager.getLogger(SootWorldBuilder.class);

    /**
     * Path to the file which specifies the basic classes that should be
     * added to Scene in advance.
     */
    private static final String BASIC_CLASSES = "basic-classes.yml";

    private static final String ANDROID_PLATFORMS = "android-benchmarks/android-platforms";

    @Override
    public void build(Options options, List<AnalysisConfig> analyses) {
        initSoot(options, analyses, this);
        if (options.isAndroidMode()) {
            buildForAndroid(options);
        } else {
            buildForJava(options);
        }
    }

    private static void initSoot(Options options, List<AnalysisConfig> analyses,
                                 SootWorldBuilder builder) {
        // reset Soot
        G.reset();

        // set Soot options
        soot.options.Options.v().set_output_dir(
                new File(options.getOutputDir(), "sootOutput").toString());
        soot.options.Options.v().set_output_format(
                soot.options.Options.output_format_jimple);
        soot.options.Options.v().set_keep_line_number(true);
        soot.options.Options.v().set_app(true);
        // exclude jdk classes from application classes
        soot.options.Options.v().set_exclude(List.of("jdk.*", "apple.laf.*"));
        soot.options.Options.v().set_whole_program(true);
        soot.options.Options.v().set_no_writeout_body_releasing(true);
        soot.options.Options.v().set_search_dex_in_archives(true);
        soot.options.Options.v().setPhaseOption("jb", "preserve-source-annotations:true");
        soot.options.Options.v().setPhaseOption("jb", "model-lambdametafactory:false");
        soot.options.Options.v().setPhaseOption("cg", "enabled:false");
        if (options.isPrependJVM()) {
            // TODO: figure out why -prepend-classpath makes Soot faster
            soot.options.Options.v().set_prepend_classpath(true);
        }
        if (options.isAllowPhantom()) {
            soot.options.Options.v().set_allow_phantom_refs(true);
        }
        if (options.isPreBuildIR()) {
            // we need to set this option to false when pre-building IRs,
            // otherwise Soot throws RuntimeException saying
            // "No method source set for method ...".
            // TODO: figure out the reason of "No method source"
            soot.options.Options.v().set_drop_bodies_after_load(false);
        }

        Scene scene = G.v().soot_Scene();
        addBasicClasses(scene);
        addReflectionLogClasses(analyses, scene);


        // Configure Soot transformer
        Transform transform = new Transform(
                "wjtp.tai-e", new SceneTransformer() {
            @Override
            protected void internalTransform(String phaseName, Map<String, String> opts) {
                builder.build(options, Scene.v());
            }
        });
        PackManager.v()
                .getPack("wjtp")
                .add(transform);
    }

    /**
     * Reads basic classes specified by file {@link #BASIC_CLASSES} and
     * adds them to {@code scene}.
     */
    private static void addBasicClasses(Scene scene) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        JavaType type = mapper.getTypeFactory()
                .constructCollectionType(List.class, String.class);
        try {
            InputStream content = SootWorldBuilder.class
                    .getClassLoader()
                    .getResourceAsStream(BASIC_CLASSES);
            List<String> classNames = mapper.readValue(content, type);
            classNames.forEach(name -> scene.addBasicClass(name, HIERARCHY));
        } catch (IOException e) {
            throw new SootFrontendException("Failed to read Soot basic classes", e);
        }
    }

    /**
     * Add classes in reflection log to the scene.
     * Tai-e's ClassHierarchy depends on Soot's Scene, which does not change
     * after hierarchy's construction, thus we need to add the classes
     * in the reflection log before starting Soot.
     * <p>
     * TODO: this is a tentative solution. We should remove it and use other
     *  way to load basic classes in the reflection log, so that world builder
     *  does not depend on analyses to be executed.
     *
     * @param analyses the analyses to be executed
     * @param scene    the Soot's scene
     */
    private static void addReflectionLogClasses(List<AnalysisConfig> analyses, Scene scene) {
        analyses.forEach(config -> {
            if (config.getId().equals(PointerAnalysis.ID)) {
                String path = config.getOptions().getString("reflection-log");
                if (path != null) {
                    LogItem.load(path).forEach(item -> {
                        // add target class
                        String target = item.target;
                        String targetClass;
                        if (target.startsWith("<")) {
                            targetClass = StringReps.getClassNameOf(target);
                        } else {
                            targetClass = target;
                        }
                        if (StringReps.isArrayType(targetClass)) {
                            targetClass = StringReps.getBaseTypeNameOf(target);
                        }
                        scene.addBasicClass(targetClass);
                    });
                }
            }
        });
    }

    private void build(Options options, Scene scene) {
        World.reset();
        World world = new World();
        World.set(world);

        world.setOptions(options);
        // initialize class hierarchy
        ClassHierarchy hierarchy = new ClassHierarchyImpl();
        SootClassLoader loader = new SootClassLoader(
                scene, hierarchy, options.isAllowPhantom());
        hierarchy.setDefaultClassLoader(loader);
        hierarchy.setBootstrapClassLoader(loader);
        world.setClassHierarchy(hierarchy);
        // initialize type manager
        TypeSystem typeSystem = new TypeSystemImpl(hierarchy);
        world.setTypeSystem(typeSystem);
        // initialize converter
        Converter converter = new Converter(loader, typeSystem);
        loader.setConverter(converter);
        // build classes in hierarchy
        buildClasses(hierarchy, scene);
        // set main method
        if (options.getMainClass() != null) {
            if (scene.hasMainClass()) {
                world.setMainMethod(
                        converter.convertMethod(scene.getMainMethod()));
            } else {
                logger.warn("Warning: main class '{}'" +
                                " does not have main(String[]) method!",
                        options.getMainClass());
            }
        } else {
            logger.warn("Warning: main class was not given!");
        }
        // set implicit entries
        world.setImplicitEntries(implicitEntries.stream()
                .map(hierarchy::getJREMethod)
                // some implicit entries may not exist in certain JDK version,
                // thus we filter out null
                .filter(Objects::nonNull)
                .toList());

        // initialize IR builder
        world.setNativeModel(getNativeModel(typeSystem, hierarchy, options));
        IRBuilder irBuilder = new IRBuilder(converter);
        world.setIRBuilder(irBuilder);
        if (options.isPreBuildIR()) {
            irBuilder.buildAll(hierarchy);
        }

        // set apkInfo
        if (options.isAndroidMode()) {
            world.setApkInfo(getApkInfo(options, hierarchy));
        }
    }

    protected static void buildClasses(ClassHierarchy hierarchy, Scene scene) {
        // TODO: parallelize?
        new ArrayList<>(scene.getClasses()).forEach(c ->
                hierarchy.getDefaultClassLoader().loadClass(c.getName()));
    }

    private static ApkInfo getApkInfo(Options options, ClassHierarchy hierarchy) {
        RawApkInfo rawApkInfo = getRawApkInfo(options);
        ApkInfo.ApkInfoConverter apkInfoConverter = new ApkInfo.ApkInfoConverter(rawApkInfo, hierarchy);
        return new ApkInfo(apkInfoConverter.convertApplication(),
                apkInfoConverter.convertExportedActivities(),
                apkInfoConverter.convertExportedServices(),
                apkInfoConverter.convertExportedBroadcastReceivers(),
                apkInfoConverter.convertExportedContentProviders(),
                apkInfoConverter.convertEnabledActivities(),
                apkInfoConverter.convertEnabledServices(),
                apkInfoConverter.convertEnabledBroadcastReceivers(),
                apkInfoConverter.convertEnabledContentProviders(),
                apkInfoConverter.convertLayoutCallbacks(),
                apkInfoConverter.convertLayoutFragments(),
                apkInfoConverter.convertLayoutViews(),
                apkInfoConverter.convertAndroidCallbacks(),
                apkInfoConverter.convertComponentFilterInfo(),
                rawApkInfo);
    }

    private static RawApkInfo getRawApkInfo(Options options) {
        try {
            String apkPath = options.getClassPath().get(0);
            ARSCFileParser resources = new ARSCFileParser();
            resources.parse(apkPath);
            ProcessManifest manifest = new ProcessManifest(new File(apkPath), resources);
            LayoutFileParser layoutFile = new LayoutFileParser(manifest.getPackageName(), apkPath, resources);
            return new RawApkInfo(manifest, layoutFile, resources);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void buildForJava(Options options) {
        // set arguments and run soot
        List<String> args = new ArrayList<>();
        // set class path
        Collections.addAll(args, "-cp", getClassPath(options));
        // set main class
        String mainClass = options.getMainClass();
        if (mainClass != null) {
            Collections.addAll(args, "-main-class", mainClass, mainClass);
        }
        // add input classes
        args.addAll(getInputClasses(options));
        try {
            soot.Main.v().run(args.toArray(new String[0]));
        } catch (SootResolver.SootClassNotFoundException e) {
            throw new RuntimeException(e.getMessage()
                    .replace("is your soot-class-path set",
                            "are your class path and class name given"));
        } catch (AssertionError e) {
            if (e.getStackTrace()[0].toString()
                    .startsWith("soot.SootResolver.resolveClass")) {
                throw new RuntimeException("Exception thrown by class resolver," +
                        " are your class path and class name given properly?", e);
            }
            throw e;
        } catch (Exception e) {
            if (e.getStackTrace()[0].getClassName().startsWith("soot.JastAdd")) {
                throw new RuntimeException("""
                        Soot frontend failed to parse input Java source file(s).
                        This exception may be caused by:
                        1. syntax or semantic errors in the source code. In this case, please fix the errors.
                        2. language features introduced by Java 8+ in the source code.
                           In this case, you could either compile the source code to bytecode (*.class)
                           or rewrite the code by using old features.""", e);
            }
            throw e;
        }
    }

    private static void buildForAndroid(Options options) {
        soot.options.Options.v().set_src_prec(soot.options.Options.src_prec_apk);
        soot.options.Options.v().set_process_multiple_dex(true);
        List<String> args = new ArrayList<>();
        // set Java version for Android
        String apkPath = options.getClassPath().get(0);
        int javaVersion = options.getJavaVersion();
        // set android JDK path
        String androidJDKPath = (javaVersion >= 9)
                ? ModulePathSourceLocator.DUMMY_CLASSPATH_JDK9_FS
                : getAndroidJDKPath(options);
        Collections.addAll(args, "-cp", androidJDKPath);
        // set android platforms path
        Collections.addAll(args, "-android-jars", options.getAndroidJars());
        // set apk path
        Collections.addAll(args, "-process-dir", apkPath);
        try {
            // soot.Main.v().run(args) will call retrieveAllBodies()
            // in PackManager.v().runPacks(); and make world building
            // phase very slow, thus, we build Soot Scene manually
            soot.options.Options.v().parse(args.toArray(new String[0]));
            Scene.v().loadNecessaryClasses();
            // run SootWorldBuilder
            PackManager.v().getPack("wjtp").apply();
        } catch (AndroidPlatformException e) {
            throw new RuntimeException("Exception thrown by android platform," +
                    " are your androidJars path given properly?", e);
        } catch (Exception e) {
            throw new RuntimeException("Soot frontend failed to build Scene" +
                    " in Android mode", e);
        }
    }
}
