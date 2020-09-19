package eu.pixliesearth.core.customitems.ci.armour.wooden;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ArmourWoodenLeggings implements CustomItem {
    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.GOLDEN_LEGGINGS)
                .setDisplayName("§cWooden Leggings")
                .setCustomModelData(23)
                .setArmour(4)
                .build();
    }

    @Override
    public String getName() {
        return getItem().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        List<String> lore = getItem().getItemMeta().getLore();
        return lore;
    }

    @Override
    public ItemStack getStatic(int durability) {
        return getItem();
    }
    
    public ItemStack getStatic() {
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
