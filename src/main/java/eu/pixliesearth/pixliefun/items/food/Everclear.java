package eu.pixliesearth.pixliefun.items.food;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemConsumptionHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Everclear extends SimpleSlimefunItem<ItemConsumptionHandler> {

    public Everclear() {
        super(PixlieFun.foodAndDrinks, PixlieFunItems.EVERCLEAR, PixlieFun.DISTILLERY, new ItemStack[] {
                new ItemStack(Material.SUGAR), new ItemStack(Material.FERMENTED_SPIDER_EYE, 2), null,
                null, null, null,
                null, null, null
        });
    }

    @Override
    public ItemConsumptionHandler getItemHandler() {
        return (e, p, item) -> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 2));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15 * 20, 2));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15 * 20, 4));
            p.sendMessage("§7§oAw man I feel dizzy...");
        };
    }

}
