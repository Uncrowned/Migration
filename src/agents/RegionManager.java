package agents;

import agents.abstracts.AbstractRegionAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sevenbits on 10.05.16.
 */
public class RegionManager {
    public static Map<String, AbstractRegionAgent> regions = new HashMap<>(200);
}
