package eu.pixliesearth.pixliefun.items;

import eu.pixliesearth.utils.ItemBuilder;
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

}
