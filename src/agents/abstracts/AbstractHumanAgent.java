package agents.abstracts;

import agents.RegionManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public abstract class AbstractHumanAgent {

    protected static Random rnd = new Random();

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

    protected abstract Integer calcRelevance(Map<String, ?> params);

    protected abstract Double calcMigration(Map<String, ?> params);

    public String getName() {
        return name;
    }

    public void migrate() {
        SortedMap<Integer, String> values = new TreeMap<>();

        RegionManager.regions.forEach((key, region) -> {
            Integer value = calcRelevance(region.getParams());
            values.put(value, key);
        });

        String topRegion = values.get(values.lastKey());
        if (!topRegion.equals(currentRegion.getName())) {
            Double p = calcMigration(this.params);
            if (p <= rnd.nextDouble()) {
                currentRegion.leave(name);
                currentRegion = RegionManager.regions.get(topRegion);
                currentRegion.enter(name, this);

                StringBuilder builder = new StringBuilder(name);
                builder.append(" want migrate to ").append(currentRegion.getName()).
                        append(" from " ).append(currentRegion.getName()).append(" ").
                        append(String.valueOf(p));

                log.info(builder.toString());
            } else {
                log.info(name + "does not want to migrate " + String.valueOf(p));
            }
        }
    }

}
