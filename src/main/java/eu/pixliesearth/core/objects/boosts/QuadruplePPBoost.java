package eu.pixliesearth.core.objects.boosts;

import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.utils.Timer;

public class QuadruplePPBoost extends Boost {

    public QuadruplePPBoost() {
        super("QuadruplePPBoost", BoostType.QUADRUPLE_PP, new Timer(Timer.MINUTE * 10));
    }

}
