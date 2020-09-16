package eu.pixliesearth.core.customitems.ci.tools;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemExplosiveShovel implements CustomItem {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.DIAMOND_SHOVEL)
        .setDisplayName("§6Explosive Shovel")
        .addLoreLine("§7Era: tbd")
        .addLoreLine("§7Break a 3x3 area of blocks")
        .setCustomModelData(4)
        .build();

    }

    @Override
    public String getName() {
        return getItem().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        return getItem().getItemMeta().getLore();
    }

    @Override
    public ItemStack getStatic(int durability) {
        return getItem();
    }

    @Override
    public boolean enchantable() {
        return true;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {

    }

}
