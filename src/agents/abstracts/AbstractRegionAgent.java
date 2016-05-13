package agents.abstracts;

import IO.data.Population;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRegionAgent {
    protected Map<String, Object> params;
    protected Map<String, Object> stats = new HashMap<>();
    protected String name;
    protected List<Population> residents;

    public AbstractRegionAgent(Map<String, Object> parameters, String name, List<Population> residents) {
        this.params = parameters;
        this.name = name;
        this.residents = residents;

        Integer start = residents.stream().mapToInt(p -> p.size).sum();
        stats.put("start", start);
        stats.put("now", start);
    }

    public abstract void enter();
    public abstract void leave();

    public Map<String, Object> getParams() {
        return params;
    }

    public Map<String, Object> getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public List<Population> getResidents() {
        return residents;
    }

    public void resetStats() {
        stats.replace("now", stats.get("start"));
    }
}
