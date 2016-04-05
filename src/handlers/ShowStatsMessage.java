package handlers;

import agents.abstracts.AbstractRegionAgent;
import jade.lang.acl.ACLMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Created by sevenbits on 05.04.16.
 */
public class ShowStatsMessage extends HandleMessage<AbstractRegionAgent> {
    public static final String message = "SHOW_STATS";
    private final static Logger log = LogManager.getLogger(AbstractRegionAgent.class);

    @Override
    public void handle(AbstractRegionAgent agent, ACLMessage message) {
        Map<String, ?> stats = agent.getStats();

        log.info(stats.toString());
    }
}
