package eu.pixliesearth.pixliefun.items;

import eu.pixliesearth.guns.guns.M1911;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PixlieFunItems {

    // CANT INSTANTIATE THIS CLASS
    private PixlieFunItems() { }

    public static final SlimefunItemStack DONJULIO_TEQUILA = new SlimefunItemStack("DON_JULIO_TEQUILA", new ItemBuilder(Material.HONEY_BOTTLE).setDisplayName("§6Don Julio Tequila").setCustomModelData(2).build());
    public static final SlimefunItemStack BEER = new SlimefunItemStack("BEER", new ItemBuilder(Material.HONEY_BOTTLE).setDisplayName("§6Beer").setCustomModelData(5).build());
    public static final SlimefunItemStack WINE = new SlimefunItemStack("WINE_PIXLIES", new ItemBuilder(Material.HONEY_BOTTLE).setDisplayName("§cWine").setCustomModelData(4).build());
    public static final SlimefunItemStack RUM = new SlimefunItemStack("RUM", new ItemBuilder(Material.HONEY_BOTTLE).setDisplayName("§dRum").setCustomModelData(3).build());

    public static final SlimefunItemStack RIFLE_BARREL = new SlimefunItemStack("RIFLE_BARREL", new ItemBuilder(Material.BRICK).setDisplayName("§6Rifle Barrel").setCustomModelData(11).build());
    public static final SlimefunItemStack RIFLE_RECEIVER = new SlimefunItemStack("RIFLE_RECEIVER", new ItemBuilder(Material.BRICK).setDisplayName("§6Rifle Receiver").setCustomModelData(8).build());
    public static final SlimefunItemStack RIFLE_STOCK = new SlimefunItemStack("RIFLE_STOCK", new ItemBuilder(Material.BRICK).setDisplayName("§6Rifle Stock").setCustomModelData(10).build());
    public static final SlimefunItemStack RIFLE_AMMO = new SlimefunItemStack("RIFLE_AMMO", new ItemBuilder(Material.STICK).setDisplayName("§aRifle Ammo").setCustomModelData(5).build());

    public static final SlimefunItemStack PISTOL_BARREL = new SlimefunItemStack("PISTOL_BARREL", Material.BRICK, "§aPistol Barrel");
    public static final SlimefunItemStack PISTOL_RECEIVER = new SlimefunItemStack("PISTOL_RECEIVER", Material.BRICK, "§cPistol Receiver");
    public static final SlimefunItemStack NINEMM = new SlimefunItemStack("9MM", Material.STICK, "§39mm Ammo");

    public static final SlimefunItemStack AK47 = new SlimefunItemStack("AK-47", Material.CARROT_ON_A_STICK, "§c§lAK-47", "§7Ammo: §3" + eu.pixliesearth.guns.guns.AK47.getAmmoType().name(), "§7Origin: §3" + eu.pixliesearth.guns.guns.AK47.getOrigin(), "§7Range: §3" + eu.pixliesearth.guns.guns.AK47.getRange(), "§7Accuracy: §3" + eu.pixliesearth.guns.guns.AK47.getAccuracy());
    public static final SlimefunItemStack M1911 = new SlimefunItemStack("M1911", Material.CARROT_ON_A_STICK, "§c§lM1911", "§7Ammo: §3" + eu.pixliesearth.guns.guns.M1911.getAmmoType().name(), "§7Origin: §3" + eu.pixliesearth.guns.guns.M1911.getOrigin(), "§7Range: §3" + eu.pixliesearth.guns.guns.M1911.getRange(), "§7Accuracy: §3" + eu.pixliesearth.guns.guns.M1911.getAccuracy());

    public static final SlimefunItemStack COCAINE = new SlimefunItemStack("COCAINE", new ItemBuilder(Material.SUGAR).setDisplayName("§f§lCocaine").build());

    public static final SlimefunItemStack DISTILLERY =  new SlimefunItemStack("DISTILLERY", new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/48437fee749cbd68f3443bb06e45058a687c0fca96436cadb538cad59b5216c6")).setDisplayName("§eDistillery").build());
    public static final SlimefunItemStack GUN_WORKBENCH = new SlimefunItemStack("GUN_WORKBENCH", new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/80a4334f6a61e40c0c63deb665fa7b581e6eb259f7a3207ced7a1ff8bdc8a9f9")).setDisplayName("§7Gun Workbench").build());

}
