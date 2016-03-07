package handlers;

import agents.abstracts.AbstractRegionAgent;
import jade.lang.acl.ACLMessage;
import java.io.Serializable;

import java.io.IOException;

/**
 * Created by sevenbits on 07.03.16.
 */
public class ParamsRequestMessage extends HandleMessage<AbstractRegionAgent> {
    public static final String message = "GetParams";

    @Override
    public void handle(AbstractRegionAgent region, ACLMessage message) {
        try {
            ACLMessage response = new ACLMessage(ACLMessage.CONFIRM);
            response.setContentObject((Serializable) region.getParams());
            response.addReceiver(message.getSender());

            region.send(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
