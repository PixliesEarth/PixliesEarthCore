package eu.pixliesearth.pixliefun.items.food;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemConsumptionHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Vodka extends SimpleSlimefunItem<ItemConsumptionHandler> {

    public Vodka() {
        super(PixlieFun.foodAndDrinks, PixlieFunItems.VODKA, PixlieFun.DISTILLERY, new ItemStack[] {new ItemStack(Material.POTATO), new ItemStack(Material.WHEAT_SEEDS), new ItemStack(Material.POTATO), null, null, null, null, null, null});
    }

    @Override
    public ItemConsumptionHandler getItemHandler() {
        return (e, p, item) -> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40 * 20, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40 * 20, 0));
            p.sendMessage("§7§oAw man I feel dizzy...");
        };
    }

}