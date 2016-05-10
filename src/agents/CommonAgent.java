package agents;

import agents.abstracts.AbstractHumanAgent;
import agents.abstracts.AbstractRegionAgent;

import java.util.Map;

/**
 * Created by sevenbits on 09.04.16.
 */
public class CommonAgent extends AbstractHumanAgent {
    public CommonAgent(AbstractRegionAgent region, Map<String, Object> parameters) {
        super(region, parameters);
    }

    @Override
    protected Integer calcRelevance(Map<String, ?> params) {
        return super.calcRelevance(params);
    }

    @Override
    protected Double calcMigration(Map<String, ?> params) {
        return super.calcMigration(params);
    }
}
