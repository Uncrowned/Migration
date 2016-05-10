package agents.abstracts;

import agents.RegionManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public abstract class AbstractHumanAgent {

    private Map<String, Object> params;
    private AbstractRegionAgent currentRegion;
    private String name;

    private final static Logger log = LogManager.getLogger(AbstractHumanAgent.class);

    public AbstractHumanAgent(AbstractRegionAgent region, Map<String, Object> parameters) {
        currentRegion = region;
        params = parameters;

        name = UUID.randomUUID().toString();

        log.info(name + " set up");
    }

    protected Integer calcRelevance(Map<String, ?> params) {
        return (Integer) params.get("averageSalary") + (Integer)params.get("standardOfLiving");
    }

    protected Double calcMigration(Map<String, ?> params) {
        return (Integer.parseInt((String) params.get("age")) * 0.1) / 100;
    }

    public String getName() {
        return name;
    }

    public void migrate() {
        SortedMap<Integer, String> values = new TreeMap<>();

        RegionManager.regions.forEach((key, region) -> {
            //calc
            Integer value = calcRelevance(region.getParams());
            values.put(value, key);
        });

        String topRegion = values.get(values.lastKey());
        if (!topRegion.equals(currentRegion.getName())) {
            Double migration = calcMigration(this.params);

            currentRegion.leave(name);
            currentRegion = RegionManager.regions.get(topRegion);
            currentRegion.enter(name, this);

            log.info(name + " want migrate to " + currentRegion.getName() + " from " + currentRegion);
        }
    }

}
