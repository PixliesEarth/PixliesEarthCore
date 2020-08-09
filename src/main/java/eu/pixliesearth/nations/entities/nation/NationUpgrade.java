package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum NationUpgrade {

    ;

    String displayName;
    Material icon;
    Era era;
    int cost;
    UpgradeType type;

    NationUpgrade(String displayName, Material icon, Era era, int cost, UpgradeType type) {
        this.displayName = displayName;
        this.icon = icon;
        this.era = era;
        this.cost = cost;
        this.type = type;
    }

    public enum UpgradeType {

        AGRICULTURE,
        MILITARY,
        INDUSTRY;

    }

}
