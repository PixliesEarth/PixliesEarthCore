package eu.pixliesearth.core.customitems.ci;

import com.mojang.datafixers.types.templates.Tag;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.customitems.CustomItem;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemSlingshot implements CustomItem {

    @Override
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
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        NamespacedKey key = new NamespacedKey(Main.getInstance(), "antistack");
        Random r = new Random();
        double randomValue = 1 + (10000 - 1) * r.nextDouble();

        meta.getCustomTagContainer().setCustomTag(key, ItemTagType.DOUBLE, randomValue);

        slingshot.setItemMeta(meta);
       // net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(slingshot);
        //NBTTagCompound comp = nmsItem.getTag();
        //comp.set()
        return slingshot;
    }
    @Override
    public String getName(){
        return getRecipe().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore(){
        List<String> lore = new ArrayList<>();
        lore.add("§7Era: 1");
        lore.add("§7Requires stone like objects to shoot");
        lore.add("§7Durability: 15");
        return lore;
    }

    @Override
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
