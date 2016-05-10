package agents;

import agents.abstracts.AbstractHumanAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sevenbits on 10.05.16.
 */
public class PeopleManager {
    public static Map<String, AbstractHumanAgent> people = new HashMap<>(1000);
}
