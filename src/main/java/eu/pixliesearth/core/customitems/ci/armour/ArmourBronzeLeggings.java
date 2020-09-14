package eu.pixliesearth.core.customitems.ci.armour;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ArmourBronzeLeggings implements CustomItem {
    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.GOLDEN_LEGGINGS)
                .setDisplayName("§6Bronze Leggings")
                .setCustomModelData(18)
                .setArmour(5)
                .setArmourToughness(1) // This makes it better than iron!
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
