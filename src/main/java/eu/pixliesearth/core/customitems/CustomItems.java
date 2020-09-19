package eu.pixliesearth.core.customitems;

import eu.pixliesearth.core.customitems.ci.armour.bronze.ArmourBronzeBoots;
import eu.pixliesearth.core.customitems.ci.armour.bronze.ArmourBronzeChestPlate;
import eu.pixliesearth.core.customitems.ci.armour.bronze.ArmourBronzeHelmet;
import eu.pixliesearth.core.customitems.ci.armour.bronze.ArmourBronzeLeggings;
import eu.pixliesearth.core.customitems.ci.armour.wooden.ArmourWoodenBoots;
import eu.pixliesearth.core.customitems.ci.armour.wooden.ArmourWoodenChestPlate;
import eu.pixliesearth.core.customitems.ci.armour.wooden.ArmourWoodenHelmet;
import eu.pixliesearth.core.customitems.ci.armour.wooden.ArmourWoodenLeggings;
import eu.pixliesearth.core.customitems.ci.items.BronzeIngot;
import eu.pixliesearth.core.customitems.ci.items.MudBrick;
import eu.pixliesearth.core.customitems.ci.items.Tablet;
import eu.pixliesearth.core.customitems.ci.items.UnfiredPot;
import eu.pixliesearth.core.customitems.ci.weapons.melee.ItemClayDagger;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.customitems.ci.tools.ItemExplosivePick;
import eu.pixliesearth.core.customitems.ci.tools.ItemExplosiveShovel;
import eu.pixliesearth.core.customitems.ci.weapons.melee.ItemBronzeSword;
import eu.pixliesearth.core.customitems.ci.weapons.melee.ItemStoneHatchet;
import eu.pixliesearth.core.customitems.ci.weapons.reach.ItemSlingshot;

public enum CustomItems {

    SLINGSHOT(new ItemSlingshot()),
    EXPLOSIVE_PICKAXE(new ItemExplosivePick()),
    EXPLOSIVE_SHOVEL(new ItemExplosiveShovel()),
    STONE_HATCHET(new ItemStoneHatchet()),
    BRONZE_SWORD(new ItemBronzeSword()),
    BRONZE_HELMET(new ArmourBronzeHelmet()),
    BRONZE_CHESTPLATE(new ArmourBronzeChestPlate()),
    BRONZE_LEGGINGS(new ArmourBronzeLeggings()),
    BRONZE_BOOTS(new ArmourBronzeBoots()),
    WOODEN_BOOTS(new ArmourWoodenBoots()),
    WOODEN_CHESTPLATE(new ArmourWoodenChestPlate()),
    WOODEN_HELMET(new ArmourWoodenHelmet()),
    WOODEN_LEGGINGS(new ArmourWoodenLeggings()),
    BRONZE_INGOT(new BronzeIngot()),
    MUD_BRICK(new MudBrick()),
    TABLET(new Tablet()),
    UNFIRED_POT(new UnfiredPot()),
    CLAY_DAGGER(new ItemClayDagger()),
    ;

    public CustomItem clazz;

    CustomItems(CustomItem clazz) { // CustomItem clazz, CustomRecipe recipe
        this.clazz = clazz;
    }
    
    public ItemStack getItem() {
    	return clazz.getItem().clone();
    }

    public ItemBuilder getBuilder() { return new ItemBuilder(getItem()); }

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
