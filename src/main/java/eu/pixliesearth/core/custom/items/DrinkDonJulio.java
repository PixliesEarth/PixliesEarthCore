package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.interfaces.IConsumable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DrinkDonJulio extends CustomItem implements IConsumable {

    @Override
    public boolean PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15 * 20, 0));
        player.sendMessage("§7§oAw man I feel dizzy...");
        return false;
    }

    @Override
    public Material getMaterial() {
        return Material.HONEY_BOTTLE;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Don Julio Tequila";
    }

    @Override
    public Integer getCustomModelData() {
        return 2;
    }

    @Override
    public String getUUID() {
        return "Pixlies:DonJulio";
    }

}
