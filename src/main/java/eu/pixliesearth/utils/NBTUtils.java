package eu.pixliesearth.utils;

import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import net.minecraft.server.v1_16_R2.NBTTagCompound;

public class NBTUtils {

    private static NBTTagCompound getTag(org.bukkit.inventory.ItemStack item) {
        net.minecraft.server.v1_16_R2.ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
        if (itemNms.hasTag()) tag = itemNms.getTag();
        else tag = new NBTTagCompound();
        return tag;
    }

    private static org.bukkit.inventory.ItemStack setTag(org.bukkit.inventory.ItemStack item, NBTTagCompound tag) {
        net.minecraft.server.v1_16_R2.ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        itemNms.setTag(tag);
        return CraftItemStack.asBukkitCopy(itemNms);
    }

    public static org.bukkit.inventory.ItemStack addString(org.bukkit.inventory.ItemStack item, String name, String value) {
        NBTTagCompound tag = NBTUtils.getTag(item);
        tag.setString(name, value);
        return NBTUtils.setTag(item, tag);
    }

    public static boolean hasString(org.bukkit.inventory.ItemStack item, String name) {
        NBTTagCompound tag = NBTUtils.getTag(item);
        return tag.hasKey(name);
    }

    public static String getString(org.bukkit.inventory.ItemStack item, String name) {
        NBTTagCompound tag = NBTUtils.getTag(item);
        return tag.getString(name);
    }

}
