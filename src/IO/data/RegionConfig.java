package IO.data;

import IO.abstracts.AbstractConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by sevenbits on 01.02.16.
 */
public class RegionConfig extends AbstractConfig {

    private List<Population> populations;

    public RegionConfig(Map<String, Object> params, List<Population> populations) {
        super(params);
        this.populations = populations;
    }

    public List<Population> getPopulation() {
        return populations;
    }
}
