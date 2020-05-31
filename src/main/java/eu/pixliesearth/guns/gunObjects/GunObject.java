package eu.pixliesearth.guns.gunObjects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GunObject {
    public ItemStack AK() {
        Material type;
        ItemStack ak = new ItemStack(Material.WOODEN_AXE);
        ItemMeta meta = ak.getItemMeta();
        meta.setDisplayName("§cAK47");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§7Ammo: §f30/30");
        lore.add("§7Damage: §f3.0");
        lore.add("§7Type: §f7.62mm");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        //TODO: for texturepack
        //meta.setCustomModelData();
        ak.setItemMeta(meta);
        return ak;
    }
}
