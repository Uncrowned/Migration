package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Map;

public class CommonAgent extends Agent {

    private Map<String, Object> params;
    private AID currentRegion;

    public CommonAgent(Map<String, Object> params, AID region) {
        this.params = params;
        this.currentRegion = region;
    }

    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage message = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

                if (message != null) {
                    if (message.getContent().equals("OK")) {
                        currentRegion = message.getSender();
                    }
                } else {
                    block();
                }
            }
        });
    }

    public void migrate() {
        try {
            ServiceDescription sd = new ServiceDescription();
            sd.setType("Region");

            DFAgentDescription dfd = new DFAgentDescription();
            dfd.addServices(sd);

            DFAgentDescription[] descriptions = DFService.search(this, dfd);

            //we have to check that dfd contains the agent which we looking for
        } catch(Exception e) {
            System.out.println( "Saw exception in CommonAgent: " + e );
            e.printStackTrace();
        }
    }

}
