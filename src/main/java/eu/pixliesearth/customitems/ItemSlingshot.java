package eu.pixliesearth.customitems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemSlingshot {
    public ItemStack getRecipe(){
        Material type;
        ItemStack slingshot = new ItemStack(Material.STICK);
        ItemMeta meta = slingshot.getItemMeta();
        meta.setDisplayName("§6Slingshot");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§7Era: 1");
        lore.add("§7Requires stone like objects to shoot");
        lore.add("§7Durability: 15");
        meta.setLore(lore);
        meta.setCustomModelData(1);
        slingshot.setItemMeta(meta);
        return slingshot;
    }

    public String getName(){
        return getRecipe().getI18NDisplayName();
    }

    public List<String> getLore(){
        List<String> lore = new ArrayList<>();
        lore.add("§7Era: 1");
        lore.add("§7Requires stone like objects to shoot");
        lore.add("§7Durability: 15");
        return lore;
    }

    public ItemStack getStatic(int durabilty){
        ItemStack slingshot = new ItemStack(Material.STICK);
        ItemMeta meta = slingshot.getItemMeta();
        meta.setDisplayName("§6Slingshot");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§7Era: 1");
        lore.add("§7Requires stone like objects to shoot");
        lore.add("§7Durability: " + durabilty);
        meta.setLore(lore);
        meta.setCustomModelData(1);
        slingshot.setItemMeta(meta);
        return slingshot;


    }
}
