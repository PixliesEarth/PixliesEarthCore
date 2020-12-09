package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomArmour;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

/**
 * @author Zenake
 */
public class ArmourHornyHelmet extends CustomArmour {

    public ArmourHornyHelmet() {

    }

    @Override
    public Material getMaterial() {
        return Material.LEATHER_HELMET;
    }

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<String>() {private static final long serialVersionUID = -3234274418406689465L;{
            add("§4g§ca§6c§eh§2i§aB§bA§3S§1S");
        }};
    }

    @Override
    public String getDefaultDisplayName() {
        return "§7§lHorny Helmet";
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
        return null;
    }

    @Override
    public double getArmour() {
        return 25D;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.COMBAT;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Horny_Helmet"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return false;
    }

    @Override
    public boolean EntityDamageEvent(EntityDamageEvent event) {
        return false;
    }

}