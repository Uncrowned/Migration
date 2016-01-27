package handlers;

import agents.abstracts.AbstractRegionAgent;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class EnterMessage extends HandleMessage {
    public static final String message = "ENTER";

    public EnterMessage() {
        super();
    }

    @Override
    public void handle(Agent agent, ACLMessage message) {
        AbstractRegionAgent region = (AbstractRegionAgent) agent;
        region.enter(message.getSender());
    }
}
