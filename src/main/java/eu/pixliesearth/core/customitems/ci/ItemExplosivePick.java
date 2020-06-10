package eu.pixliesearth.core.customitems.ci;

import eu.pixliesearth.core.customitems.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemExplosivePick implements CustomItem {
    @Override
    public ItemStack getRecipe(){
        Material type;
        ItemStack explo = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = explo.getItemMeta();
        meta.setDisplayName("§6Explosive Pickaxe");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Era: tbd");
        lore.add("§7Break a 3x3 area of blocks");
        meta.setCustomModelData(3);
        explo.setItemMeta(meta);
        return explo;
    }

    @Override
    public String getName(){
        return getRecipe().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore(){
        List<String> lore = getRecipe().getItemMeta().getLore();
        return lore;
    }

    @Override
    public ItemStack getStatic(int i){
        return getRecipe();
    }
}
