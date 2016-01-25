package handlers;

import agents.RegionAgent;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class EnterMessage extends HandleMessage {
    public static final String message = "ENTER";

    public EnterMessage() {
        super();
    }

    @Override
    public void handle(Agent agent, ACLMessage message) {
        RegionAgent region = (RegionAgent) agent;
        region.enter(message.getSender());
    }
}
