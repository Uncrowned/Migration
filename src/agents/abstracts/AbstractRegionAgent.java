package agents.abstracts;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRegionAgent {
    protected Map<String, Object> params;
    protected Map<String, Object> stats;
    protected String name;
    protected Map<String, AbstractHumanAgent> residents = new HashMap<>(0);

    public AbstractRegionAgent(Map<String, Object> parameters, String name) {
        this.params = parameters;
        this.name = name;
    }

    public abstract void enter(String resident, AbstractHumanAgent agent);
    public abstract void leave(String resident);

    public Map<String, Object> getParams() {
        return params;
    }

    public Map<String, Object> getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public void setResidents(Map<String, AbstractHumanAgent> residents) {
        this.residents = residents;
    }
}
