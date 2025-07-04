

package pascal.taie.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.FileReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.jar.Manifest;

/**
 * A utility class for logging runtime information about the environment and the Tai-e application.
 * This includes details about the Java environment and versioning information for the Tai-e application.
 */
public class RuntimeInfoLogger {

    private static final Logger logger = LogManager.getLogger(RuntimeInfoLogger.class);

    private static final String[] ENV_KEYS = {
            "java.version",
            "java.version.date",
            "java.runtime.version",
            "java.vendor",
            "java.vendor.version",
            "os.name",
            "os.version",
            "os.arch",
    };

    private static final String VERSION_MANIFEST_KEY = "Tai-e-Version";

    private static final String COMMIT_MANIFEST_KEY = "Tai-e-Commit";

    private static final String VERSION_PROPERTY_KEY = "projectVersion";

    private static final String UNKNOWN = "Unknown";

    /**
     * Logs runtime information, including environment details and Tai-e version/commit information.
     */
    public static void logRuntimeInfo() {
        logEnvInfo();
        logTaieInfo();
    }

    /**
     * Logs environment information based on predefined system properties.
     */
    private static void logEnvInfo() {
        for (String key : ENV_KEYS) {
            String value = System.getProperty(key);
            logger.info("{}: {}", key, value);
        }
    }

    /**
     * Logs Tai-e version and commit information by attempting to read from the manifest file
     * or fallback methods if the manifest is not available.
     */
    private static void logTaieInfo() {
        Manifest manifest = getManifest();
        String version = manifest != null ? readVersionFromManifest(manifest)
                : readVersionFromGradleProperties();
        logger.info("Tai-e Version: {}", version);
        String commit = manifest != null ? readCommitFromManifest(manifest)
                : readCommitFromDotGit();
        logger.info("Tai-e Commit: {}", commit);
    }

    /**
     * Reads the Tai-e version from the provided manifest.
     *
     * @param manifest the manifest to read from
     * @return the Tai-e version, or {@code "Unknown"} if not found
     */
    private static String readVersionFromManifest(Manifest manifest) {
        String version = manifest.getMainAttributes().getValue(VERSION_MANIFEST_KEY);
        if (version == null) {
            logger.warn("Manifest does not contain Tai-e version information");
            return UNKNOWN;
        }
        return version;
    }

    /**
     * Reads the Tai-e version from the gradle.properties file.
     *
     * @return the Tai-e version, or {@code "Unknown"} if an error occurs
     */
    private static String readVersionFromGradleProperties() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(Path.of("gradle.properties").toFile()));
            return properties.getProperty(VERSION_PROPERTY_KEY);
        } catch (Exception e) {
            logger.warn("Failed to read version from 'gradle.properties': {}", e.toString());
        }
        return UNKNOWN;
    }

    /**
     * Reads the Tai-e commit hash from the provided manifest.
     *
     * @param manifest the manifest to read from
     * @return the Tai-e commit hash, or {@code "Unknown"} if not found
     */
    private static String readCommitFromManifest(Manifest manifest) {
        String commit = manifest.getMainAttributes().getValue(COMMIT_MANIFEST_KEY);
        if (commit == null) {
            logger.warn("Manifest does not contain Tai-e commit information");
            return UNKNOWN;
        }
        return commit;
    }

    /**
     * Reads the current git commit hash from the .git directory.
     *
     * @return the current git commit hash, or {@code "Unknown"} if an error occurs
     */
    private static String readCommitFromDotGit() {
        try {
            String gitHead = Files.readString(Path.of(".git", "HEAD"));
            if (gitHead.startsWith("ref: ")) {
                return Files.readString(Path.of(".git", gitHead.substring(5).trim())).trim();
            } else {
                return gitHead.trim();
            }
        } catch (Exception e) {
            logger.warn("Failed to read Git commit hash: {}", e.toString());
        }
        return UNKNOWN;
    }

    /**
     * Retrieves the manifest of the current JAR file, if available.
     *
     * @return the manifest, or {@code null} if an error occurs or the manifest is not found
     */
    @Nullable
    private static Manifest getManifest() {
        try {
            URL url = RuntimeInfoLogger.class.getProtectionDomain().getCodeSource().getLocation();
            if (url.getPath().endsWith(".jar")) {
                var jarConnection = (JarURLConnection) new URL("jar:" + url + "!/")
                        .openConnection();
                return jarConnection.getManifest();
            }
        } catch (Exception e) {
            logger.warn("Failed to read manifest: {}", e.toString());
        }
        return null;
    }

}
