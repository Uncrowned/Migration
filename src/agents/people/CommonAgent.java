package agents.people;

import agents.abstracts.AbstractHumanAgent;
import agents.abstracts.AbstractRegionAgent;

import java.util.Map;

/**
 * Created by sevenbits on 09.04.16.
 */
public class CommonAgent extends AbstractHumanAgent {
    public CommonAgent(Map<String, Object> parameters) {
        super(parameters);
    }

    @Override
    protected Double calcRelevance(Map<String, ?> params) {
        return (double)((Integer) params.get("averageSalary") + (Integer)params.get("standardOfLiving"));
    }

    @Override
    protected Double calcMigration(Map<String, ?> params) {
        return Double.valueOf(params.get("value").toString());
    }
}
