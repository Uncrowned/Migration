package agents.people;

import agents.abstracts.AbstractHumanAgent;

import java.util.Map;

/**
 * Created by sevenbits on 13.05.16.
 */
public class SouthAgent extends AbstractHumanAgent {
    public SouthAgent(Map<String, Object> parameters) {
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
