package eu.pixliesearth.core.machines;

import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ConstIngredients {

    public static final ItemBuilder BRONZE_INGOT = new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§c§lBronze Ingot").setCustomModelData(10);
    public static final ItemBuilder MUD_BRICK = new ItemBuilder(Material.BRICK).setDisplayName("§6§lMud Brick").setGlow().setCustomModelData(12);

}
