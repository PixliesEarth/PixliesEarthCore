package eu.pixliesearth.core.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.Energyable;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.Methods;

public class ItemEnergyInspector extends CustomItem {
	
    public ItemEnergyInspector() {

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
        return "§cEnergy Inspector";
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
        return "Pixlies:Energy_Inspector"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public Rarity getRarity() {
    	return Rarity.RARE;
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	if (event.getPlayer().getInventory().getItemInMainHand()==null) return false;
		if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(MinecraftMaterial.AIR.getMaterial())) return false;
		Player p = event.getPlayer();
		Double d = CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(event.getClickedBlock().getLocation());
		if (d==null) {
			p.sendMessage("§6This block does not contain energy!");
			return false;
		}
		p.sendMessage("§6§lEnergy Statistics");
		p.sendMessage("§7» §eContains: §c"+Methods.convertEnergyDouble(d));
		p.sendMessage("§7» §eCapacity: §c"+Methods.convertEnergyDouble(getCapacity(event.getClickedBlock().getLocation())));
		return false;
    }
    
    public Double getCapacity(Location location) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		CustomBlock c = h.getCustomBlockFromLocation(location);
		if (c==null) return null;
		if (c instanceof Energyable) {
			return ((Energyable)c).getCapacity();
		} else {
			return null;
		}
	}
}