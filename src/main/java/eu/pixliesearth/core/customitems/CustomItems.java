package eu.pixliesearth.core.customitems;

import eu.pixliesearth.core.customcrafting.CustomRecipe;
import eu.pixliesearth.core.customcrafting.CustomRecipeBuilder;
import eu.pixliesearth.core.customitems.ci.tools.ItemExplosivePick;
import eu.pixliesearth.core.customitems.ci.tools.ItemExplosiveShovel;
import eu.pixliesearth.core.customitems.ci.weapons.reach.ItemSlingshot;
import eu.pixliesearth.core.customitems.ci.weapons.melee.ItemStoneHatchet;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum CustomItems {

    SLINGSHOT(new ItemSlingshot(), null),
    EXPLOSIVE_PICKAXE(new ItemExplosivePick(), new CustomRecipeBuilder().setFirstSlot(new ItemStack(Material.TNT)).setSecondSlot(new ItemStack(Material.TNT)).setThirdSlot(new ItemStack(Material.TNT)).setFifthSlot(new ItemStack(Material.STICK)).setEighthSlot(new ItemStack(Material.STICK)).setResult(new ItemExplosivePick().getItem()).build()),
    EXPLOSIVE_SHOVEL(new ItemExplosiveShovel(), new CustomRecipeBuilder().setSecondSlot(new ItemStack(Material.TNT)).setFifthSlot(new ItemStack(Material.STICK)).setEighthSlot(new ItemStack(Material.STICK)).setResult(new ItemExplosiveShovel().getItem()).build()),
    STONE_HATCHET(new ItemStoneHatchet(), null);

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