package agents;

import agents.abstracts.AbstractRegionAgent;
import handlers.HandleMessage;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.HashMap;
import java.util.Map;

public class RegionAgent extends AbstractRegionAgent {

    private Map<String, Object> stats;
    private Map<AID, AID> residents = new HashMap<>(0);

    protected void setup() {
        try {
            ServiceDescription sd = new ServiceDescription();
            sd.setType("Region");

            Map<String, Object> params = (Map<String, Object>) getArguments()[0];
            sd.setName((String) params.get("name"));

            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            dfd.addServices(sd);
            DFService.register(this, dfd);

            addBehaviour(new CyclicBehaviour(this) {
                public void action() {
                    ACLMessage message = receive();

                    if (message != null) {
                        Map<String, HandleMessage> messages = (Map<String, HandleMessage>) getArguments()[1];
                        if (messages.containsKey(message.getContent())) {
                            messages.get(message.getContent()).handle(this.getAgent(), message);
                        }
                    } else {
                        block();
                    }
                }
            });

            System.out.println(getName() + " set up.");
        } catch (Exception e) {
            System.out.println("Saw exception in RegionAgent: " + e);
            e.printStackTrace();
        }
    }

    public RegionAgent() {
        stats = new HashMap<>();
        stats.put("came", 0);
        stats.put("gone", 0);
    }

    @Override
    public void enter(AID resident) {
        residents.put(resident, resident);

        ACLMessage message = createMessage(resident, "OK");
        send(message);

        stats.replace("came", (Integer) stats.get("came") + 1);

        System.out.println(getName() + " came " + String.valueOf(stats.get("came")));
    }

    @Override
    public void leave(AID resident) {
        residents.remove(resident);
        stats.replace("gone", (Integer) stats.get("gone") + 1);

        System.out.println(getName() + " leave " + String.valueOf(stats.get("gone")));
    }

    private ACLMessage createMessage(AID receiver, String content) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setContent(content);
        message.addReceiver(receiver);

        return message;
    }

    @Override
    public Map<String, Object> getParams() {
        return (Map<String, Object>) getArguments()[0];
    }

    @Override
    public Map<String, Object> getStats() {
        return stats;
    }

    @Override
    public void setResidents(Map<AID, AID> residents) {
        this.residents = residents;
    }

}
