package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomWeapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class ChristmasTreeSword extends CustomWeapon {

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public String getUUID() {
        return "Christmas:christmas_tree";
    }

    @Override
    public Integer getCustomModelData() {
        return 5;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§aChristmas §cSword";
    }

    @Override
    public boolean EntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player)
            event.getEntity().sendMessage("§cHo §aHo §cHo§7!");
        return false;
    }

}
