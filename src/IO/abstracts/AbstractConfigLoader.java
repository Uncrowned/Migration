package IO.abstracts;

import java.util.Map;

/**
 * Created by sevenbits on 01.02.16.
 */
public abstract class AbstractConfigLoader {
    public abstract Map<String, AbstractConfig> load() throws Exception;
}
