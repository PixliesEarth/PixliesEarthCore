package eu.pixliesearth.core.customitems.ci;

import eu.pixliesearth.core.customitems.DamagerCI;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class ItemStoneHatchet implements DamagerCI {

    @Override
    public ItemStack getRecipe() {
        return new ItemBuilder(Material.STONE_AXE)
                .setDisplayName("§7§lStone Hatchet")
                .setDurability((short) 100)
                .setCustomModelData(5)
                .addLoreLine("§2Damage: " + damage())
                .build();
    }

    @Override
    public String getName() {
        return getRecipe().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        List<String> returner = new ArrayList<>();
        returner.add("§2Damage: " + damage());
        return returner;
    }

    @Override
    public ItemStack getStatic(int durabilty) {
        return new ItemBuilder(Material.STONE_AXE)
                .setDisplayName("§7§lStone Hatchet")
                .setDurability((short) 100)
                .setCustomModelData(5)
                .addLoreLine("§2Damage: " + damage())
                .build();
    }

    @Override
    public boolean enchantable() {
        return true;
    }

    @Override
    public double damage() {
        return 2.0;
    }
}
