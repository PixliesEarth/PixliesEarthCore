package eu.pixliesearth.pixliefun.items.food;

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

public class FourLoko extends SimpleSlimefunItem<ItemConsumptionHandler> {

    public FourLoko() {
        super(PixlieFun.foodAndDrinks, PixlieFunItems.FOURLOKO, PixlieFun.DISTILLERY, new ItemStack[] {
                new ItemStack(Material.SUGAR), new ItemStack(Material.SUGAR_CANE), new ItemStack(Material.WHEAT),
                new ItemStack(Material.APPLE), null, null,
                null, null, null
        });
    }

    @Override
    public ItemConsumptionHandler getItemHandler() {
        return (e, p, item) -> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40 * 20, 0));
            p.sendMessage("§7§oAw man I feel dizzy...");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
            }, 80);
        };
    }

}
