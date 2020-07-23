package eu.pixliesearth.core.guns.gunObjects;

import eu.pixliesearth.core.guns.Gun;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Musket extends Gun {

    @Override
    public double damage() { return 2D; };

    @Override
    public int maxAmmo() { return 5; };

    @Override
    public boolean automatic() { return false; };

    @Override
    public ItemStack getItem(int ammo) {
        ItemStack musket = new ItemStack(Material.WOODEN_AXE);
        ItemMeta meta = musket.getItemMeta();
        meta.setDisplayName("§cMusket");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§7Ammo: §f" + ammo + "/" + maxAmmo());
        lore.add("§7Damage: §f" + damage() + "§c§l♥");
        lore.add("§7Type: §fMusketball");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        meta.setCustomModelData(7);
        musket.setItemMeta(meta);
        return musket;
    }


}
