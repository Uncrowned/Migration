import IO.HumanConfigLoader;
import IO.RegionConfigLoader;
import IO.abstracts.AbstractConfig;
import IO.abstracts.AbstractConfigLoader;
import IO.data.Population;
import IO.data.RegionConfig;
import agents.RegionAgent;
import agents.RegionManager;
import agents.abstracts.AbstractHumanAgent;
import agents.abstracts.AbstractRegionAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by sevenbits on 10.05.16.
 */
public class Main {
    private final static Logger log = LogManager.getLogger(Main.class);
    private final static Map<String, AbstractHumanAgent> humanObjects = new HashMap<>();

    private static void createPeoples(List<Population> population) throws Exception {
        AbstractConfigLoader loader = new HumanConfigLoader();
        Map<String, AbstractConfig> humanConfigs = loader.load();

        for (Population one : population) {
            if (!humanObjects.containsKey(one.type)) {
                Class clazz = Class.forName(humanConfigs.get(one.type).getParams().get("type").toString());
                AbstractHumanAgent human = (AbstractHumanAgent) clazz.getConstructor(Map.class)
                        .newInstance(humanConfigs.get(one.type).getParams());

                humanObjects.put(one.type, human);
            }
        }
    }

    public static void main(String[] args) {
        try {
            AbstractConfigLoader regionConfigLoader = new RegionConfigLoader();
            Map<String, AbstractConfig> configs = regionConfigLoader.load();
            configs.forEach((key, config) -> {
                try {
                    AbstractRegionAgent region = RegionAgent.class.getConstructor(Map.class, String.class, List.class).newInstance(config.getParams(),
                            config.getParams().get("name").toString(), ((RegionConfig) config).getPopulation());
                    RegionManager.regions.put(region.getName(), region);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            });

            configs.forEach((key, config) -> {
                try {
                    createPeoples(((RegionConfig) config).getPopulation());
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            });

            String command = "next";
            Scanner scanner = new Scanner(System.in);
            while (!command.equals("exit")) {
                command = scanner.next();

                if (command.equals("migrate")) {
                    log.info("Migration started!");

                    RegionManager.regions.forEach((name, region) -> {
                        region.getResidents().forEach(p -> {
                            for (long i = 0; i < p.size; i++) {
                                humanObjects.get(p.type).migrate(region);
                            }
                        });
                    });

                    log.info("Migration finished!");
                }

                if (command.equals("stats")) {
                    RegionManager.regions.forEach((name, region) -> {
                        log.info(region.getName() + " " + region.getStats().toString());
                    });
                }

                if (command.equals("reset")) {
                    RegionManager.regions.forEach((name, region) -> region.resetStats());
                    log.info("Statistic was reset.");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
