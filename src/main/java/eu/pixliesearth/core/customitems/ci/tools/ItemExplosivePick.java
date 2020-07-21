package eu.pixliesearth.core.customitems.ci.tools;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemExplosivePick implements CustomItem {
    @Override
    public ItemStack getItem() {
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
        return getItem().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        List<String> lore = getItem().getItemMeta().getLore();
        return lore;
    }

    @Override
    public ItemStack getStatic(int durability) {
        return getItem();
    }

    @Override
    public boolean enchantable() {
        return true;
    }

}
