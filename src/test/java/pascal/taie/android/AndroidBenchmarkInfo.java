package pascal.taie.android;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import pascal.taie.util.collection.Maps;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public record AndroidBenchmarkInfo(String id,
                            String apk,
                            int expected) {
    @JsonCreator
    public AndroidBenchmarkInfo(
            @JsonProperty("id") String id,
            @JsonProperty("apk") String apk,
            @JsonProperty("expected") int expected) {
        this.id = id;
        this.apk = apk;
        this.expected = expected;
    }

    public static Map<String, AndroidBenchmarkInfo> load(String parentPath, String childPath) {
        File file = new File(parentPath, childPath);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<AndroidBenchmarkInfo>> typeRef = new TypeReference<>() {};
        try {
            Map<String, AndroidBenchmarkInfo> benchmarkInfos = Maps.newLinkedHashMap();
            mapper.readValue(file, typeRef).forEach(
                    bmInfo -> benchmarkInfos.put(bmInfo.id(), bmInfo));
            return benchmarkInfos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
