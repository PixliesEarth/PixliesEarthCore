package eu.pixliesearth.core.customitems;

import eu.pixliesearth.core.customcrafting.CustomRecipe;
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
    BRONZE_SWORD(new ItemBronzeSword(), null),
    BRONZE_INGOT(new BronzeIngot(), null),
    ;

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

    public static CustomItems getByItemStack(ItemStack item) {
        for (CustomItems ci : values())
            if (item.isSimilar(ci.clazz.getItem()))
                return ci;
        return null;
    }

}
