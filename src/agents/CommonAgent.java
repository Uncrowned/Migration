package agents;

import handlers.EnterMessage;
import handlers.LeaveMessage;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.*;

public class CommonAgent extends Agent {

    private Map<String, Object> params;
    private String currentRegion;

    private Map<AID, Map<String, ?>> regionsParams = new HashMap<>();

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
                        confirm(message.getSender().getName());
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

                ACLMessage confirm = receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
                if (confirm != null) {
                    try {
                        Map<String, ?> params = (Map<String, ?>) confirm.getContentObject();
                        regionsParams.put(confirm.getSender(), params);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.out.println(getName() + " set up.");
    }

    public void confirm(String region) {
        currentRegion = region;

        System.out.println("agent " + this.getName() + " migrated to " + currentRegion);
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

    protected Integer calcRelevance(Map<String, ?> params) {
        return (Integer) params.get("averageSalary") + (Integer)params.get("standardOfLiving");
    }

    protected Double calcMigration(Map<String, ?> params) {
        return (Integer.parseInt((String) params.get("age")) * 0.1) / 100;
    }

    public void migrate() {
        SortedMap<Integer, AID> values = new TreeMap<>();

        regionsParams.forEach((key, map) -> {
            //calc
            Integer value = calcRelevance(map);
            values.put(value, key);
        });

        AID topRegion = values.get(values.lastKey());
        if (!topRegion.getName().equals(currentRegion)) {
            Double migration = calcMigration(this.params);

            ACLMessage enter = new ACLMessage(ACLMessage.REQUEST);
            enter.setContent(EnterMessage.message);
            enter.addReceiver(topRegion);

            send(enter);

            ACLMessage leave = new ACLMessage(ACLMessage.REQUEST);
            leave.setContent(LeaveMessage.message);
            leave.addReceiver(new AID(currentRegion, true));

            send(leave);

            System.out.println("agent " + this.getName() + " want migrate to " + topRegion.getName() + " from " + currentRegion);
        }
    }

}
