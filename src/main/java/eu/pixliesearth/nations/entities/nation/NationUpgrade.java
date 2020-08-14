package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public enum NationUpgrade {

    ;

    String displayName;
    Material icon;
    Era era;
    int cost;
    UpgradeType type;
    BiConsumer<Nation, Player> execute;

    NationUpgrade(String displayName, Material icon, Era era, int cost, UpgradeType type, BiConsumer<Nation, Player> execute) {
        this.displayName = displayName;
        this.icon = icon;
        this.era = era;
        this.cost = cost;
        this.type = type;
        this.execute = execute;
    }

    public enum UpgradeType {

        AGRICULTURE,
        MILITARY,
        INDUSTRY;

    }

}
