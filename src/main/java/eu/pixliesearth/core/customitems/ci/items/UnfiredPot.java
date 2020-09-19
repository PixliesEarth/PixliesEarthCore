package eu.pixliesearth.core.customitems.ci.items;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static org.bukkit.Material.BRICK;

public class UnfiredPot implements CustomItem {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(BRICK).setDisplayName("§cUnfired Pot").setCustomModelData(13).build();
    }

    @Override
    public String getName() {
        return getItem().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        return getItem().getLore();
    }

    @Override
    public ItemStack getStatic(int durability) {
        return getItem();
    }

    @Override
    public boolean enchantable() {
        return false;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {

    }

}
