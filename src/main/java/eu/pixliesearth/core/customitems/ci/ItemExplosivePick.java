package eu.pixliesearth.core.customitems.ci;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemExplosivePick implements CustomItem {
    @Override
    public ItemStack getRecipe() {
/*        ItemStack explo = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = explo.getItemMeta();
        meta.setDisplayName("§6Explosive Pickaxe");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Era: tbd");
        lore.add("§7Break a 3x3 area of blocks");
        meta.setCustomModelData(3);
        explo.setItemMeta(meta);*/
        return new ItemBuilder(Material.DIAMOND_PICKAXE)
                .setDisplayName("§6Explosive Pickaxe")
                .addLoreLine("§7Era: tbd")
                .addLoreLine("§7Break a 3x3 area of blocks")
                .setCustomModelData(3)
                .build();
    }

    @Override
    public String getName() {
        return getRecipe().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        List<String> lore = getRecipe().getItemMeta().getLore();
        return lore;
    }

    @Override
    public ItemStack getStatic(int i) {
        return getRecipe();
    }

    @Override
    public boolean enchantable() {
        return true;
    }

}
