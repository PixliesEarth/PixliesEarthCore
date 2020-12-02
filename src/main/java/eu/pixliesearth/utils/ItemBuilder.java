package eu.pixliesearth.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private ItemStack item;
    private List<String> lore;
    private ItemMeta meta;

    public ItemBuilder(ItemStack item) {
        this.item = item.clone();
        this.meta = this.item.getItemMeta();
        this.lore = this.meta.getLore() == null ? new ArrayList<>() : meta.getLore();
    }
    
    public ItemBuilder(String uuid) {
        this.item = CustomItemUtil.getItemStackFromUUID(uuid);
        this.meta = this.item.getItemMeta();
        this.lore = this.meta.getLore() == null ? new ArrayList<>() : meta.getLore();
    }

    public ItemBuilder(Material mat, int amount) {
        item = new ItemStack(mat, amount);
        meta = item.getItemMeta();
        this.lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
    }

    public ItemBuilder(Material mat) {
        item = new ItemStack(mat);
        meta = item.getItemMeta();
        this.lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
    }
    /**
     * Use {@link ItemBuilder#setDamage(int)} instead!
     * 
     * @param durability the durability to set the item to
     */
    @Deprecated
    public ItemBuilder setDurability(short durability) {
        item.setDurability(durability);
        item.setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder setDamage(int damage) {
        Damageable d = (Damageable) meta;
        d.setDamage(damage);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setAmount(int value) {
        item.setAmount(value);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setNoName() {
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setGlow() {
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setGlow(boolean value) {
        if (value) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        lore.add(line);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLoreArray(String[] lines) {
        lore.addAll(Arrays.asList(lines));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLoreAll(List<String> lines) {
        lore.addAll(lines);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        ((SkullMeta) meta).setOwningPlayer(getOfflinePlayer(owner));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setSkullOwner(UUID uuid) {
        ((SkullMeta) meta).setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBook(Enchantment enchantment, int level) {
        ((EnchantmentStorageMeta) meta).addStoredEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean value) {
        meta.setUnbreakable(value);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setCustomModelData(int data) {
        meta.setCustomModelData(data);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment ench, int lvl) {
        meta.addEnchant(ench, lvl, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder hideFlags() {
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addNBTTag(String key, Object value, NBTTagType type) {
		NBTUtil.NBTTags tags = NBTUtil.getTagsFromItem(item);
		tags.addTag(key, value, type);
		item = NBTUtil.addTagsToItem(item, tags);
        return this;
    }

    public ItemBuilder setAttackDamage(double amount) {
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        item.setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder setArmour(double amount) {
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.armor", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier modifier3 = new AttributeModifier(UUID.randomUUID(), "generic.armor", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier modifier4 = new AttributeModifier(UUID.randomUUID(), "generic.armor", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier2);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier3);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier4);
        item.setItemMeta(meta);
        return this;
    }
    public ItemBuilder setArmourToughness(double amount) {
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier modifier3 = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier modifier4 = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier2);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier3);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier4);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder cloneBuilder() {
        return new ItemBuilder(build().clone());
    }

    public ItemStack build() {
        return item;
    }
    
    private OfflinePlayer getOfflinePlayer(String name) {
    	for (OfflinePlayer op : Bukkit.getOfflinePlayers()) if (op.getName().equalsIgnoreCase(name)) return op;
    	return null;
    }

}
