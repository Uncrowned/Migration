package agents;

import IO.HumanConfigLoader;
import IO.RegionConfigLoader;
import IO.abstracts.AbstractConfig;
import IO.data.RegionConfig;
import IO.abstracts.AbstractConfigLoader;
import IO.data.Population;

import handlers.EnterMessage;
import handlers.HandleMessage;
import handlers.LeaveMessage;

import handlers.ParamsRequestMessage;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GodlikeAgent extends Agent {
    private Map<String, AbstractConfig> humanConfigs;
    private List<AID> humanAgents = new ArrayList<>();

    public GodlikeAgent() throws Exception {
        AbstractConfigLoader loader = new HumanConfigLoader();
        humanConfigs = loader.load();
    }

    private Map<String, HandleMessage> getHandlers() {
        Map<String, HandleMessage> map = new HashMap<>(2);
        map.put(EnterMessage.message, new EnterMessage());
        map.put(LeaveMessage.message, new LeaveMessage());
        map.put(ParamsRequestMessage.message, new ParamsRequestMessage());

        return map;
    }

    private void createPeoples(List<Population> population, String regionName) {
        for (Population one : population) {
            for (long i = 0; i < one.size; i++) {
                PlatformController container = getContainerController();
                try {
                    AgentController human = container.createNewAgent("human" + i + regionName,
                            humanConfigs.get(one.type).getParams().get("type").toString(), new Object[] {
                        humanConfigs.get(one.type).getParams(),
                        regionName
                    });

                    human.start();

                    humanAgents.add(new AID(human.getName(), AID.ISGUID));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void setup() {
        AbstractConfigLoader regionConfigLoader = new RegionConfigLoader();
        Map<String, AbstractConfig> configs = null;
        try {
            configs = regionConfigLoader.load();
            Map<String, HandleMessage> handlers = getHandlers();
            PlatformController container = getContainerController();

            configs.forEach((key, config) -> {
                try {
                    AgentController region = container.createNewAgent(config.getParams().get("name").toString(),
                            RegionAgent.class.getName(), new Object[] {config.getParams(), handlers});

                    region.start();

                    createPeoples(((RegionConfig) config).getPopulation(), region.getName());
                } catch (ControllerException e) {
                    e.printStackTrace();
                }
            });

            addBehaviour(new CyclicBehaviour(this) {
                public void action() {
                    ACLMessage message = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

                    if (message != null) {
                        if (message.getContent().equals("start")) {
                            godsWill();
                        }
                        if (message.getContent().equals("preparation")) {
                            harvestInfo();
                        }
                    } else {
                        block();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void godsWill() {
        for (AID agent : humanAgents) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.setContent("Migrate");
            message.addReceiver(agent);

            send(message);
        }
    }

    private void harvestInfo() {
        for (AID agent : humanAgents) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.setContent("Harvest info");
            message.addReceiver(agent);

            send(message);
        }
    }
}
