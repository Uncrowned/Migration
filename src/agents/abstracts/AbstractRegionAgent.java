package agents.abstracts;

import jade.core.AID;
import jade.core.Agent;

import java.util.Map;

public abstract class AbstractRegionAgent extends Agent {
    public abstract void enter(AID resident);
    public abstract void leave(AID resident);

    public abstract Map<String, Object> getParams();
    public abstract Map<String, Object> getStats();

    public abstract void setResidents(Map<AID, AID> residents);
}
