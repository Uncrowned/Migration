package handlers;

import agents.RegionAgent;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class LeaveMessage extends HandleMessage {
    public static final String message = "LEAVE";

    public LeaveMessage() {
        super();
    }

    @Override
    public void handle(Agent agent, ACLMessage message) {
        RegionAgent region = (RegionAgent) agent;
        region.exit(message.getSender());
    }
}
