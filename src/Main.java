import IO.HumanConfigLoader;
import IO.RegionConfigLoader;
import IO.abstracts.AbstractConfig;
import IO.abstracts.AbstractConfigLoader;
import IO.data.Population;
import IO.data.RegionConfig;
import agents.PeopleManager;
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

    private static void createPeoples(List<Population> population, AbstractRegionAgent region) throws Exception {
        AbstractConfigLoader loader = new HumanConfigLoader();
        Map<String, AbstractConfig> humanConfigs = loader.load();
        Map<String, AbstractHumanAgent> residents = new HashMap<>();

        for (Population one : population) {
            Class clazz = Class.forName(humanConfigs.get(one.type).getParams().get("type").toString());
            for (long i = 0; i < one.size; i++) {
                AbstractHumanAgent human = (AbstractHumanAgent) clazz.getConstructor(AbstractRegionAgent.class, Map.class)
                        .newInstance(region, humanConfigs.get(one.type).getParams());

                residents.put(human.getName(), human);
                PeopleManager.people.put(human.getName(), human);
            }
        }

        region.setResidents(residents);
    }

    public static void main(String[] args) {
        AbstractConfigLoader regionConfigLoader = new RegionConfigLoader();
        Map<String, AbstractConfig> configs = null;
        try {
            configs = regionConfigLoader.load();
            configs.forEach((key, config) -> {
                try {
                    AbstractRegionAgent region = RegionAgent.class.getConstructor(Map.class, String.class).newInstance(config.getParams(),
                            config.getParams().get("name").toString());
                    RegionManager.regions.put(region.getName(), region);

                    createPeoples(((RegionConfig) config).getPopulation(), region);
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
                    PeopleManager.people.forEach((name, agent) -> {
                        agent.migrate();
                    });
                }

                if (command.equals("stats")) {
                    RegionManager.regions.forEach((name, region) -> {
                        log.info(region.getName() + " " + region.getStats().toString());
                    });
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
