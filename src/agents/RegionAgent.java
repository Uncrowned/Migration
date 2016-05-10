package agents;

import agents.abstracts.AbstractHumanAgent;
import agents.abstracts.AbstractRegionAgent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class RegionAgent extends AbstractRegionAgent {
    private final static Logger log = LogManager.getLogger(AbstractRegionAgent.class);

    public RegionAgent(Map<String, Object> parameters, String name) {
        super(parameters, name);
        stats = new HashMap<>();
        stats.put("came", 0);
        stats.put("gone", 0);
        stats.put("start", 0);
        stats.put("now", 0);

        log.info(name + " set up.");
    }

    @Override
    public void enter(String resident, AbstractHumanAgent agent) {
        residents.put(resident, agent);
        stats.replace("came", (Integer) stats.get("came") + 1);
        stats.replace("now", (Integer) stats.get("now") + 1);

        log.info(name + " came " + String.valueOf(stats.get("came")));
    }

    @Override
    public void leave(String resident) {
        residents.remove(resident);
        stats.replace("gone", (Integer) stats.get("gone") + 1);
        stats.replace("now", (Integer) stats.get("now") - 1);

        log.info(name + " leave " + String.valueOf(stats.get("gone")));
    }

    @Override
    public void setResidents(Map<String, AbstractHumanAgent> residents) {
        super.setResidents(residents);

        stats.put("start", residents.size());
        stats.put("now", residents.size());
    }
}
