package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.localization.Lang;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

@Getter
public enum NationUpgrade {

    TWO_MORE_SETTLEMENTS("§b§l+2 settlements", Material.MAGENTA_BED, Era.ANCIENT, 5, (nation, player) -> {
        nation.getExtras().putIfAbsent("settlements", 3);
        nation.getExtras().put("settlements", (Integer) nation.getExtras().get("settlements") + 2);
        nation.save();
    }, true),
    ;

    String displayName;
    Material icon;
    Era era;
    double cost;
    BiConsumer<Nation, Player> execute;
    boolean multiplePurchasable;

    NationUpgrade(String displayName, Material icon, Era era, double cost, BiConsumer<Nation, Player> execute, boolean multiplePurchasable) {
        this.displayName = displayName;
        this.icon = icon;
        this.era = era;
        this.cost = cost;
        this.execute = execute;
        this.multiplePurchasable = multiplePurchasable;
    }

}
