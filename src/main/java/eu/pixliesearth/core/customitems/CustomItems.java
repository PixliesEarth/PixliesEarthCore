package eu.pixliesearth.core.customitems;

import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.customitems.ci.armour.ArmourBronzeBoots;
import eu.pixliesearth.core.customitems.ci.armour.ArmourBronzeChestPlate;
import eu.pixliesearth.core.customitems.ci.armour.ArmourBronzeHelmet;
import eu.pixliesearth.core.customitems.ci.armour.ArmourBronzeLeggings;
import eu.pixliesearth.core.customitems.ci.items.BronzeIngot;
import eu.pixliesearth.core.customitems.ci.machines.BronzeForgeCI;
import eu.pixliesearth.core.customitems.ci.machines.InputNodeCI;
import eu.pixliesearth.core.customitems.ci.machines.KilnCI;
import eu.pixliesearth.core.customitems.ci.machines.MachineCrafterCI;
import eu.pixliesearth.core.customitems.ci.machines.OutputNodeCI;
import eu.pixliesearth.core.customitems.ci.machines.PotteryCI;
import eu.pixliesearth.core.customitems.ci.machines.TinkerTableCI;
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
    BRONZE_INGOT(new BronzeIngot()),
    TINKER_TABLE(new TinkerTableCI()),
    KILN(new KilnCI()),
    POTTERY(new PotteryCI()),
    BRONZE_FORGE(new BronzeForgeCI()),
    INPUT_NODE(new InputNodeCI()),
    OUTPUT_NODE(new OutputNodeCI()),
    MACHINE_CRAFTER(new MachineCrafterCI()),
    BRONZE_HELMET(new ArmourBronzeHelmet()),
    BRONZE_CHESTPLATE(new ArmourBronzeChestPlate()),
    BRONZE_LEGGINGS(new ArmourBronzeLeggings()),
    BRONZE_BOOTS(new ArmourBronzeBoots()),
    ;

    public CustomItem clazz;

    CustomItems(CustomItem clazz) { // CustomItem clazz, CustomRecipe recipe
        this.clazz = clazz;
    }
    
    public ItemStack getItem() {
    	return clazz.getItem();
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
