package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.interfaces.IConsumable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChristmasCandyCane extends CustomItem implements IConsumable {

    @Override
    public Material getMaterial() {
        return Material.COOKIE;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§aCandy §cCane";
    }

    @Override
    public Integer getCustomModelData() {
        return 2;
    }

    @Override
    public String getUUID() {
        return "Christmas:Candy_Cane";
    }

    @Override
    public boolean PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 0));
        return false;
    }

}
