package eu.pixliesearth.pixliefun.items.food;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemConsumptionHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DonJulioTequilla extends SimpleSlimefunItem<ItemConsumptionHandler> {

    public DonJulioTequilla() {
        super(PixlieFun.foodAndDrinks, PixlieFunItems.DONJULIO_TEQUILA, PixlieFun.DISTILLERY, new ItemStack[] {new ItemStack(Material.SUGAR_CANE), new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GOLD_NUGGET), new ItemStack(Material.SWEET_BERRIES), null, null, null, null, null});
    }

    @Override
    public ItemConsumptionHandler getItemHandler() {
        return (e, p, item) -> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 15 * 20, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 0));
            p.sendMessage("§7§oAw man I feel dizzy...");
        };
    }

}
