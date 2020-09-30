package eu.pixliesearth.core.customitems.ci.tools;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemScythe implements CustomItem {
    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.WOODEN_HOE)
                .setDisplayName("§6Scythe")
                .addLoreLine("§7Increases the chance of getting seeds when breaking grass to 100%")
                .setCustomModelData(24)
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

    @Override
    public boolean enchantable() {
        return true;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
    	
    }

}
