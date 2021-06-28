package eu.pixliesearth.pixliefun.items;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.utils.ItemBuilder;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemConsumptionHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DonJulioTequilla extends SimpleSlimefunItem<ItemConsumptionHandler> {

    public static final SlimefunItemStack item = new SlimefunItemStack("DON_JULIO_TEQUILA", new ItemBuilder(Material.HONEY_BOTTLE)
            .setDisplayName("§6Don Julio Tequila")
            .setCustomModelData(2)
            .build());

    public DonJulioTequilla() {
        super(PixlieFun.pixlieFunCategory, item, PixlieFun.DISTILLERY, new ItemStack[] {new ItemStack(Material.SUGAR_CANE), new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GOLD_NUGGET), new ItemStack(Material.SWEET_BERRIES), null, null, null, null, null});
    }

    @Override
    public ItemConsumptionHandler getItemHandler() {
        return (e, p, item) -> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15 * 20, 0));
            p.sendMessage("§7§oAw man I feel dizzy...");
        };
    }

}