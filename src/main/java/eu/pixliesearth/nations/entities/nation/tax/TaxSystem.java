package eu.pixliesearth.nations.entities.nation.tax;

import lombok.Getter;

import java.util.Map;

@Getter
public class TaxSystem {

    private final boolean enabled;
    private final double percentage;

    public TaxSystem(Map<String, Object> map) {
        this.enabled = (boolean) map.get("enabled");
        this.percentage = (double) map.get("percentage");
    }

}
