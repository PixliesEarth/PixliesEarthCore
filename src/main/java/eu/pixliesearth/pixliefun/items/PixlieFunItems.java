package eu.pixliesearth.pixliefun.items;

import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

public class PixlieFunItems {

    public static final SlimefunItemStack DONJULIO_TEQUILA = new SlimefunItemStack("DON_JULIO_TEQUILA", new ItemBuilder(Material.HONEY_BOTTLE).setDisplayName("§6Don Julio Tequila").setCustomModelData(2).build());

    public static final SlimefunItemStack RIFLE_BARREL = new SlimefunItemStack("RIFLE_BARREL", new ItemBuilder(Material.BRICK).setDisplayName("§6Rifle Barrel").setCustomModelData(11).build());
    public static final SlimefunItemStack RIFLE_RECEIVER = new SlimefunItemStack("RIFLE_RECEIVER", new ItemBuilder(Material.BRICK).setDisplayName("§6Rifle Receiver").setCustomModelData(8).build());
    public static final SlimefunItemStack RIFLE_STOCK = new SlimefunItemStack("RIFLE_STOCK", new ItemBuilder(Material.BRICK).setDisplayName("§6Rifle Stock").setCustomModelData(10).build());
    public static final SlimefunItemStack RIFLE_AMMO = new SlimefunItemStack("RIFLE_AMMO", new ItemBuilder(Material.STICK).setDisplayName("§aRifle Ammo").setCustomModelData(5).build());

    public static final SlimefunItemStack COCAINE = new SlimefunItemStack("COCAINE", new ItemBuilder(Material.SUGAR).setDisplayName("§f§lCocaine").build());

    public static final SlimefunItemStack DISTILLERY =  new SlimefunItemStack("DISTILLERY", new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/48437fee749cbd68f3443bb06e45058a687c0fca96436cadb538cad59b5216c6")).setDisplayName("§eDistillery").build());
    public static final SlimefunItemStack GUN_WORKBENCH = new SlimefunItemStack("GUN_WORKBENCH", new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/80a4334f6a61e40c0c63deb665fa7b581e6eb259f7a3207ced7a1ff8bdc8a9f9")).setDisplayName("§7Gun Workbench").build());

}
