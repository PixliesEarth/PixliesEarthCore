package eu.pixliesearth.core.objects;

import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Boost {

    private String name;
    private BoostType boostType;
    private Timer timer;

    public enum BoostType {

        DOUBLE_EXP,
        DOUBLE_DROP,
        QUADRUPLE_PP

    }

}
