package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.localization.Lang;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

@Getter
public enum NationUpgrade {

    ONE_MORE_SETTLEMENT("§b§lMore settlements", Material.MAGENTA_BED, Era.ANCIENT, 5, (nation, player) -> {
        if (Integer.parseInt(nation.getExtras().get("settlements").toString()) == 3) {
            nation.setXpPoints(5 + nation.getXpPoints());
            nation.save();
            player.sendMessage(Lang.NATION + "§7You have reached the maximum settlements you can purchase.");
            return;
        }
        nation.getExtras().put("settlements", Integer.parseInt(nation.getExtras().get("settlements").toString()) + 1);
        nation.save();
    })
    ;

    String displayName;
    Material icon;
    Era era;
    double cost;
    BiConsumer<Nation, Player> execute;

    NationUpgrade(String displayName, Material icon, Era era, double cost, BiConsumer<Nation, Player> execute) {
        this.displayName = displayName;
        this.icon = icon;
        this.era = era;
        this.cost = cost;
        this.execute = execute;
    }

}
