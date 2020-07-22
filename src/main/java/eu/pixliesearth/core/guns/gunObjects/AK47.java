package eu.pixliesearth.core.guns.gunObjects;

import eu.pixliesearth.core.guns.Gun;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AK47 extends Gun {

    public int damage = 3;

    public int maxAmmo = 30;

    public boolean automatic = true;

    @Override
    public ItemStack getItem(int ammo) {
        ItemStack ak = new ItemStack(Material.WOODEN_AXE);
        ItemMeta meta = ak.getItemMeta();
        String type = automatic ? "&a&lAUTOMATIC" : "&a&lSINGLE-FIRE";
        meta.setDisplayName("§cAK47");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(type);
        lore.add("§7Ammo: §f" + ammo + "/" + maxAmmo);
        lore.add("§7Damage: §f" + damage + "§c§l♥");
        lore.add("§7Type: §f7.62mm");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        meta.setCustomModelData(6);
        ak.setItemMeta(meta);
        return ak;
    }

}
