package agents;

import IO.HumanConfigLoader;
import IO.RegionConfigLoader;
import IO.abstracts.AbstractConfig;
import IO.data.RegionConfig;
import IO.abstracts.AbstractConfigLoader;
import IO.data.Population;

import agents.abstracts.AbstractRegionAgent;

import handlers.EnterMessage;
import handlers.HandleMessage;
import handlers.LeaveMessage;

import jade.core.AID;
import jade.core.Agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GodlikeAgent extends Agent {
    private Map<String, Class> humanAgents = new HashMap<>();
    private Map<String, AbstractConfig> humanConfigs;

    public GodlikeAgent() {
        AbstractConfigLoader loader = new HumanConfigLoader();
        this.humanConfigs = loader.load();
    }

    private Map<String, HandleMessage> getHandlers() {
        Map<String, HandleMessage> map = new HashMap<>(2);
        map.put(EnterMessage.message, new EnterMessage());
        map.put(LeaveMessage.message, new LeaveMessage());

        humanAgents.put("Common", CommonAgent.class);

        return map;
    }

    private void createPeoples(List<Population> population, AID region) {
        for (Population one : population) {
            for (long i = 0; i < one.getSize(); i++) {
                Class clazz = humanAgents.get(one.getType());

                clazz.getConstructor(Map.class, AID.class).newInstance()
            }
        }
    }

    protected void setup() {
        AbstractConfigLoader regionConfigLoader = new RegionConfigLoader();
        Map<String, AbstractConfig> configs = regionConfigLoader.load();
        Map<String, HandleMessage> handlers = getHandlers();

        //change types here
        configs.forEach(action -> {
            AbstractRegionAgent regionAgent = new RegionAgent(config.getParams(), handlers);
            createPeoples(((RegionConfig) config).getPopulation(), regionAgent.getAID());
        });

        //invoke migration for all "peoples"
    }
}
