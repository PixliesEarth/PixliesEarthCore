package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.localization.Lang;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

@Getter
public enum NationUpgrade {

    TWO_MORE_SETTLEMENTS("§b§l+2 settlements", Material.CAMPFIRE, Era.ANCIENT, 5, (nation, player) -> {
        try {
            nation.getExtras().putIfAbsent("settlements", 3.0);
            nation.getExtras().put("settlements", (Double) nation.getExtras().get("settlements") + 2.0);
            nation.save();
        } catch (Exception e ) {
            nation.getExtras().put("settlements", 5.0);
        }
    }, true),
    DOUBLE_PP("§b§lDouble Political-Power", Material.EXPERIENCE_BOTTLE, Era.MEDIEVAL, 20, (nation, player) -> { }, false),
    FIFTY_CLAIMS("§b§l50 More Claims", Material.BARREL, Era.ANCIENT, 20, (nation, player) -> {
        nation.boostClaims(50);
        nation.save();
    }, true),
    SIX_MORE_ALLIES("§b§l6 More Allies", Material.PLAYER_HEAD, Era.ANCIENT, 15, (nation, player) -> { }, false),
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

    public boolean has(Nation nation) {
        return nation.getUpgrades().contains(name());
    }

}
