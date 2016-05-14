package IO;

import IO.abstracts.AbstractConfig;
import IO.abstracts.AbstractConfigLoader;
import IO.data.Population;
import IO.data.RegionConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by sevenbits on 01.02.16.
 */
public class RegionConfigLoader extends AbstractConfigLoader {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Map<String, AbstractConfig> load() throws IOException {
        Map<String, AbstractConfig> configs = new HashMap<>();

        Files.walk(Paths.get("src/resources/regions/")).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {
                    Map<String, Object> params = new HashMap<>();
                    List<Population> populations = new ArrayList<>();

                    JsonNode node = mapper.readValue(new File(filePath.toUri()), JsonNode.class);
                    params = mapper.readValue(node.get("params").toString(), new TypeReference<Map<String, Object>>(){});

                    Iterator it = node.get("populations").iterator();
                    while (it.hasNext()) {
                        populations.add(mapper.readValue(it.next().toString(), Population.class));
                    }

                    RegionConfig config = new RegionConfig(params, populations);
                    configs.put(params.get("name").toString(), config);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return configs;
    }
}
