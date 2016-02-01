package IO.abstracts;

import java.util.Map;

/**
 * Created by sevenbits on 01.02.16.
 */
public abstract class AbstractConfig {
    private Map<String, Object> params;

    public AbstractConfig(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }
}
