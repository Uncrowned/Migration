package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

public class CommonAgent extends Agent {

    private Map<String, Object> params;
    private String currentRegion;

    private Map<AID, Map<String, Object>> regionsParams = new HashMap<>();

    public CommonAgent() {
    }

    protected void setup() {
        currentRegion = (String) getArguments()[1];
        params = (Map<String, Object>) getArguments()[0];

        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage message = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

                if (message != null) {
                    if (message.getContent().equals("OK")) {
                        currentRegion = message.getSender().getName();
                    }
                    if (message.getContent().equals("Migrate")) {
                        migrate();
                    }
                    if (message.getContent().equals("Harvest info")) {
                        harvest();
                    }
                } else {
                    block();
                }

                message = receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
                if (message != null) {
                    try {
                        Serializable params = message.getContentObject();

                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.out.println(getName() + " set up.");
    }

    public void harvest() {
        try {
            ServiceDescription sd = new ServiceDescription();
            sd.setType("Region");

            DFAgentDescription dfd = new DFAgentDescription();
            dfd.addServices(sd);

            DFAgentDescription[] descriptions = DFService.search(this, dfd);

            for (DFAgentDescription description : descriptions) {
                ACLMessage response = new ACLMessage(ACLMessage.REQUEST);
                response.setContent("GetParams");
                response.addReceiver(description.getName());

                send(response);
            }
        } catch(Exception e) {
            System.out.println( "Saw exception in CommonAgent: " + e );
            e.printStackTrace();
        }
    }

    public void migrate() {

    }

}
