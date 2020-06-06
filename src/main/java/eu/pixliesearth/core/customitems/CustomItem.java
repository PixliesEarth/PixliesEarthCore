package eu.pixliesearth.core.customitems;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface CustomItem {

    public ItemStack getRecipe();

    public String getName();

    public List<String> getLore();

    public ItemStack getStatic(int durabilty);

}
