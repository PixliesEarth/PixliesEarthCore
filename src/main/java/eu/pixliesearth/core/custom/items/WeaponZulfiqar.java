package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomWeapon;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class WeaponZulfiqar extends CustomWeapon {

    public WeaponZulfiqar() {

    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6§lZulfiqar";
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public Map<String, Object> getDefaultNBT() {
        return new HashMap<String, Object>();
    }

    @Override
    public Map<Enchantment, Integer> getDefaultEnchants() {
        return new HashMap<Enchantment, Integer>();
    }

    @Override
    public Set<ItemFlag> getItemFlags(){
        return new HashSet<ItemFlag>();
    }

    @Override
    public Integer getCustomModelData() {
        return 330;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.COMBAT;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Zulfiqar"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public int getDamage() {
        return 14;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.GODLIKE;
    }

    @Override
    public boolean EntityDamageEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return false;
        Player target = (Player) event.getEntity();
        target.sendMessage("§6You have been hit with divine intellect.");
        return false;
    }

}
