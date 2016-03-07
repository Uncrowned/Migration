package handlers;

import agents.abstracts.AbstractRegionAgent;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class LeaveMessage extends HandleMessage<AbstractRegionAgent> {
    public static final String message = "LEAVE";

    public LeaveMessage() {
        super();
    }

    @Override
    public void handle(AbstractRegionAgent region, ACLMessage message) {
        region.leave(message.getSender());
    }
}
