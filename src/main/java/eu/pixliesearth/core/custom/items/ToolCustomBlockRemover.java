package eu.pixliesearth.core.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.MinecraftMaterial;

public class ToolCustomBlockRemover extends CustomItem {

    public ToolCustomBlockRemover() {

    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_SHOVEL;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§c§lBlock Remover";
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
        return null;
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
        return CreativeTabs.TOOLS;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Block_Remover"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	Block b = event.getClickedBlock();
    	if (b==null) return true;
    	CustomBlock cb = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(b.getLocation());
    	if (cb==null) {
    		b.setType(MinecraftMaterial.AIR.getMaterial());
    	} else {
    		CustomFeatureLoader.getLoader().getHandler().removeCustomBlockFromLocation(b.getLocation());
    	}
        return false;
    }

}
