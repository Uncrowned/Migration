package handlers;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public abstract class HandleMessage<T extends Agent> {

    public HandleMessage() {}

    public abstract void handle(T agent, ACLMessage message);
}
