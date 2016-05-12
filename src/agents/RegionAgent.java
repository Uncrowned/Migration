package agents;

import IO.data.Population;
import agents.abstracts.AbstractRegionAgent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class RegionAgent extends AbstractRegionAgent {
    private final static Logger log = LogManager.getLogger(AbstractRegionAgent.class);

    public RegionAgent(Map<String, Object> parameters, String name, List<Population> residents) {
        super(parameters, name, residents);
        stats.put("came", 0);
        stats.put("gone", 0);

        log.info(name + " set up.");
    }

    @Override
    public void enter() {
        stats.replace("came", (Integer) stats.get("came") + 1);
        stats.replace("now", (Integer) stats.get("now") + 1);
    }

    @Override
    public void leave() {
        stats.replace("gone", (Integer) stats.get("gone") + 1);
        stats.replace("now", (Integer) stats.get("now") - 1);
    }
}
