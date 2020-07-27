package eu.pixliesearth.core.customitems.ci.resources;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BronzeIngot implements CustomItem {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.GOLD_INGOT).setGlow().setDisplayName("§6Bronze Ingot").build();
    }

    @Override
    public String getName() {
        return "§6Bronze Ingot";
    }

    @Override
    public List<String> getLore() {
        return new ArrayList<>();
    }

    @Override
    public ItemStack getStatic(int durability) {
        return new ItemBuilder(Material.GOLD_INGOT).setGlow().setDisplayName("§6Bronze Ingot").build();
    }

    @Override
    public boolean enchantable() {
        return false;
    }

}
