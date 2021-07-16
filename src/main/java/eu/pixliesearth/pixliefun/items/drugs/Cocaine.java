package eu.pixliesearth.pixliefun.items.drugs;

import eu.pixliesearth.Main;
import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemConsumptionHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cocaine  extends SimpleSlimefunItem<ItemConsumptionHandler> {

    //TODO CHANGE RECIPE
    public Cocaine() {
        super(PixlieFun.pixlieFunCategory, PixlieFunItems.COCAINE, PixlieFun.DISTILLERY, new ItemStack[] {new ItemStack(Material.SUGAR_CANE), new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GOLD_NUGGET), new ItemStack(Material.SWEET_BERRIES), null, null, null, null, null});
    }

    @Override
    public ItemConsumptionHandler getItemHandler() {
        return (e, p, item) -> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 2));
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 5));
            }, 15 * 20);
            p.sendMessage("§7§oAaaah yes, what I need rightnow...");
        };
    }

}
