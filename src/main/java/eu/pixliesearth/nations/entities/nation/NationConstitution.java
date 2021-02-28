package eu.pixliesearth.nations.entities.nation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class NationConstitution {

    /*
     * Values:
     * 0 - Forbidden
     * 1 - Restricted
     * 2 - Allowed
     */
    private final @Getter Map<String, Integer> entries = new HashMap<>(){{
        put("test", 2);
    }};

}
