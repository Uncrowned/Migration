package IO;

import IO.abstracts.AbstractConfig;
import IO.abstracts.AbstractConfigLoader;
import IO.data.HumanConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by sevenbits on 01.02.16.
 */
public class HumanConfigLoader extends AbstractConfigLoader {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Map<String, AbstractConfig> load() throws IOException {
        Map<String, AbstractConfig> configs = new HashMap<>();

        Files.walk(Paths.get("/home/sevenbits/projects/Migration/src/resources/humans/")).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {
                    Map<String, Object> params = new HashMap<>();

                    params = mapper.readValue(new File(filePath.toUri()), new TypeReference<Map<String, String>>(){});

                    HumanConfig config = new HumanConfig(params);
                    configs.put(params.get("type").toString(), config);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return configs;
    }
}
