package eu.pixliesearth.pixliefun.items.food;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemConsumptionHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IrishCoffee extends SimpleSlimefunItem<ItemConsumptionHandler> {

    public IrishCoffee() {
        super(PixlieFun.foodAndDrinks, PixlieFunItems.IRISHCOFFEE, PixlieFun.DISTILLERY, new ItemStack[] {new ItemStack(Material.MILK_BUCKET), new ItemStack(Material.COCOA_BEANS, 2), new ItemStack(Material.WHEAT), null, null, null, null, null, null});
    }

    @Override
    public ItemConsumptionHandler getItemHandler() {
        return (e, p, item) -> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 15 * 20, 0));
            p.sendMessage("§7§oAw man I feel dizzy...");
        };
    }

}
