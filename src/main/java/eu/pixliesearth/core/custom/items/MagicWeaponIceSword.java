package eu.pixliesearth.core.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.util.Vector;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomWeapon;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;

public class MagicWeaponIceSword extends CustomWeapon {

    public MagicWeaponIceSword() {

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
        return "§bIce Sword";
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
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.REDSTONE;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Ice_Sword"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public Integer getCustomModelData() {
        return null;
    }

    @Override
    public int getDamage() {
        return 7;
    }
    
    @Override
    public boolean EntityDamageEvent(EntityDamageEvent event) {
    	if (!(event instanceof EntityDamageByEntityEvent)) return false;
    	EntityDamageByEntityEvent event2 = ((EntityDamageByEntityEvent)event);
    	Profile profile = Main.getInstance().getProfile(event2.getDamager().getUniqueId());
        if (Energy.take(profile, 0.05)) {
        	event.getEntity().setVelocity(new Vector(0D, event.getEntity().getVelocity().getY(), 0D));
        } else {
        	((Player)event.getEntity()).sendActionBar("§cNot enough mana");
        }
    	return false;
    }

}