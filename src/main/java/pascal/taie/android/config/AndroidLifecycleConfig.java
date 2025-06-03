package pascal.taie.android.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import pascal.taie.config.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Configuration for an AndroidLifecycle.
 */
public record AndroidLifecycleConfig(@JsonProperty String className,
                                     @JsonProperty List<String> callbackMethodSubSigs) {

    private static final String CONFIG = "android-lifecycle.yml";

    /**
     * Used by deserialization from configuration file.
     */
    @JsonCreator
    public AndroidLifecycleConfig(
            @JsonProperty("className") String className,
            @JsonProperty("callbackMethodSubSigs") List<String> callbackMethodSubSigs) {
        this.className = className;
        this.callbackMethodSubSigs = callbackMethodSubSigs;
    }

    @Override
    public String toString() {
        return "AndroidLifecycleConfig{" +
                "className='" + className + '\'' +
                ", callbackMethodSubSigs='" + callbackMethodSubSigs + '\'' +
                '}';
    }

    /**
     * Parses a list of AndroidLifecycleConfig from given input stream.
     */
    public static List<AndroidLifecycleConfig> loadAndroidLifecycleConfig() {
        InputStream content = AndroidLifecycleConfig.class
                .getClassLoader()
                .getResourceAsStream(CONFIG);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        JavaType type = mapper.getTypeFactory()
                .constructCollectionType(List.class, AndroidLifecycleConfig.class);
        try {
            return mapper.readValue(content, type);
        } catch (IOException e) {
            throw new ConfigException("Failed to read AndroidLifecycle config file", e);
        }
    }

}
