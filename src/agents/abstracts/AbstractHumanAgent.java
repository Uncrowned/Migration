package agents.abstracts;

import agents.RegionManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map.Entry;
import java.util.*;

public abstract class AbstractHumanAgent {

    protected static Random rnd = new Random();
    protected NavigableMap<Double, String> regionRating = new TreeMap<>();
    protected Double ratingSum = 0.0;

    private Map<String, Object> params;

    private final static Logger log = LogManager.getLogger(AbstractHumanAgent.class);

    public AbstractHumanAgent(Map<String, Object> parameters) {
        params = parameters;

        Iterator<Entry<String, AbstractRegionAgent>> it = RegionManager.regions.entrySet().iterator();
        while (it.hasNext()) {
            AbstractRegionAgent region = it.next().getValue();
            Double value = calcRelevance(region.getParams());

            regionRating.put(ratingSum, region.getName());
            ratingSum += value;
        }

        log.info(this.getClass().toString() + " set up");
    }

    protected abstract Double calcRelevance(Map<String, ?> params);

    protected abstract Double calcMigration(Map<String, ?> params);

    public void migrate(AbstractRegionAgent currentRegion) {
        String topRegion = regionRating.floorEntry(ratingSum * rnd.nextDouble()).getValue();
        if (!topRegion.equals(currentRegion.getName())) {
            Double p = calcMigration(this.params);
            Double next = rnd.nextDouble();
            if (p >= next) {
                RegionManager.regions.get(topRegion).enter();
                currentRegion.leave();

                StringBuilder builder = new StringBuilder();
                builder.append(topRegion).append(" to ").append(currentRegion.getName()).append(" ").
                        append(String.valueOf(p)).append(" ").append(next);

                log.info(builder.toString());

                return;
            }
        }

        log.info("does not want to migrate from " + currentRegion.getName());
    }
}
