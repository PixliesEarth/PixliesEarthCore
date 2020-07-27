package eu.pixliesearth.core.customitems;

import eu.pixliesearth.core.customcrafting.CustomRecipe;
import eu.pixliesearth.core.customitems.ci.resources.BronzeIngot;
import eu.pixliesearth.core.customitems.ci.tools.ItemExplosivePick;
import eu.pixliesearth.core.customitems.ci.tools.ItemExplosiveShovel;
import eu.pixliesearth.core.customitems.ci.weapons.reach.ItemSlingshot;
import eu.pixliesearth.core.customitems.ci.weapons.melee.ItemStoneHatchet;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum CustomItems {

    SLINGSHOT(new ItemSlingshot(), null),
    EXPLOSIVE_PICKAXE(new ItemExplosivePick(), new CustomRecipe(new ItemStack(Material.TNT), new ItemStack(Material.TNT), new ItemStack(Material.TNT), null, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null, new ItemExplosivePick().getItem())),
    EXPLOSIVE_SHOVEL(new ItemExplosiveShovel(), new CustomRecipe(null, new ItemStack(Material.TNT), null, null, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null, new ItemExplosiveShovel().getItem())),
    STONE_HATCHET(new ItemStoneHatchet(), null),
    BRONZE_INGOT(new BronzeIngot(), new CustomRecipe(new ItemStack(Material.GOLD_BLOCK), new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.IRON_INGOT), null, new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.IRON_INGOT), new ItemStack(Material.IRON_INGOT), new ItemStack(Material.IRON_BLOCK), new BronzeIngot().getItem()));

    public CustomItem clazz;
    public CustomRecipe recipe;

    CustomItems(CustomItem clazz, CustomRecipe recipe) {
        this.clazz = clazz;
        this.recipe = recipe;
    }

    public static boolean contains(String test) {
        for (CustomItems c : values()) {
            if (c.name().equals(test))
                return true;
        }
        return false;
    }

}