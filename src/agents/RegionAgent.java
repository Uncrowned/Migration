package agents;

import handlers.HandleMessage;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.Map;

public class RegionAgent extends Agent {

    private Map<String, HandleMessage> messages;
    private Map<String, Object> params;
    private Map<AID, AID> residents;

    private long agentsCame = 0;
    private long agentsGone = 0;

    protected void setup() {
        try {
            System.out.println(getLocalName() + " setting up");

            ServiceDescription sd = new ServiceDescription();
            sd.setType("Region");
            sd.setName((String) params.get("name"));

            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            dfd.addServices(sd);
            DFService.register(this, dfd);

            addBehaviour(new CyclicBehaviour(this) {
                public void action() {
                    ACLMessage message = receive();

                    if (message != null) {
                        if (messages.containsKey(message.getContent())) {
                            messages.get(message.getContent()).handle(this.getAgent(), message);
                        }
                    } else {
                        block();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Saw exception in RegionAgent: " + e);
            e.printStackTrace();
        }
    }

    public RegionAgent(Map<String, Object> params, Map<AID, AID> residents, Map<String, HandleMessage> messages) {
        this.params = params;
        this.residents = residents;
        this.messages = messages;
    }

    public void enter(AID resident) {
        residents.put(resident, resident);

        ACLMessage message = createMessage(resident, "OK");
        send(message);

        agentsCame++;
    }

    public void exit(AID resident) {
        residents.remove(resident);
        agentsGone++;
    }

    private ACLMessage createMessage(AID reciever, String content) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setContent(content);
        message.addReceiver(reciever);

        return message;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public long getAgentsCame() {
        return agentsCame;
    }

    public long getAgentsGone() {
        return agentsGone;
    }
}
