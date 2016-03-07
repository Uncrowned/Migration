package handlers;

import agents.abstracts.AbstractRegionAgent;
import jade.lang.acl.ACLMessage;

public class EnterMessage extends HandleMessage<AbstractRegionAgent> {
    public static final String message = "ENTER";

    public EnterMessage() {
        super();
    }

    @Override
    public void handle(AbstractRegionAgent region, ACLMessage message) {
        region.enter(message.getSender());
    }
}
