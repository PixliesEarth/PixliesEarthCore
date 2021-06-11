package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

/**
 * @author Zenake
 */
public class ToolNetherPlatePickaxe extends CustomItem {
    public ToolNetherPlatePickaxe() {
        
    }
    
    @Override
    public Material getMaterial() {
        return Material.NETHERITE_PICKAXE;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Nether Plate Pickaxe";
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
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.REDSTONE;
    }
    
    @Override
    public String getUUID() {
        return "Pixlies:Nether_Plate_Pickaxe"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return false;
    }
}