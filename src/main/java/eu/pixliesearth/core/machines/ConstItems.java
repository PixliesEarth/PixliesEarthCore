package eu.pixliesearth.core.machines;

import eu.pixliesearth.utils.ItemBuilder;

import static org.bukkit.Material.*;

public class ConstItems {

    public static final ItemBuilder BRONZE_INGOT = new ItemBuilder(GOLD_INGOT).setDisplayName("§c§lBronze Ingot").setCustomModelData(10);
    public static final ItemBuilder MUD_BRICK = new ItemBuilder(BRICK).setDisplayName("§6§lMud Brick").setGlow().setCustomModelData(12);
    public static final ItemBuilder UNFIRED_POT = new ItemBuilder(BRICK).setDisplayName("§cUnfired Pot").setCustomModelData(13);
    public static final ItemBuilder TABLET = new ItemBuilder(PAPER).setDisplayName("§6§lTablet").setCustomModelData(14);
    public static final ItemBuilder CLAY_DAGGER = new ItemBuilder(WOODEN_SWORD).setDisplayName("§cClay Dagger").setCustomModelData(15).setAttackDamage(3);
    
}
