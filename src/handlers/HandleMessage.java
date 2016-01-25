package handlers;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public abstract class HandleMessage {

    public HandleMessage() {}

    public abstract void handle(Agent agent, ACLMessage message);
}
