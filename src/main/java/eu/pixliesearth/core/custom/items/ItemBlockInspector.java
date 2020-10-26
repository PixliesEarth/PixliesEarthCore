package eu.pixliesearth.core.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.CustomItemUtil;

public class ItemBlockInspector extends CustomItem {
	
    public ItemBlockInspector() {

    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§cBlock Inspector";
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
        return CreativeTabs.TOOLS;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Block_Inspector"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public Rarity getRarity() {
    	return Rarity.RARE;
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	if (event.getPlayer().getInventory().getItemInMainHand()==null) return false;
		if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(MinecraftMaterial.AIR.getMaterial())) return false;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Player p = event.getPlayer();
		p.sendMessage("§6§lBlock Information");
		p.sendMessage("§7» §eUUID: §c"+CustomItemUtil.getUUIDFromLocation(event.getClickedBlock().getLocation()));
		p.sendMessage("§7» §eEnergy: §c"+(h.getPowerAtLocation(event.getClickedBlock().getLocation())!=null));
		p.sendMessage("§7» §eMachine: §c"+(CustomItemUtil.getUUIDFromLocation(event.getClickedBlock().getLocation()).startsWith("Machine")));
		return false;
    }
}