package eu.pixliesearth.core.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SkullBuilder {
    private Class<?> skullMetaClass;
    private final ItemStack skull;
    private final SkullMeta skullMeta;
    private final List<String> lore = new ArrayList();
    private final String signature;
    private final String value;

    public SkullBuilder(String signature, String value) {
        this.signature = signature;
        this.value = value;
        this.skull = new ItemStack(Material.PLAYER_HEAD, 1);
        this.skullMeta = (SkullMeta) this.skull.getItemMeta();

        try {
            this.skullMetaClass = Class.forName("org.bukkit.craftbukkit.v1_15_R1.inventory.CraftMetaSkull");
        } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
        }

    }

    public SkullBuilder setDisplayname(String name) {
        this.skullMeta.setDisplayName(name);
        return this;
    }

    public SkullBuilder setAmount(int amount) {
        this.skull.setAmount(amount);
        return this;
    }

    public SkullBuilder addLoreLine(String line) {
        this.lore.add(line);
        return this;
    }

    public SkullBuilder addLoreArray(String[] lines) {
        this.lore.addAll(Arrays.asList(lines));

        return this;
    }

    public SkullBuilder addLoreAll(List<String> lines) {
        this.lore.addAll(lines);
        return this;
    }

    public ItemStack build() {
        if (!this.lore.isEmpty()) {
            this.skullMeta.setLore(this.lore);
        }

        try {
            Field profileField = this.skullMetaClass.getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(this.skullMeta, this.getProfile(this.signature, this.value));
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        this.skull.setItemMeta(this.skullMeta);
        this.skull.setDurability((short) 3);
        return this.skull;
    }

    private GameProfile getProfile(String signature, String value) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), (String) null);
        Property property = new Property("textures", value, signature);
        profile.getProperties().put("textures", property);
        return profile;
    }
}
