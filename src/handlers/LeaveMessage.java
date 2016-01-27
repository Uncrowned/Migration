package handlers;

import agents.abstracts.AbstractRegionAgent;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class LeaveMessage extends HandleMessage {
    public static final String message = "LEAVE";

    public LeaveMessage() {
        super();
    }

    @Override
    public void handle(Agent agent, ACLMessage message) {
        AbstractRegionAgent region = (AbstractRegionAgent) agent;
        region.leave(message.getSender());
    }
}
