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
        return ((Double) params.get("averageSalary"))/5.0 + (Double)params.get("populace")
                + (Double)params.get("pension")/5.0 - (Double)params.get("unemployment")
                - (Double)params.get("consumerBasket")/100.0 + (Double)params.get("lifespan")/10.0;
    }

    @Override
    protected Double calcMigration(Map<String, ?> params) {
        return Double.valueOf(params.get("value").toString());
    }
}
