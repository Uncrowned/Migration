package agents;

import agents.abstracts.AbstractAgent;

import java.util.Map;

/**
 * Created by sevenbits on 09.04.16.
 */
public class CommonAgent extends AbstractAgent {
    @Override
    protected Integer calcRelevance(Map<String, ?> params) {
        return super.calcRelevance(params);
    }

    @Override
    protected Double calcMigration(Map<String, ?> params) {
        return super.calcMigration(params);
    }
}
